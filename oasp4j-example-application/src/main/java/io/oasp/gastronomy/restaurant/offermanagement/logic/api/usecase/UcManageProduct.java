package io.oasp.gastronomy.restaurant.offermanagement.logic.api.usecase;

import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.ProductEto;

/**
 * Interface for the usecsae to manage an {@link ProductEto product}.
 *
 * @author mvielsac
 */
public interface UcManageProduct {

  /**
   * If no ID is contained creates the {@link ProductEto} for the first time. Else it updates the {@link ProductEto}
   * with given ID. If no {@link ProductEto} with given ID is present, an exception will be thrown.
   *
   * @param product the {@link ProductEto} to persist.
   * @return the persisted {@link ProductEto}.
   */
  ProductEto saveProduct(ProductEto product);

  /**
   * Deletes a {@link ProductEto}.
   *
   * @param productId is the ID of the {@link ProductEto} to delete
   */
  void deleteProduct(Long productId);
}
