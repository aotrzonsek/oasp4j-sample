package io.oasp.gastronomy.restaurant.tablemanagement.logic.impl;

import io.oasp.gastronomy.restaurant.general.common.base.AbstractBeanMapperSupport;
import io.oasp.gastronomy.restaurant.tablemanagement.common.api.datatype.TableState;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.Tablemanagement;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.to.TableEto;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.usecase.UcFindTable;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.usecase.UcManageTable;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Implementation of {@link Tablemanagement}.
 *
 * @author etomety
 */
@Named
public class TablemanagementImpl extends AbstractBeanMapperSupport implements Tablemanagement {

  private UcFindTable ucFindTable;

  private UcManageTable ucManageTable;

  /**
   * The constructor.
   */
  public TablemanagementImpl() {

    super();
  }

  /**
   * Sets the field 'ucFindTable'.
   *
   * @param ucFindTable New value for ucFindTable
   */
  @Inject
  public void setUcFindTable(UcFindTable ucFindTable) {

    this.ucFindTable = ucFindTable;
  }

  /**
   * Sets the field 'ucManageTable'.
   *
   * @param ucManageTable New value for ucManageTable
   */
  @Inject
  public void setUcManageTable(UcManageTable ucManageTable) {

    this.ucManageTable = ucManageTable;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TableEto findTable(Long id) {

    return this.ucFindTable.findTable(id);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<TableEto> findAllTables() {

    return this.ucFindTable.findAllTables();
  }

  /**
   * {@inheritDoc}
   *
   */
  @Override
  public TableEto createTable(TableEto table) {

    return this.ucManageTable.createTable(table);
  }

  /**
   * {@inheritDoc}
   *
   */
  @Override
  public void deleteTable(Long id) {

    this.ucManageTable.deleteTable(id);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<TableEto> findFreeTables() {

    return this.ucFindTable.findFreeTables();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isTableReleasable(Long tableId) {

    return this.ucManageTable.isTableReleasable(tableId);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void markTableAs(Long tableId, TableState newState) {

    this.ucManageTable.markTableAs(tableId, newState);
  }

}
