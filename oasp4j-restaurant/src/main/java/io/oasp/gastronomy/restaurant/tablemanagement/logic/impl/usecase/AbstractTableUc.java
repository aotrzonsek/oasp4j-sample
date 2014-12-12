package io.oasp.gastronomy.restaurant.tablemanagement.logic.impl.usecase;

import io.oasp.gastronomy.restaurant.general.logic.base.AbstractUc;
import io.oasp.gastronomy.restaurant.tablemanagement.delivery.dataaccess.api.dao.TableDao;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.domain.TableRepository;

import javax.inject.Inject;

/**
 *
 * @author jozitz
 * @author llaszkie
 */
public abstract class AbstractTableUc extends AbstractUc {

  /** @see #getTableRepository() */
  private TableRepository tableRepository;

  /** @see #getTableDao() */
  private TableDao tableDao;

  /**
   * @return the {@link TableRepository} instance.
   */
  public TableRepository getTableRepository() {

    return this.tableRepository;
  }

  /**
   * @return the {@link TableDao} instance.
   */
  public TableDao getTableDao() {

    return this.tableDao;
  }

  /**
   * @param tableDao the {@link TableDao} to {@link Inject}.
   */
  @Inject
  public void setTableDao(TableDao tableDao) {

    this.tableDao = tableDao;
  }

  /**
   * @param tableRepository the {@link TableRepository} to {@link Inject}.
   */
  @Inject
  public void setTableRepository(TableRepository tableRepository) {

    this.tableRepository = tableRepository;
  }
}
