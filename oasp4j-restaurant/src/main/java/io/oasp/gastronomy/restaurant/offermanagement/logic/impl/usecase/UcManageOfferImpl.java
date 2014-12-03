package io.oasp.gastronomy.restaurant.offermanagement.logic.impl.usecase;

import io.oasp.gastronomy.restaurant.offermanagement.common.api.Offer;
import io.oasp.gastronomy.restaurant.offermanagement.common.api.exception.OfferEmptyException;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.OfferEntity;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.usecase.UcManageOffer;
import io.oasp.gastronomy.restaurant.offermanagement.logic.base.usecase.AbstractOfferUc;

import java.util.Objects;

import javax.inject.Named;

import net.sf.mmm.util.exception.api.DuplicateObjectException;

/**
 * Implementation of {@link UcManageOffer}.
 *
 * @author loverbec
 */
@Named
public class UcManageOfferImpl extends AbstractOfferUc implements UcManageOffer {

  /**
   * {@inheritDoc}
   */
  @Override
  public void deleteOffer(Long offerId) {

    getOfferDao().delete(offerId);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void updateOffer(OfferEto offer) {

    Objects.requireNonNull(offer, "offer");

    OfferEntity persistedOffer = getBeanMapper().map(offer, OfferEntity.class);
    getOfferDao().save(persistedOffer);
    return;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void createOffer(OfferEto offer) {

    Objects.requireNonNull(offer, "offer");

    Long offerId = offer.getId();

    OfferEntity persistedOffer = getOfferDao().findOne(offerId);
    if (persistedOffer != null) {
      throw new DuplicateObjectException(Offer.class, offerId);
    }

    if ((offer.getMealId() == null) && (offer.getDrinkId() == null) && (offer.getSideDishId() == null)) {
      throw new OfferEmptyException(offer);
    } else {
      getOfferDao().save(getBeanMapper().map(offer, OfferEntity.class));
      return;
    }

  }

}
