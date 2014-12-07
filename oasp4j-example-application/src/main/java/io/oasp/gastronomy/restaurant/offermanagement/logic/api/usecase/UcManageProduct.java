package io.oasp.gastronomy.restaurant.offermanagement.logic.api.usecase;

import io.oasp.gastronomy.restaurant.general.logic.api.to.BinaryObjectEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.ProductEto;

import java.sql.Blob;

/**
 * Interface of UcManageProduct to centralize documentation and signatures of methods.
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

  /**
   * Updates the picture of the product
   *
   * @param productId is the ID of the {@link ProductEto} to update the picture
   * @param blob is the binary representation of the picture
   * @param binaryObjectEto is the mimeType of the blob
   */
  void updateProductPicture(Long productId, Blob blob, BinaryObjectEto binaryObjectEto);

  /**
   * Deletes the Picture of the {@link ProductEto}
   *
   * @param productId is the ID of the {@link ProductEto} to delte the picture
   */
  void deleteProductPicture(Long productId);

}
