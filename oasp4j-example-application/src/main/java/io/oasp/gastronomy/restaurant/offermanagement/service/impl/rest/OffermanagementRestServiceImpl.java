package io.oasp.gastronomy.restaurant.offermanagement.service.impl.rest;

import io.oasp.gastronomy.restaurant.general.common.api.constants.PermissionConstants;
import io.oasp.gastronomy.restaurant.general.logic.api.to.BinaryObjectEto;
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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
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

import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;
import org.apache.cxf.jaxrs.ext.multipart.MultipartBody;
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
   * {@inheritDoc}
   */
  @SuppressWarnings("javadoc")
  @Produces(MediaType.APPLICATION_JSON)
  @GET
  @Path("/offer/{id}")
  @RolesAllowed(PermissionConstants.FIND_OFFER)
  public OfferEto getOffer(@PathParam("id") Long id) {

    return this.offerManagement.findOffer(id);
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("javadoc")
  @Consumes(MediaType.APPLICATION_JSON)
  @POST
  @Path("/offer/")
  @RolesAllowed(PermissionConstants.CREATE_OFFER)
  public void createOffer(@Valid OfferEto offer) {

    this.offerManagement.createOffer(offer);
  }

  // although id in path is redundant, this structure is intentionally chosen
  // for further reasons behind this decision see one of the other ***ManagementRestServiceImpl
  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("javadoc")
  @Consumes(MediaType.APPLICATION_JSON)
  @PUT
  @Path("/offer/{id}")
  @RolesAllowed(PermissionConstants.UPDATE_OFFER)
  public void updateOffer(OfferEto offer) {

    this.offerManagement.updateOffer(offer);
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("javadoc")
  @Produces(MediaType.APPLICATION_JSON)
  @GET
  @Path("/offer/")
  @RolesAllowed(PermissionConstants.FIND_OFFER)
  public List<OfferEto> getAllOffers() {

    return this.offerManagement.findAllOffers();
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("javadoc")
  @Produces(MediaType.APPLICATION_JSON)
  @GET
  @Path("/product/")
  @RolesAllowed(PermissionConstants.FIND_PRODUCT)
  public List<ProductEto> getAllProducts() {

    return this.offerManagement.findAllProducts();
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("javadoc")
  @Consumes(MediaType.APPLICATION_JSON)
  @POST
  @Path("/product/")
  @RolesAllowed(PermissionConstants.CREATE_PRODUCT)
  public void createProduct(ProductEto product) {

    this.offerManagement.createProduct(product);
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("javadoc")
  @Produces(MediaType.APPLICATION_JSON)
  @GET
  @Path("/product/meal/")
  @RolesAllowed(PermissionConstants.FIND_PRODUCT)
  public List<MealEto> getAllMeals() {

    return this.offerManagement.findAllMeals();
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("javadoc")
  @Produces(MediaType.APPLICATION_JSON)
  @GET
  @Path("/product/drink/")
  @RolesAllowed(PermissionConstants.FIND_PRODUCT)
  public List<DrinkEto> getAllDrinks() {

    return this.offerManagement.findAllDrinks();
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("javadoc")
  @Produces(MediaType.APPLICATION_JSON)
  @GET
  @Path("/product/side/")
  @RolesAllowed(PermissionConstants.FIND_PRODUCT)
  public List<SideDishEto> getAllSideDishes() {

    return this.offerManagement.findAllSideDishes();
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("javadoc")
  @DELETE
  @Path("/offer/{id}")
  @RolesAllowed(PermissionConstants.DELETE_OFFER)
  public void deleteOffer(@PathParam("id") Long id) {

    this.offerManagement.deleteOffer(id);
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("javadoc")
  @Produces(MediaType.APPLICATION_JSON)
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
   * {@inheritDoc}
   */
  @SuppressWarnings("javadoc")
  @Consumes(MediaType.APPLICATION_JSON)
  @PUT
  @Path("/product/{id}")
  @RolesAllowed(PermissionConstants.UPDATE_PRODUCT)
  public void updateProduct(ProductEto product) {

    this.offerManagement.updateProduct(product);
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("javadoc")
  @GET
  @Path("/product/{id}/inuse")
  @RolesAllowed({ PermissionConstants.FIND_OFFER, PermissionConstants.FIND_OFFER })
  public boolean isProductInUseByOffer(@PathParam("id") Long id) {

    return this.offerManagement.isProductInUseByOffer(findProduct(id));
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("javadoc")
  @DELETE
  @Path("/product/{id}")
  @RolesAllowed(PermissionConstants.DELETE_PRODUCT)
  public void deleteProduct(@PathParam("id") Long id) {

    this.offerManagement.deleteProduct(id);
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("javadoc")
  @Consumes(MediaType.APPLICATION_JSON)
  @GET
  @Path("/sortby/{sortBy}")
  @RolesAllowed(PermissionConstants.FIND_OFFER)
  public List<OfferEto> getFilteredOffers(OfferFilter offerFilterBo, @PathParam("sortBy") OfferSortBy sortBy) {

    return this.offerManagement.findOffersFiltered(offerFilterBo, sortBy);
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("javadoc")
  @Consumes(MediaType.APPLICATION_JSON)
  @GET
  @Path("/product/sortby/{sortBy}")
  @RolesAllowed(PermissionConstants.FIND_OFFER)
  public List<ProductEto> getFilteredProducts(ProductFilter productFilterBo, @PathParam("sortBy") ProductSortBy sortBy) {

    return this.offerManagement.findProductsFiltered(productFilterBo, sortBy);
  }

  @SuppressWarnings("javadoc")
  @Consumes("multipart/mixed")
  @POST
  @Path("/product/{id}/picture")
  @RolesAllowed(PermissionConstants.UPDATE_PRODUCT)
  public void updateProductPicture(@PathParam("id") Long productId,
      @Multipart(value = "binaryObjectEto", type = MediaType.APPLICATION_JSON) BinaryObjectEto binaryObjectEto,
      @Multipart(value = "blob", type = MediaType.APPLICATION_OCTET_STREAM) InputStream picture)
      throws SerialException, SQLException, IOException {

    Blob blob = new SerialBlob(IOUtils.readBytesFromStream(picture));
    this.offerManagement.updateProductPicture(productId, blob, binaryObjectEto);

  }

  @SuppressWarnings("javadoc")
  @Produces("multipart/mixed")
  @GET
  @Path("/product/{id}/picture")
  @RolesAllowed(PermissionConstants.FIND_PRODUCT)
  public MultipartBody getProductPicture(@PathParam("id") long productId) throws SQLException, IOException {

    Blob blob = this.offerManagement.findProductPictureBlob(productId);
    byte[] data = IOUtils.readBytesFromStream(blob.getBinaryStream());

    List<Attachment> atts = new LinkedList<>();
    atts.add(new Attachment("binaryObjectEto", MediaType.APPLICATION_JSON, this.offerManagement
        .findProductPicture(productId)));
    atts.add(new Attachment("blob", MediaType.APPLICATION_OCTET_STREAM, new ByteArrayInputStream(data)));
    return new MultipartBody(atts, true);

  }

  @SuppressWarnings("javadoc")
  @DELETE
  @Path("/product/{id}/picture")
  @RolesAllowed(PermissionConstants.UPDATE_PRODUCT)
  public void deleteProductPicture(long productId) {

    this.offerManagement.deleteProductPicture(productId);
  }
}
