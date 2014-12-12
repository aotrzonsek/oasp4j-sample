package io.oasp.gastronomy.restaurant.tablemanagement.services;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import io.oasp.gastronomy.restaurant.general.common.AbstractRestServiceTest;
import io.oasp.gastronomy.restaurant.tablemanagement.common.api.datatype.TableState;
import io.oasp.gastronomy.restaurant.tablemanagement.common.builders.TableEntityBuilder;
import io.oasp.gastronomy.restaurant.tablemanagement.common.builders.TableEtoBuilder;
import io.oasp.gastronomy.restaurant.tablemanagement.delivery.dataaccess.api.TableEntity;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.to.TableEto;
import io.oasp.gastronomy.restaurant.test.general.AppProperties.RestUrls;
import io.oasp.gastronomy.restaurant.test.general.webclient.ResponseData;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test table management rest service
 * 
 * @author hahmad, arklos, mbrunnli
 */
public class TableManagementRestServiceTest extends AbstractRestServiceTest {

  /**
   * Test get table
   * 
   * @throws Exception test fails
   */
  @Test
  public void getTableTest() throws Exception {

    TableEntity tableEntity =
        new TableEntityBuilder().number(10l).state(TableState.RESERVED)
            .persist(this.transactionTemplate, TableManagementRestServiceTest.this.em);

    ResponseData<TableEto> table =
        this.waiter.get(RestUrls.TableManagement.getGetTableUrl(tableEntity.getId()), TableEto.class);

    Assert.assertNotNull(table.getResponseObject());
    Assert.assertEquals(tableEntity.getNumber(), table.getResponseObject().getNumber());
    Assert.assertEquals(tableEntity.getState(), table.getResponseObject().getState());

  }

  /**
   * Test get all tables
   */
  @Test
  public void getAllTablesTest() {

    List<TableEntity> tableEntities =
        new TableEntityBuilder().persistAndDuplicate(TableManagementRestServiceTest.this.transactionTemplate,
            TableManagementRestServiceTest.this.em, 5);

    List<ResponseData<TableEto>> tableEtos =
        this.waiter.getAll(RestUrls.TableManagement.getAllTablesUrl(), TableEto.class);

    Assert.assertNotNull(tableEtos);
    Assert.assertEquals(tableEntities.size(), tableEtos.size());
  }

  /**
   * Test get all free tables
   */
  @Test
  public void getFreeTablesTest() {

    new TableEntityBuilder().state(TableState.OCCUPIED).persistAndDuplicate(
        TableManagementRestServiceTest.this.transactionTemplate, TableManagementRestServiceTest.this.em, 5);
    new TableEntityBuilder()
        .number(100l)
        .state(TableState.FREE)
        .persistAndDuplicate(TableManagementRestServiceTest.this.transactionTemplate,
            TableManagementRestServiceTest.this.em, 2);
    new TableEntityBuilder()
        .number(200l)
        .state(TableState.RESERVED)
        .persistAndDuplicate(TableManagementRestServiceTest.this.transactionTemplate,
            TableManagementRestServiceTest.this.em, 2);

    List<ResponseData<TableEto>> tableEtos =
        this.waiter.getAll(RestUrls.TableManagement.getFreeTablesUrl(), TableEto.class);

    Assert.assertNotNull(tableEtos);
    Assert.assertEquals(2, tableEtos.size());
  }

  /**
   * Test mark table as
   */
  @Test
  public void markTableAsTest() {

    TableEntity tableEntity =
        new TableEntityBuilder().state(TableState.RESERVED).persist(this.transactionTemplate, this.em);

    this.waiter.post(RestUrls.TableManagement.markTableAsUrl(tableEntity.getId(), TableState.OCCUPIED));
    ResponseData<TableEto> table =
        this.waiter.get(RestUrls.TableManagement.getGetTableUrl(tableEntity.getId()), TableEto.class);
    assertThat(table.getResponse().getStatus(), is(200));
    assertThat(table.getResponseObject().getState(), is(TableState.OCCUPIED));

    this.waiter.post(RestUrls.TableManagement.markTableAsUrl(tableEntity.getId(), TableState.FREE));
    table = this.waiter.get(RestUrls.TableManagement.getGetTableUrl(tableEntity.getId()), TableEto.class);
    assertThat(table.getResponse().getStatus(), is(200));
    assertThat(table.getResponseObject().getState(), is(TableState.FREE));
  }

  /**
   * Test create table
   */
  @Test
  public void createTableTest() {

    this.chief.post(RestUrls.TableManagement.getCreateTableUrl(),
        new TableEtoBuilder().number(10l).state(TableState.RESERVED).createNew());

    List<ResponseData<TableEto>> tableEtos =
        this.waiter.getAll(RestUrls.TableManagement.getAllTablesUrl(), TableEto.class);

    Assert.assertNotNull(tableEtos);
    Assert.assertEquals(1, tableEtos.size());
    TableEto result = tableEtos.get(0).getResponseObject();
    Assert.assertNotNull(result);
    Assert.assertEquals(new Long(10), result.getNumber());
    Assert.assertEquals(TableState.RESERVED, result.getState());
  }

  /**
   * Test delete table
   */
  @Test
  public void deleteTableTest() {

    TableEntity tableToBeDeleted = new TableEntityBuilder().persist(this.transactionTemplate, this.em);

    this.chief.delete(RestUrls.TableManagement.getDeleteTableUrl(tableToBeDeleted.getId()));

    List<ResponseData<TableEto>> tableEtos =
        this.waiter.getAll(RestUrls.TableManagement.getAllTablesUrl(), TableEto.class);

    Assert.assertNotNull(tableEtos);
    Assert.assertEquals(0, tableEtos.size());
  }

  /**
   * Test is table releasable
   */
  @Test
  public void isTableReleaseable() {

    TableEntity tableTarget =
        new TableEntityBuilder().state(TableState.OCCUPIED).persist(this.transactionTemplate, this.em);

    ResponseData<Boolean> response =
        this.waiter.get(RestUrls.TableManagement.isTableReleasableUrl(tableTarget.getId()), Boolean.class);

    assertThat(response.getResponseObject(), is(true));
  }

}
