package io.oasp.gastronomy.restaurant.offermanagement.logic.impl.usecase;

import io.oasp.gastronomy.restaurant.offermanagement.common.api.Product;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.ProductEntity;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.ProductEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.usecase.UcManageProduct;
import io.oasp.gastronomy.restaurant.offermanagement.logic.base.usecase.AbstractProductUc;

import java.util.Objects;

import javax.inject.Named;

import net.sf.mmm.util.exception.api.DuplicateObjectException;

/**
 * Implementation of {@link UcManageProduct}.
 *
 * @author loverbec
 */
@Named
public class UcManageProductImpl extends AbstractProductUc implements UcManageProduct {

  /**
   * {@inheritDoc}
   */
  @Override
  public void updateProduct(ProductEto product) {

    Objects.requireNonNull(product, "product");

    ProductEntity productEntity = getBeanMapper().map(product, ProductEntity.class);
    getProductDao().save(productEntity);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void createProduct(ProductEto product) {

    Objects.requireNonNull(product, "product");

    Long id = product.getId();
    if (id != null) {
      ProductEntity persistedProduct = getProductDao().findOne(id);
      if (persistedProduct != null) {
        throw new DuplicateObjectException(Product.class, id);
      }
    }
    getProductDao().save(getBeanMapper().map(product, ProductEntity.class));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void deleteProduct(Long productId) {

    getProductDao().delete(productId);
  }

}
