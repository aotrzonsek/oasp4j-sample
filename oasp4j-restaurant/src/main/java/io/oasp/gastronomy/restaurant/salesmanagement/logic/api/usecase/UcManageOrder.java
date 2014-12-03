package io.oasp.gastronomy.restaurant.salesmanagement.logic.api.usecase;

import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderCto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderEto;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.to.TableEto;

/**
 * Interface of UcMangeOrder to centralize documentation and signatures of methods.
 *
 * @author mvielsac
 */
public interface UcManageOrder {
  /**
   * Creates an {@link OrderEto} for the given {@link TableEto}.
   *
   * @param table for which the {@link OrderEto} is
   *
   * @return the new {@link OrderEto}
   */
  OrderEto createOrder(TableEto table);

  /**
   * Saves or updates the given {@link OrderCto}.
   *
   * @param order is the {@link OrderCto} to persist.
   * @return the saved {@link OrderCto}.
   */
  OrderCto updateOrder(OrderCto order);

  /**
   * Saves or updates the given {@link OrderEto}.
   *
   * @param order is the {@link OrderEto} to persist.
   * @return the saved {@link OrderEto}.
   */
  OrderEto updateOrder(OrderEto order);

  /**
   * @param table the {@link OrderEto} to create.
   * @return the created OrderEto
   */
  OrderEto createOrder(OrderEto table);

  /**
   * @param id is the {@link OrderEto#getId() ID} of the order to delete.
   */
  void deleteOrder(Long id);
}
