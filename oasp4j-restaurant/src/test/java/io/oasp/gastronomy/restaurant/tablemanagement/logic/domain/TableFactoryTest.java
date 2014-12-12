package io.oasp.gastronomy.restaurant.tablemanagement.logic.domain;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import io.oasp.gastronomy.restaurant.general.common.api.datatype.Role;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.Salesmanagement;
import io.oasp.gastronomy.restaurant.staffmanagement.logic.api.Staffmanagement;
import io.oasp.gastronomy.restaurant.staffmanagement.logic.api.to.StaffMemberEto;
import io.oasp.gastronomy.restaurant.staffmanagement.logic.api.to.StaffMemberEtoBuilder;
import io.oasp.gastronomy.restaurant.tablemanagement.common.api.datatype.TableState;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.to.TableEto;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.to.TableEtoBuilder;

import java.util.Random;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Test for creation logic for {@link TableBusinessEntity}
 *
 * @author llaszkie
 */
public class TableFactoryTest extends Assert {

  private TableFactory testedTableFactory;

  @Mock
  private Staffmanagement staffmanagement;

  @Mock
  private Salesmanagement salesmanagement;

  /**
   * init mocks
   */
  @Before
  public void initMocks() {

    MockitoAnnotations.initMocks(this);
  }

  /**
   * Test method for {@link io.oasp.gastronomy.restaurant.tablemanagement.logic.domain.TableFactory#create()}.
   */
  @Test
  public void testShouldCreateTableBusinessEntity() {

    // given
    final long waiterId = new Random().nextLong();
    final TableEto source = TableEtoBuilder.tableEto().withWaiterId(waiterId).build();
    final StaffMemberEto waiter = StaffMemberEtoBuilder.staffMemberEto().withRole(Role.WAITER).build();

    // when
    when(this.staffmanagement.findStaffMember(eq(waiterId))).thenReturn(waiter);
    this.testedTableFactory = new TableFactory(source, this.staffmanagement, this.salesmanagement);
    // and when
    TableBusinessEntity tableBusinessEntity = this.testedTableFactory.create();

    // then
    assertNotNull("TableBusinessEntity is null!", tableBusinessEntity);
    assertNotNull("TableBusinessEntity.getTable() is null!", tableBusinessEntity.getTable());
    assertEquals("TableBusinessEntity state is not FREE", TableState.FREE, tableBusinessEntity.getState());
  }

  /**
   * Test method for {@link io.oasp.gastronomy.restaurant.tablemanagement.logic.domain.TableFactory#create()}.
   * Exceptional flow.
   */
  @Test
  public void testSetIdShouldThrowException() {

    // given
    final long tableId = new Random().nextLong();
    final TableEto source = TableEtoBuilder.tableEto().withId(tableId).build();

    // when
    this.testedTableFactory = new TableFactory(source, this.staffmanagement, this.salesmanagement);
    try {
      // and when
      this.testedTableFactory.create();
      fail("Exception was expected because of given Id");
    } catch (IllegalArgumentException expected) {
      // then
      assertTrue(expected.getMessage().toUpperCase().contains("ID"));
    }
  }

  /**
   * Test method for {@link io.oasp.gastronomy.restaurant.tablemanagement.logic.domain.TableFactory#create()}.
   * Exceptional flow.
   */
  @Test
  public void testNotAWaiterShouldThrowException() {

    // given
    final long waiterId = new Random().nextLong();
    final TableEto source = TableEtoBuilder.tableEto().withWaiterId(waiterId).build();
    final StaffMemberEto chief = StaffMemberEtoBuilder.staffMemberEto().withRole(Role.CHIEF).build();

    // when
    when(this.staffmanagement.findStaffMember(eq(waiterId))).thenReturn(chief);
    this.testedTableFactory = new TableFactory(source, this.staffmanagement, this.salesmanagement);
    try {
      // and when
      this.testedTableFactory.create();
      fail("Exception was expected because of wrong staff ROLE");
    } catch (IllegalArgumentException expected) {
      // then
      assertTrue(expected.getMessage().toUpperCase().contains("ROLE"));
    }
  }

}
