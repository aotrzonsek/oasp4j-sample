package io.oasp.gastronomy.restaurant.tablemanagement.logic.domain;

import io.oasp.gastronomy.restaurant.general.common.api.datatype.Role;
import io.oasp.gastronomy.restaurant.general.common.domain.Factory;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.Salesmanagement;
import io.oasp.gastronomy.restaurant.staffmanagement.common.api.StaffMember;
import io.oasp.gastronomy.restaurant.staffmanagement.logic.api.Staffmanagement;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.to.TableEto;

/**
 * This type encapsulates creation logic for {@link TableBusinessEntity}
 *
 * @author llaszkie
 */
@Factory
public class TableFactory {

  // elements
  private TableEto source;

  // logic
  private Staffmanagement staffmanagement;

  private Salesmanagement salesmanagement;

  /**
   * The constructor.
   *
   * @param source source data
   * @param staffmanagement service for checking creator role
   * @param salesmanagement service needed by created {@link TableBusinessEntity}
   */
  public TableFactory(TableEto source, Staffmanagement staffmanagement, Salesmanagement salesmanagement) {

    super();
    this.source = source;
    this.staffmanagement = staffmanagement;
    this.salesmanagement = salesmanagement;
  }

  /**
   * Creates a new restaurant table, performs necessary checks.
   *
   * @return the new {@link TableBusinessEntity} that could be stored in DB or used.
   */
  public TableBusinessEntity create() {

    Long tableId = this.source.getId();
    if (tableId != null) {
      throw new IllegalArgumentException("Table ID must not be set for creation!");
    }

    Long waiterId = this.source.getWaiterId();
    if (waiterId != null) {
      StaffMember staffMember = this.staffmanagement.findStaffMember(waiterId);
      if (Role.WAITER != staffMember.getRole()) {
        throw new IllegalArgumentException("Staff Member with id " + waiterId + " has role " + staffMember.getRole()
            + " and can not be associated as waiter for table with ID " + tableId + "!");
      }
    }

    return new TableBusinessEntity(this.source, this.salesmanagement);
  }

}
