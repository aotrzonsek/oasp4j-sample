package io.oasp.gastronomy.restaurant.tablemanagement.logic.domain;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import io.oasp.gastronomy.restaurant.general.common.api.exception.IllegalEntityStateException;
import io.oasp.gastronomy.restaurant.general.common.domain.BusinessEntity;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.Salesmanagement;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderEtoBuilder;
import io.oasp.gastronomy.restaurant.tablemanagement.common.api.Table;
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
 * Tests for {@link BusinessEntity} representing {@link Table} logic
 *
 * @author LLASZKIE
 */
public class TableBusinessEntityTest extends Assert {

  private TableBusinessEntity testedTableBusinessEntity;

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
   * Test method for
   * {@link io.oasp.gastronomy.restaurant.tablemanagement.logic.domain.TableBusinessEntity#canBeDeleted()}.
   */
  @Test
  public void testFreeTableCanBeDeleted() {

    // given
    TableEto tableData = TableEtoBuilder.tableEto().build();
    this.testedTableBusinessEntity = new TableBusinessEntity(tableData, this.salesmanagement);

    // when
    boolean canBeDeleted = this.testedTableBusinessEntity.canBeDeleted();

    // then
    assertTrue("Free table can be deleted", canBeDeleted);
  }

  /**
   * Test method for
   * {@link io.oasp.gastronomy.restaurant.tablemanagement.logic.domain.TableBusinessEntity#canBeDeleted()}.
   */
  @Test
  public void testOccupiedTableCanNotBeDeleted() {

    // given
    TableEto tableData = TableEtoBuilder.tableEto().build();
    this.testedTableBusinessEntity = new TableBusinessEntity(tableData, TableState.OCCUPIED, this.salesmanagement);

    // when
    boolean canBeDeleted = this.testedTableBusinessEntity.canBeDeleted();

    // then
    assertFalse("Occupied table can NOT be deleted", canBeDeleted);
  }

  /**
   * Test method for {@link io.oasp.gastronomy.restaurant.tablemanagement.logic.domain.TableBusinessEntity#getState()}.
   */
  @Test
  public void testGetStateShouldReturnFreeForNewTable() {

    // given
    TableEto tableData = TableEtoBuilder.tableEto().build();
    this.testedTableBusinessEntity = new TableBusinessEntity(tableData, this.salesmanagement);

    // when
    TableState state = this.testedTableBusinessEntity.getState();

    // then
    assertEquals(TableState.FREE, state);
  }

  /**
   * Test method for {@link io.oasp.gastronomy.restaurant.tablemanagement.logic.domain.TableBusinessEntity#getState()}.
   */
  @Test
  public void testGetStateShouldReturnOccupiedForTable() {

    // given
    TableEto tableData = TableEtoBuilder.tableEto().build();
    this.testedTableBusinessEntity = new TableBusinessEntity(tableData, TableState.OCCUPIED, this.salesmanagement);

    // when
    TableState state = this.testedTableBusinessEntity.getState();

    // then
    assertEquals(TableState.OCCUPIED, state);
  }

  /**
   * Test method for {@link io.oasp.gastronomy.restaurant.tablemanagement.logic.domain.TableBusinessEntity#getTable()}.
   */
  @Test
  public void testGetTable() {

    // given
    TableEto tableData = TableEtoBuilder.tableEto().withId(new Random().nextLong()).build();
    this.testedTableBusinessEntity = new TableBusinessEntity(tableData, this.salesmanagement);

    // when
    TableEto tableProjection = this.testedTableBusinessEntity.getTable();

    // then
    assertEquals(tableData.getId(), tableProjection.getId());
    assertNotSame(tableData, tableProjection);
  }

  /**
   * Test method for
   * {@link io.oasp.gastronomy.restaurant.tablemanagement.logic.domain.TableBusinessEntity#isTableReleasable()}.
   */
  @Test
  public void testNotOccupiedTableIsReleasable() {

    // given
    TableEto tableData = TableEtoBuilder.tableEto().build();
    this.testedTableBusinessEntity = new TableBusinessEntity(tableData, this.salesmanagement);

    // when
    boolean isTableReleasable = this.testedTableBusinessEntity.isTableReleasable();

    // then
    assertTrue(isTableReleasable);
  }

  /**
   * Test method for
   * {@link io.oasp.gastronomy.restaurant.tablemanagement.logic.domain.TableBusinessEntity#isTableReleasable()}.
   */
  @Test
  public void testTableWithoutOrderIsReleasable() {

    // given
    long tableId = new Random().nextLong();
    TableEto tableData = TableEtoBuilder.tableEto().withId(tableId).build();
    this.testedTableBusinessEntity = new TableBusinessEntity(tableData, TableState.OCCUPIED, this.salesmanagement);

    // when
    when(this.salesmanagement.findOpenOrderForTable(eq(tableId))).thenReturn(null);
    boolean isTableReleasable = this.testedTableBusinessEntity.isTableReleasable();

    // then
    assertTrue(isTableReleasable);
  }

  /**
   * Test method for
   * {@link io.oasp.gastronomy.restaurant.tablemanagement.logic.domain.TableBusinessEntity#isTableReleasable()}.
   */
  @Test
  public void testTableWithOpenOrderIsNotReleasable() {

    // given
    long tableId = new Random().nextLong();
    TableEto tableData = TableEtoBuilder.tableEto().withId(tableId).build();
    this.testedTableBusinessEntity = new TableBusinessEntity(tableData, TableState.OCCUPIED, this.salesmanagement);

    // when
    when(this.salesmanagement.findOpenOrderForTable(eq(tableId))).thenReturn(OrderEtoBuilder.orderEto().build());
    boolean isTableReleasable = this.testedTableBusinessEntity.isTableReleasable();

    // then
    assertFalse(isTableReleasable);
  }

  /**
   * Test method for
   * {@link io.oasp.gastronomy.restaurant.tablemanagement.logic.domain.TableBusinessEntity#markTableAs(io.oasp.gastronomy.restaurant.tablemanagement.common.api.datatype.TableState)}
   * .
   */
  @Test
  public void testMarkTableAsDoesNotChangeState() {

    // given
    TableEto tableData = TableEtoBuilder.tableEto().build();
    this.testedTableBusinessEntity = new TableBusinessEntity(tableData, TableState.OCCUPIED, this.salesmanagement);

    // when
    this.testedTableBusinessEntity.markTableAs(TableState.OCCUPIED);

    // then
    assertEquals(TableState.OCCUPIED, this.testedTableBusinessEntity.getState());
  }

  /**
   * Test method for
   * {@link io.oasp.gastronomy.restaurant.tablemanagement.logic.domain.TableBusinessEntity#markTableAs(io.oasp.gastronomy.restaurant.tablemanagement.common.api.datatype.TableState)}
   * .
   */
  @Test
  public void testMarkTableAsShouldChangeState() {

    // given
    TableEto tableData = TableEtoBuilder.tableEto().build();
    this.testedTableBusinessEntity = new TableBusinessEntity(tableData, TableState.FREE, this.salesmanagement);

    // when
    this.testedTableBusinessEntity.markTableAs(TableState.RESERVED);

    // then
    assertEquals(TableState.RESERVED, this.testedTableBusinessEntity.getState());
  }

  /**
   * Test method for
   * {@link io.oasp.gastronomy.restaurant.tablemanagement.logic.domain.TableBusinessEntity#markTableAs(io.oasp.gastronomy.restaurant.tablemanagement.common.api.datatype.TableState)}
   * .
   */
  @Test
  public void testMarkTableAsReservedShouldThrowExceptionForOccupiedTable() {

    // given
    TableEto tableData = TableEtoBuilder.tableEto().build();
    this.testedTableBusinessEntity = new TableBusinessEntity(tableData, TableState.OCCUPIED, this.salesmanagement);

    // when
    try {
      this.testedTableBusinessEntity.markTableAs(TableState.RESERVED);
      fail("Expected exception was not thrown!");
    } catch (IllegalEntityStateException expected) {
      // then
    }
  }

  /**
   * Test method for
   * {@link io.oasp.gastronomy.restaurant.tablemanagement.logic.domain.TableBusinessEntity#markTableAs(io.oasp.gastronomy.restaurant.tablemanagement.common.api.datatype.TableState)}
   * .
   */
  @Test
  public void testMarkTableAsFreeShouldThrowExceptionForTableWithOpenOrder() {

    // given
    long tableId = new Random().nextLong();
    TableEto tableData = TableEtoBuilder.tableEto().withId(tableId).build();
    this.testedTableBusinessEntity = new TableBusinessEntity(tableData, TableState.OCCUPIED, this.salesmanagement);

    // when
    when(this.salesmanagement.findOpenOrderForTable(eq(tableId))).thenReturn(OrderEtoBuilder.orderEto().build());
    try {
      // and when
      this.testedTableBusinessEntity.markTableAs(TableState.FREE);
      fail("Expected exception was not thrown!");
    } catch (IllegalEntityStateException expected) {
      // then
    }
  }

}
