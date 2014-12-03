package io.oasp.gastronomy.restaurant.offermanagement.logic.api.usecase;

import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.ProductEto;

/**
 * Interface for the usecsae to manage an {@link ProductEto product}.
 *
 * @author mvielsac
 */
public interface UcManageProduct {
  /**
   * Updates a product.
   *
   * @param product the {@link ProductEto} to persist.
   */
  void updateProduct(ProductEto product);

  /**
   * Creates the {@link ProductEto} for the first time.
   *
   * @param product the {@link ProductEto} to persist.
   */
  void createProduct(ProductEto product);

  /**
   * Deletes a {@link ProductEto}.
   *
   * @param productId is the ID of the {@link ProductEto} to delete
   */
  void deleteProduct(Long productId);
}
