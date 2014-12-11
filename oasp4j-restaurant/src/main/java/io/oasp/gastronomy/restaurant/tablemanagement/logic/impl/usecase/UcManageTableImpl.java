package io.oasp.gastronomy.restaurant.tablemanagement.logic.impl.usecase;

import io.oasp.gastronomy.restaurant.general.common.api.exception.IllegalEntityStateException;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.Salesmanagement;
import io.oasp.gastronomy.restaurant.staffmanagement.logic.api.Staffmanagement;
import io.oasp.gastronomy.restaurant.tablemanagement.common.api.datatype.TableState;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.to.TableEto;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.usecase.UcManageTable;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.domain.TableBusinessEntity;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.domain.TableFactory;

import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of {@link UcManageTable}.
 *
 * @author jozitz
 */
@Named
public class UcManageTableImpl extends AbstractTableUc implements UcManageTable {

  /** Logger instance. */
  private static final Logger LOG = LoggerFactory.getLogger(UcManageTableImpl.class);

  private Salesmanagement salesManagement;

  private Staffmanagement staffmanagement;

  /**
   * {@inheritDoc}
   */
  @Override
  public TableEto createTable(TableEto table) {

    Objects.requireNonNull(table, "table");

    TableBusinessEntity tableBusinessEntity =
        new TableFactory(table, this.staffmanagement, this.salesManagement).create();
    TableEto savedTable = getTableRepository().save(tableBusinessEntity);

    LOG.debug("Table with id '{}' has been created.", savedTable.getId());
    return savedTable;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void deleteTable(Long tableId) {
  
    Objects.requireNonNull(tableId, "table");
  
    TableBusinessEntity tableBusinessEntity = getTableRepository().find(tableId);
    if (!tableBusinessEntity.canBeDeleted()) {
      throw new IllegalEntityStateException(tableBusinessEntity, tableBusinessEntity.getState());
    }
    getTableRepository().delete(tableBusinessEntity);
    LOG.debug("Table with id '{}' has been deleted.", tableBusinessEntity.getTable().getId());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void markTableAs(Long tableId, TableState newState) {

    Objects.requireNonNull(tableId, "table");

    TableBusinessEntity tableBusinessEntity = getTableRepository().find(tableId);
    tableBusinessEntity.markTableAs(newState);
    getTableRepository().save(tableBusinessEntity);
    LOG.debug("The table with id '{}' is marked as {}.", tableId, newState);

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isTableReleasable(Long tableId) {

    Objects.requireNonNull(tableId, "table");

    TableBusinessEntity tableBusinessEntity = getTableRepository().find(tableId);
    return tableBusinessEntity.isTableReleasable();
  }

  /**
   * Sets the field 'salesManagement'.
   *
   * @param salesManagement new value for salesManagement
   */
  @Inject
  public void setSalesManagement(Salesmanagement salesManagement) {

    this.salesManagement = salesManagement;
  }

  /**
   * @param staffmanagement the staffmanagement to set
   */
  @Inject
  public void setStaffmanagement(Staffmanagement staffmanagement) {

    this.staffmanagement = staffmanagement;
  }

}
