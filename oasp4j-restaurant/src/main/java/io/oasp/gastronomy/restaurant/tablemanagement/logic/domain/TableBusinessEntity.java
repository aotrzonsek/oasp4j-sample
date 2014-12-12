package io.oasp.gastronomy.restaurant.tablemanagement.logic.domain;

import io.oasp.gastronomy.restaurant.general.common.api.exception.IllegalEntityStateException;
import io.oasp.gastronomy.restaurant.general.common.domain.BusinessEntity;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.Salesmanagement;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderEto;
import io.oasp.gastronomy.restaurant.tablemanagement.common.api.Table;
import io.oasp.gastronomy.restaurant.tablemanagement.common.api.datatype.TableState;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.to.TableEto;

/**
 * This is a business representation for a table of the restaurant. It could be projected to {@link Table}, can be
 * {@link TableState#isReserved() reserved} or {@link TableState#isOccupied() occupied}.
 *
 * @author llaszkie
 */
@BusinessEntity
public class TableBusinessEntity {

  private TableEto tableData;

  private Salesmanagement salesmanagement;

  /**
   * The constructor. State is explicitly requested.
   *
   * @param tableData the table base data
   * @param tableState initial state
   * @param salesmanagement logic
   */
  public TableBusinessEntity(TableEto tableData, TableState tableState, Salesmanagement salesmanagement) {

    super();
    this.salesmanagement = salesmanagement;
    this.tableData = (TableEto) tableData.clone();
    this.tableData.setState(tableState);
  }

  /**
   * The constructor. State is set to {@link TableState#FREE}.
   *
   * @param tableData the table base data
   * @param salesmanagement logic
   */
  public TableBusinessEntity(TableEto tableData, Salesmanagement salesmanagement) {

    super();
    this.salesmanagement = salesmanagement;
    this.tableData = (TableEto) tableData.clone();
    this.tableData.setState(TableState.FREE);
  }

  /**
   * @return <code>true</code> if underlying table can be deleted
   */
  public boolean canBeDeleted() {

    return getTable().getState() == TableState.FREE;
  }

  /**
   * @return the underlying table state
   */
  public TableState getState() {

    return this.tableData.getState();
  }

  /**
   * @return simple projection of base data
   */
  public TableEto getTable() {

    return (TableEto) this.tableData.clone();
  }

  /**
   * Evaluate if this table could marked as free.
   *
   * @return <code>true</code> if the table could be released<br>
   *         <code>false</code> , otherwise
   */
  public boolean isTableReleasable() {

    if (getState() != TableState.OCCUPIED) {
      return true;
    }

    OrderEto order = this.salesmanagement.findOpenOrderForTable(this.tableData.getId());
    // no open order so the table is actually free...
    return order == null;
  }

  /**
   * Marks a restaurant table as {@link TableState}.
   *
   * @param newState new table {@link TableState}
   */
  public void markTableAs(TableState newState) {

    if (getState() == newState) {
      return;
    }

    switch (newState) {
    case FREE:
      // we need the ensure that there is no open order associated with the table...
      OrderEto openOrder = this.salesmanagement.findOpenOrderForTable(this.tableData.getId());
      if (openOrder != null) {
        throw new IllegalEntityStateException(this, getState(), newState);
      }
      break;
    case RESERVED:
      if (getState() != TableState.FREE) {
        // table has to be free before going to reserved state...
        throw new IllegalEntityStateException(this, getState(), newState);
      }
      break;
    default:
      // nothing to do...
      break;
    }

    setState(newState);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return getClass().getSimpleName();
  }

  /**
   * @param newState new table {@link TableState}
   */
  private void setState(TableState newState) {

    this.tableData.setState(newState);
  }

}
