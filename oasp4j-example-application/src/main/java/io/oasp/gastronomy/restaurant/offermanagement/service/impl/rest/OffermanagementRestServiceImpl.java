package io.oasp.gastronomy.restaurant.offermanagement.service.impl.rest;

import io.oasp.gastronomy.restaurant.general.common.api.constants.PermissionConstants;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.Offermanagement;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.DrinkEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.MealEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferFilter;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferSortBy;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.ProductEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.ProductFilter;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.ProductSortBy;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.SideDishEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.usecase.UcFindOffer;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.usecase.UcFindProduct;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.usecase.UcManageOffer;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.usecase.UcManageProduct;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

/**
 * This class contains methods for REST calls. Some URI structures may seem depricated, but in fact are not. See the
 * correspondent comments on top.
 *
 * @author agreul
 */
@Path("/offermanagement")
@Named("OffermanagementRestService")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Transactional
@Validated
public class OffermanagementRestServiceImpl {

  private Offermanagement offerManagement;

  /**
   * @param offerManagement the offerManagement to be set
   */
  @Inject
  public void setOfferManagement(Offermanagement offerManagement) {

    this.offerManagement = offerManagement;
  }

  /**
   * Delegates to {@link UcFindOffer#findOffer}.
   *
   * @param id the ID of the {@link OfferEto}
   * @return the {@link OfferEto}
   */
  @GET
  @Path("/offer/{id}")
  @RolesAllowed(PermissionConstants.FIND_OFFER)
  public OfferEto getOffer(@PathParam("id") Long id) {

    return this.offerManagement.findOffer(id);
  }

  /**
   * Delegates to {@link UcManageOffer#saveOffer}.
   *
   * @param offer the {@link OfferEto} to save
   *
   * @return the saved {@link OfferEto}
   */
  @POST
  @Path("/offer/")
  @RolesAllowed(PermissionConstants.SAVE_OFFER)
  public OfferEto saveOffer(@Valid OfferEto offer) {

    return this.offerManagement.saveOffer(offer);
  }

  // although id in path is redundant, this structure is intentionally chosen
  // for further reasons behind this decision see one of the other ***ManagementRestServiceImpl
  /**
   * Delegates to {@link UcManageOffer#saveOffer}.
   *
   * @param offer the {@link OfferEto} to be updated
   *
   * @return the updated {@link OfferEto}
   */
  @PUT
  @Path("/offer/{id}")
  @RolesAllowed(PermissionConstants.SAVE_OFFER)
  @Deprecated
  public OfferEto updateOffer(OfferEto offer) {

    return this.offerManagement.saveOffer(offer);
  }

  /**
   * Delegates to {@link UcFindOffer#findAllOffers}.
   *
   * @return all {@link OfferEto}s as list
   */
  @GET
  @Path("/offer/")
  @RolesAllowed(PermissionConstants.FIND_OFFER)
  public List<OfferEto> getAllOffers() {

    return this.offerManagement.findAllOffers();
  }

  /**
   * Delegates to {@link UcFindProduct#findAllProducts}.
   *
   * @return all {@link ProductEto}s as list
   */
  @GET
  @Path("/product/")
  @RolesAllowed(PermissionConstants.FIND_PRODUCT)
  public List<ProductEto> getAllProducts() {

    return this.offerManagement.findAllProducts();
  }

  /**
   * Delegates to {@link UcManageProduct#saveProduct}.
   *
   * @param product the product to save
   * @return the saved product
   */
  @POST
  @Path("/product/")
  @RolesAllowed(PermissionConstants.SAVE_PRODUCT)
  public ProductEto saveProduct(ProductEto product) {

    return this.offerManagement.saveProduct(product);
  }

  /**
   * Delegates to {@link UcFindProduct#findAllMeals}.
   *
   * @return all {@link MealEto}s as list
   */
  @GET
  @Path("/product/meal/")
  @RolesAllowed(PermissionConstants.FIND_PRODUCT)
  public List<MealEto> getAllMeals() {

    return this.offerManagement.findAllMeals();
  }

