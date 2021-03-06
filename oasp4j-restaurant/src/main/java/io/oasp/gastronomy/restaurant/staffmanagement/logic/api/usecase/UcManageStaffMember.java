package io.oasp.gastronomy.restaurant.staffmanagement.logic.api.usecase;

import io.oasp.gastronomy.restaurant.staffmanagement.logic.api.to.StaffMemberEto;

/**
 * Interface of UcManageStaffMember to centralize documentation and signatures of methods.
 *
 * @author mvielsac
 */
public interface UcManageStaffMember {

  /**
   * @param staffMemberId the {@link StaffMemberEto#getId() ID} of a {@link StaffMemberEto} to delete.
   */
  void deleteStaffMember(Long staffMemberId);

  /**
   * @param login {@link StaffMemberEto#getName() login} of a {@link StaffMemberEto} to delete.
   */
  void deleteStaffMemberByLogin(String login);

  /**
   * @param staffMember The {@link StaffMemberEto} to update.
   */
  void updateStaffMember(StaffMemberEto staffMember);
}
