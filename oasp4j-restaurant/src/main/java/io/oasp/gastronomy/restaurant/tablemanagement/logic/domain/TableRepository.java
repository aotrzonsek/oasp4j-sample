package io.oasp.gastronomy.restaurant.tablemanagement.logic.domain;

import io.oasp.gastronomy.restaurant.general.common.domain.DomainRepository;
import io.oasp.gastronomy.restaurant.general.common.domain.Repository;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.to.TableEto;

/**
 * This type serves as a repository for {@link TableBusinessEntity}
 *
 * @author llaszkie
 */
@Repository
public interface TableRepository extends DomainRepository<TableBusinessEntity, TableEto> {

}
