package io.oasp.gastronomy.restaurant.general.common.domain;

import io.oasp.gastronomy.restaurant.general.common.api.to.AbstractEto;

/**
 * Interface for all domain repositories of this application.
 *
 * @param <BUSINESS_ENTITY> is the generic type of the {@link BusinessEntity}
 * @param <TO> is the generic type of the {@link AbstractEto}
 *
 * @author llaszkie
 */
@Repository
public interface DomainRepository<BUSINESS_ENTITY, TO extends AbstractEto> {

  /**
   * Persists given business entity.
   *
   * @param business_entity to be persisted
   * @return Transfer Object representing persisted data
   *
   */
  public TO save(BUSINESS_ENTITY business_entity);

  /**
   * Deletes given business entity.
   *
   * @param business_entity to be deleted
   *
   */
  public void delete(BUSINESS_ENTITY business_entity);

  /**
   * Retrieves a business entity by its id.
   *
   * @param id must not be {@literal null}.
   * @return the entity with the given id or {@literal null} if none found
   */
  public BUSINESS_ENTITY find(Long id);

}
