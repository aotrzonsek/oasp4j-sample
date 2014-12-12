package io.oasp.gastronomy.restaurant.tablemanagement.logic.api.usecase;

import io.oasp.gastronomy.restaurant.tablemanagement.common.api.datatype.TableState;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.to.TableEto;

/**
 * Interface of UcManageTable to centralize documentation and signatures of methods.
 *
 * @author mvielsac
 */
public interface UcManageTable {
  /**
   * Deletes a restaurant table from the database by its id 'id'.
   *
   * @param tableId Id of the restaurant table to delete
   */
  void deleteTable(Long tableId);

  /**
   * Creates a new restaurant table and store it in the database.
   *
   * @param table the {@link TableEto} to create.
   * @return the new {@link TableEto} that has been saved with ID and version.
   */
  TableEto createTable(TableEto table);

  /**
   * Marks a restaurant table as {@link TableState}.
   *
   * @param table Table the restaurant table to mark as occupied
   * @param newState new table {@link TableState}
   * @throws ValidationException thrown by validation check
   */
  /**
   * Evaluate if this table could marked as free.
   *
   * @param tableId Id of the restaurant table to be evaluate
   * @return <code>true</code> if the table could be released<br>
   *         <code>false</code> , otherwise
   */
  boolean isTableReleasable(Long tableId);

  /**
   * Marks a restaurant table as {@link TableState}.
   *
   * @param tableId Id of the restaurant table to mark as occupied
   * @param newState new table {@link TableState}
   */
  void markTableAs(Long tableId, TableState newState);
}