  /**
   * Delegates to {@link UcFindProduct#findAllDrinks}.
   *
   * @return all {@link DrinkEto}s as list
   */
  @GET
  @Path("/product/drink/")
  @RolesAllowed(PermissionConstants.FIND_PRODUCT)
  public List<DrinkEto> getAllDrinks() {

    return this.offerManagement.findAllDrinks();
  }

  /**
   * Delegates to {@link UcFindProduct#findAllSideDishes}.
   *
   * @return all {@link SideDishEto}s as list
   */
  @GET
  @Path("/product/side/")
  @RolesAllowed(PermissionConstants.FIND_PRODUCT)
  public List<SideDishEto> getAllSideDishes() {

    return this.offerManagement.findAllSideDishes();
  }

  /**
   * Delegates to {@link UcManageOffer#deleteOffer}.
   *
   * @param id ID of the {@link OfferEto} to delete
   */
  @DELETE
  @Path("/offer/{id}")
  @RolesAllowed(PermissionConstants.DELETE_OFFER)
  public void deleteOffer(@PathParam("id") Long id) {

    this.offerManagement.deleteOffer(id);
  }

  /**
   * Delegates to {@link UcFindProduct#findProduct}.
   *
   * @param id ID of the {@link ProductEto}
   * @return the {@link ProductEto}
   */
  @GET
  @Path("/product/{id}")
  @RolesAllowed(PermissionConstants.FIND_PRODUCT)
  public ProductEto findProduct(@PathParam("id") Long id) {

    return this.offerManagement.findProduct(id);
  }

  // although id in path is redundant, this structure is intentionally chosen
  // for further reasons behind this decision see one of the other
  // *ManagementRestServiceImpl
  /**
   * Delegates to {@link UcManageProduct#saveProduct}.
   *
   * @param product the {@link ProductEto} to be updated
   */
  @PUT
  @Path("/product/{id}")
  @RolesAllowed(PermissionConstants.SAVE_PRODUCT)
  @Deprecated
  public void updateProduct(ProductEto product) {

    this.offerManagement.saveProduct(product);
  }

  /**
   * Delegates to {@link UcFindOffer#isProductInUseByOffer}.
   *
   * @param id ID of the {@link ProductEto}
   * @return true, if there are no offers, that use the given ProductEto. false otherwise.
   */
  @GET
  @Path("/product/{id}/inuse")
  @RolesAllowed({ PermissionConstants.FIND_OFFER, PermissionConstants.FIND_OFFER })
  public boolean isProductInUseByOffer(@PathParam("id") Long id) {

    return this.offerManagement.isProductInUseByOffer(findProduct(id));
  }

  /**
   * Delegates to {@link UcManageProduct#deleteProduct}.
   *
   * @param id ID of the ProductEto to delete
   */
  @DELETE
  @Path("/product/{id}")
  @RolesAllowed(PermissionConstants.DELETE_PRODUCT)
  public void deleteProduct(@PathParam("id") Long id) {

    this.offerManagement.deleteProduct(id);
  }

  /**
   * Delegates to {@link UcFindOffer#findOffersFiltered}.
   *
   * @param offerFilter the offers filter criteria
   * @param sortBy sorting specification
   * @return list with all {@link OfferEto}s that match the {@link OfferFilter} criteria
   */
  @GET
  @Path("/sortby/{sortBy}")
  @RolesAllowed(PermissionConstants.FIND_OFFER)
  public List<OfferEto> getFilteredOffers(OfferFilter offerFilter, @PathParam("sortBy") OfferSortBy sortBy) {

    return this.offerManagement.findOffersFiltered(offerFilter, sortBy);
  }

  /**
   * Delegates to {@link UcFindProduct#findProductsFiltered}.
   *
   * @param productFilter filter specification
   * @param sortBy sorting specification
   * @return list with all {@link ProductEto}s that match the {@link ProductFilter} criteria
   */
  @GET
  @Path("/product/sortby/{sortBy}")
  @RolesAllowed(PermissionConstants.FIND_OFFER)
  public List<ProductEto> getFilteredProducts(ProductFilter productFilter, @PathParam("sortBy") ProductSortBy sortBy) {

    return this.offerManagement.findProductsFiltered(productFilter, sortBy);
  }
}
