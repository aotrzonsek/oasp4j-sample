package io.oasp.gastronomy.restaurant.salesmanagement.dataaccess.impl.dao;

import io.oasp.gastronomy.restaurant.general.common.api.constants.NamedQueries;
import io.oasp.gastronomy.restaurant.general.dataaccess.base.dao.ApplicationDaoImpl;
import io.oasp.gastronomy.restaurant.salesmanagement.dataaccess.api.OrderPositionEntity;
import io.oasp.gastronomy.restaurant.salesmanagement.dataaccess.api.dao.OrderPositionDao;

import java.util.List;

import javax.inject.Named;

import com.mysema.query.alias.Alias;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.path.EntityPathBase;

/**
 * Implementation of {@link OrderPositionDao}.
 *
 * @author hohwille
 */
@Named
public class OrderPositionDaoImpl extends ApplicationDaoImpl<OrderPositionEntity> implements OrderPositionDao {

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<OrderPositionEntity> getEntityClass() {

    return OrderPositionEntity.class;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<OrderPositionEntity> findOrderPositionsByOrder(Long orderId) {

    OrderPositionEntity orderPosition = Alias.alias(OrderPositionEntity.class);
    EntityPathBase<OrderPositionEntity> alias = Alias.$(orderPosition);
    JPAQuery query = new JPAQuery(getEntityManager()).from(alias);
    query.where(Alias.$(orderPosition.getOrder().getId()).eq(orderId));
    return query.list(alias);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<OrderPositionEntity> findOpenOrderPositionsByOrder(Long orderId) {

    return getEntityManager()
        .createNamedQuery(NamedQueries.GET_OPEN_ORDER_POSITIONS_FOR_ORDER, OrderPositionEntity.class)
        .setParameter(1, orderId).getResultList();
  }

}
