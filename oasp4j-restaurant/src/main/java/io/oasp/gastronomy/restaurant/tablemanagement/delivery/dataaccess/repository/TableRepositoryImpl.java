package io.oasp.gastronomy.restaurant.tablemanagement.delivery.dataaccess.repository;

import io.oasp.gastronomy.restaurant.general.common.base.AbstractBeanMapperSupport;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.Salesmanagement;
import io.oasp.gastronomy.restaurant.tablemanagement.delivery.dataaccess.api.TableEntity;
import io.oasp.gastronomy.restaurant.tablemanagement.delivery.dataaccess.api.dao.TableDao;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.to.TableEto;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.domain.TableBusinessEntity;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.domain.TableRepository;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * This type is a DAO-based implementation of {@link TableBusinessEntity} Repository
 *
 * @author llaszkie
 */
@Named
public class TableRepositoryImpl extends AbstractBeanMapperSupport implements TableRepository {

  private TableDao tableDao;

  private Salesmanagement salesmanagement;

  /**
   * {@inheritDoc}
   */
  @Override
  public void delete(TableBusinessEntity table) {

    this.tableDao.delete(table.getTable().getId());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TableEto save(TableBusinessEntity table) {

    TableEntity tableEntity = getBeanMapper().map(table.getTable(), TableEntity.class);
    this.tableDao.save(tableEntity);
    return getBeanMapper().map(tableEntity, TableEto.class);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TableBusinessEntity find(Long id) {

    TableEntity tableEntity = this.tableDao.findOne(id);
    if (tableEntity != null) {
      TableEto tableData = getBeanMapper().map(tableEntity, TableEto.class);
      return new TableBusinessEntity(tableData, tableData.getState(), this.salesmanagement);
    }
    return null;
  }

  /**
   * @param tableDao the {@link TableDao} to {@link Inject}.
   */
  @Inject
  public void setTableDao(TableDao tableDao) {

    this.tableDao = tableDao;
  }

  /**
   * @param salesmanagement the {@link Salesmanagement} to {@link Inject}.
   */
  @Inject
  public void setSalesmanagement(Salesmanagement salesmanagement) {

    this.salesmanagement = salesmanagement;
  }
}
