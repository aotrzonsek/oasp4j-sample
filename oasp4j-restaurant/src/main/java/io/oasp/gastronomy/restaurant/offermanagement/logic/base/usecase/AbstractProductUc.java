package io.oasp.gastronomy.restaurant.offermanagement.logic.base.usecase;

import io.oasp.gastronomy.restaurant.general.logic.base.AbstractUc;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.dao.ProductDao;

import javax.inject.Inject;

/**
 * Abstract base class for a {@link io.oasp.gastronomy.restaurant.offermanagement.common.api.Product} related use-case.
 *
 * @author jozitz
 */
public abstract class AbstractProductUc extends AbstractUc {

  /** @see #setProductDao(ProductDao) */
  private ProductDao productDao;

  /**
   * The constructor.
   */
  public AbstractProductUc() {

    super();
  }

  /**
   * @return productDao
   */
  public ProductDao getProductDao() {

    return this.productDao;
  }

  /**
   * Sets the field 'productDao'.
   *
   * @param productDao New value for productDao
   */
  @Inject
  public void setProductDao(ProductDao productDao) {

    this.productDao = productDao;
  }

}
