package io.oasp.gastronomy.restaurant.salesmanagement.common.api;

import io.oasp.gastronomy.restaurant.general.common.api.ApplicationEntity;
import io.oasp.gastronomy.restaurant.general.common.api.datatype.Money;

import java.util.List;

/**
 * This is the interface for a {@link Bill}. It represents the actual payment for an {@link Order}.
 *
 * @author hohwille
 */
public interface Bill extends ApplicationEntity {

  /**
   * @return the total amount that has to be payed for this {@link Bill}.
   */
  Money getTotalAmount();

  /**
   * @param totalAmount is the new {@link #getTotalAmount() total amount}.
   */
  void setTotalAmount(Money totalAmount);

  /**
   * @return the tip (voluntary payment in addition to {@link #getTotalAmount() total amount}).
   */
  Money getTip();

  /**
   * @param tip is the new {@link #getTip() tip}.
   */
  void setTip(Money tip);

  /**
   * @return <code>true</code> if this {@link Bill} has been payed, <code>false</code> otherwise.
   */
  boolean isPayed();

  /**
   * @param payed is the new value of {@link #isPayed() payed}.
   */
  void setPayed(boolean payed);

  /**
   * @return the {@link List} with the {@link OrderPosition#getId() IDs} of the {@link OrderPosition}s.
   */
  List<Long> getOrderPositionIds();

  /**
   * @param ids are the new {@link #getOrderPositionIds() orderPositionIds}.
   */
  void setOrderPositionIds(List<Long> ids);

}
