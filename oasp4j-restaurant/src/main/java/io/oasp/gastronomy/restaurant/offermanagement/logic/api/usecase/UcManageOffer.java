package io.oasp.gastronomy.restaurant.offermanagement.logic.api.usecase;

import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferEto;

/**
 * Interface for the usecsae to manage an {@link OfferEto offer}.
 *
 * @author mvielsac
 */
public interface UcManageOffer {

  /**
   * Deletes an {@link OfferEto} by its {@link OfferEto#getId() id}.
   *
   * @param offerId is the {@link OfferEto#getId() id} that identifies the {@link OfferEto} to be deleted.
   */
  void deleteOffer(Long offerId);

  /**
   * Saves an {@link OfferEto}. If this {@link OfferEto} does not exist, an exception will be thrown.
   *
   * @param offer the {@link OfferEto} to persist.
   */
  void updateOffer(OfferEto offer);

  /**
   * Creates the {@link OfferEto} for the first time.
   *
   * @param offer the {@link OfferEto} to persist.
   */
  void createOffer(OfferEto offer);

}
