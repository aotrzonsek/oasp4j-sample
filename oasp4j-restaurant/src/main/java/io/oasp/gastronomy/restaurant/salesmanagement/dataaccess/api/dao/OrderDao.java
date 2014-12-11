package io.oasp.gastronomy.restaurant.salesmanagement.dataaccess.api.dao;

import io.oasp.gastronomy.restaurant.general.dataaccess.api.dao.ApplicationDao;
import io.oasp.gastronomy.restaurant.salesmanagement.dataaccess.api.OrderEntity;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderSearchCriteriaTo;

import java.util.List;

/**
 * {@link ApplicationDao Data Access Object} for {@link OrderEntity} entity.
 *
 * @author hohwille
 * @author rjoeris
 */
public interface OrderDao extends ApplicationDao<OrderEntity> {

  /**
   * @param tableId the {@link io.oasp.gastronomy.restaurant.tablemanagement.delivery.dataaccess.api.TableEntity#getNumber()
   *        table ID} for which the open {@link OrderEntity} is requested.
   * @return the {@link OrderEntity} with the given {@link OrderEntity#getTableId() table ID} in
   *         {@link OrderEntity#getState() state}
   *         {@link io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.OrderState#OPEN} or
   *         <code>null</code> if no such entity exists.
   */
  OrderEntity findOpenOrderByTable(long tableId);

  /**
   * Finds the {@link OrderEntity orders} matching the given {@link OrderSearchCriteriaTo}.
   *
   * @param criteria is the {@link OrderSearchCriteriaTo}.
   * @return the matching {@link OrderEntity} or <code>null</code> if no such {@link OrderEntity} exists.
   */
  List<OrderEntity> findOrders(OrderSearchCriteriaTo criteria);

}
