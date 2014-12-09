package io.oasp.gastronomy.restaurant.salesmanagement.service.impl.rest;

import io.oasp.gastronomy.restaurant.general.common.api.constants.PermissionConstants;
import io.oasp.gastronomy.restaurant.general.common.api.datatype.Money;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferEto;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.OrderPositionState;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.OrderState;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.PaymentStatus;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.Salesmanagement;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.BillEto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderCto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderEto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderPositionEto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderSearchCriteriaTo;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.PaymentData;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.usecase.UcManageOrderPosition;
import io.oasp.module.rest.service.api.RequestParameters;

import java.util.List;
import java.util.Objects;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.springframework.transaction.annotation.Transactional;

/**
 * This class contains methods for handling REST calls for {@link Salesmanagement}.
 *
 * @author agreul
 */
@Path("/salesmanagement")
@Named("SalesmanagementRestService")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Transactional
public class SalesmanagementRestServiceImpl {

  private Salesmanagement salesManagement;

  /**
   * @param salesManagement the salesManagement to be set
   */
  @Inject
  public void setSalesManagement(Salesmanagement salesManagement) {

    this.salesManagement = salesManagement;
  }

  /**
   * @param orderId specified for the order
   * @return the order
   */
  @Path("/order/{orderId}")
  @GET
  @RolesAllowed(PermissionConstants.FIND_ORDER)
  public OrderEto getOrder(@PathParam("orderId") Long orderId) {

    return this.salesManagement.findOrder(orderId);
  }

  /**
   * @param info is the {@link UriInfo}.
   * @return the {@link List} of matching {@link OrderCto}s.
   */
  @Path("/order")
  @GET
  @RolesAllowed(PermissionConstants.FIND_ORDER)
  public List<OrderCto> findOrders(@Context UriInfo info) {

    RequestParameters parameters = RequestParameters.fromQuery(info);
    OrderSearchCriteriaTo criteria = new OrderSearchCriteriaTo();
    criteria.setTableId(parameters.get("tableId", Long.class, false));
    criteria.setState(parameters.get("state", OrderState.class, false));
    return this.salesManagement.findOrderCtos(criteria);
  }

  /**
   * @param order the {@link OrderCto} to update.
   * @param orderId the {@link OrderEto#getId() ID} of the order.
   * @return the updated {@link OrderCto}.
   */
  @Path("/order/{orderId}")
  @PUT
  @RolesAllowed(PermissionConstants.UPDATE_ORDER)
  @Deprecated
  public OrderCto updateOrder(OrderCto order, @PathParam("orderId") Long orderId) {

    Objects.requireNonNull(order, "order");
    OrderEto orderEto = order.getOrder();
    Objects.requireNonNull(orderEto, "order");
    Long jsonOrderId = orderEto.getId();
    if (jsonOrderId == null) {
      orderEto.setId(orderId);
    } else if (!jsonOrderId.equals(orderId)) {
      throw new BadRequestException("Order ID of URL does not match JSON payload!");
    }
    return this.salesManagement.saveOrder(order);
  }

  /**
   * If no ID is contained creates the {@link OfferEto} for the first time. Else it updates the {@link OfferEto} with
   * given ID. If no {@link OfferEto} with given ID is present, an exception will be thrown.
   *
   * @param order the {@link OrderCto} to create.
   * @return the created {@link OrderCto} (with {@link OrderEto#getId() ID}(s) assigned).
   */
  @Path("/order/")
  @POST
  @RolesAllowed(PermissionConstants.CREATE_ORDER)
  public OrderCto saveOrder(OrderCto order) {

    return this.salesManagement.saveOrder(order);
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("javadoc")
  @Path("/order/{orderId}/position/{orderPositionId}")
  @GET
  @RolesAllowed(PermissionConstants.FIND_ORDER)
  public OrderPositionEto getOrderPosition(@PathParam("orderPositionId") Long orderPositionId) {

    return this.salesManagement.findOrderPosition(orderPositionId);

  }

  /**
   * @param offer the offer as new position within an order as JSON
   * @param orderId the order id
   * @param comment the comment together with an orderPosition
   * @return a new orderPosition
   */
  @Path("/order/{orderId}/position/{comment}")
  @POST
  @RolesAllowed(PermissionConstants.CREATE_ORDER)
  public OrderPositionEto createOrderPosition(OfferEto offer, @PathParam("orderId") Long orderId,
      @PathParam("comment") String comment) {

    return this.salesManagement.createOrderPosition(offer, getOrder(orderId), comment);
  }

  // although orderId and orderPositionId are not explicitly needed here, the path structure is intentionally chosen
  // see createOrderPosition for a similar reason
  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("javadoc")
  @PUT
  @Path("/order/{orderId}/position/{orderPositionId}")
  @RolesAllowed(PermissionConstants.UPDATE_ORDER)
  @Deprecated
  public void updateOrderPosition(OrderPositionEto order) {

    this.salesManagement.saveOrderPosition(order);
  }

  /**
   * Calls {@link UcManageOrderPosition#saveOrderPosition}.
   *
   * @param orderPosition the OrderPositionEto to save
   * @return the saved OrderPositionEto
   */
  @PUT
  @Path("/order/{orderId}/position/")
  @RolesAllowed(PermissionConstants.UPDATE_ORDER)
  public OrderPositionEto saveOrderPosition(OrderPositionEto orderPosition) {

    return this.salesManagement.saveOrderPosition(orderPosition);
  }

  // again orderId is not explicitly needed here
  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("javadoc")
  @PUT
  @Path("/order/{orderId}/position/{orderPositionId}/{newstate}")
  @RolesAllowed(PermissionConstants.UPDATE_ORDER)
  public void markOrderPositionAs(OrderPositionEto orderPosition, @PathParam("newState") OrderPositionState newState) {

    this.salesManagement.markOrderPositionAs(orderPosition, newState);
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("javadoc")
  @GET
  @Path("/bill/{billId}")
  @RolesAllowed(PermissionConstants.FIND_BILL)
  public BillEto getBill(@PathParam("billId") Long billId) {

    return this.salesManagement.findBill(billId);
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("javadoc")
  @POST
  @Path("/bill/{billId}/payment")
  @RolesAllowed(PermissionConstants.UPDATE_BILL)
  public PaymentStatus doPayment(@PathParam("billId") Long billId) {

    return this.salesManagement.doPayment(getBill(billId));
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("javadoc")
  @Path("/bill/{billId}/payment")
  @POST
  @RolesAllowed(PermissionConstants.UPDATE_BILL)
  public PaymentStatus doPayment(@PathParam("billId") Long billId, PaymentData paymentData) {

    return this.salesManagement.doPayment(getBill(billId), paymentData);
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("javadoc")
  @POST
  @Path("/bill/{tip}")
  @RolesAllowed(PermissionConstants.CREATE_BILL)
  public BillEto createBill(List<OrderPositionEto> orderPositions, @PathParam("tip") Money tip) {

    return this.salesManagement.createBill(orderPositions, tip);
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("javadoc")
  @Path("/bill/{billId}")
  @DELETE
  @RolesAllowed(PermissionConstants.DELETE_BILL)
  public void deleteBill(@PathParam("billId") Long billId) {

    this.salesManagement.deleteBill(billId);
  }
}
