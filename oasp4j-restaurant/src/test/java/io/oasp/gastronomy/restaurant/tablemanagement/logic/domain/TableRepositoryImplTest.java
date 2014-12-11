package io.oasp.gastronomy.restaurant.tablemanagement.logic.domain;

import io.oasp.gastronomy.restaurant.general.common.AbstractSpringIntegrationTest;
import io.oasp.gastronomy.restaurant.tablemanagement.common.api.Table;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.to.TableEto;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.to.TableEtoBuilder;
import io.oasp.module.configuration.common.api.ApplicationConfigurationConstants;

import java.util.Random;

import javax.inject.Inject;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

/**
 * Tests for DAO-Repository implementation for {@link TableBusinessEntity}
 *
 * @author llaszkie
 */
@ContextConfiguration({ ApplicationConfigurationConstants.BEANS_LOGIC })
public class TableRepositoryImplTest extends AbstractSpringIntegrationTest {

  /**
   * Id of existing {@link Table}
   */
  private static final long SAMPLE_TABLE_ID = 101L;

  @Inject
  private TableRepository testedTableRepository;

  /**
   * Test method for
   * {@link io.oasp.gastronomy.restaurant.tablemanagement.delivery.dataaccess.repository.TableRepositoryImpl#delete(io.oasp.gastronomy.restaurant.tablemanagement.logic.domain.TableBusinessEntity)}
   * .
   */
  @Test
  public void testDelete() {

    // given
    long tableId = SAMPLE_TABLE_ID;
    TableBusinessEntity tableBusinessEntity = this.testedTableRepository.find(tableId);

    // when
    this.testedTableRepository.delete(tableBusinessEntity);

    // then
    assertNull(this.testedTableRepository.find(tableId));

  }

  /**
   * Test method for
   * {@link io.oasp.gastronomy.restaurant.tablemanagement.delivery.dataaccess.repository.TableRepositoryImpl#save(io.oasp.gastronomy.restaurant.tablemanagement.logic.domain.TableBusinessEntity)}
   * .
   */
  @Test
  public void testSave() {

    // given
    TableEto tableData = TableEtoBuilder.tableEto().withId(new Random().nextLong()).build();
    TableBusinessEntity tableBusinessEntity = new TableBusinessEntity(tableData, null);

    // when
    TableEto savedTable = this.testedTableRepository.save(tableBusinessEntity);

    // then
    assertEquals(tableData.getId(), savedTable.getId());
    assertNotNull(savedTable.getModificationCounter());
  }

  /**
   * Test method for
   * {@link io.oasp.gastronomy.restaurant.tablemanagement.delivery.dataaccess.repository.TableRepositoryImpl#find(java.lang.Long)}
   * .
   */
  @Test
  public void testFind() {

    // given
    long tableId = 101L;

    // when
    TableBusinessEntity tableBusinessEntity = this.testedTableRepository.find(tableId);

    // then
    assertNotNull(tableBusinessEntity);
    assertEquals(tableId, tableBusinessEntity.getTable().getId().longValue());

  }

}
