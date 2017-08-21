package com.shanpizza.platform.rest.resource;

import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.transaction.annotation.Transactional;

import com.shanpizza.platform.model.Coupon;


/**
 * The Interface CouponResource.
 * 
 * This interface is providing a REST API contract to perform operations on Coupon Repository.
 * API provides a mechanism to lookup coupons and redeem them as well as ability to create custom coupons.
 */
public interface CouponResource {
	
	/**
	 * Gets the all coupons.
	 *
	 * @return the all coupons
	 */
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Response getAllCoupons(@QueryParam("page") @DefaultValue("0") Integer page,
            @QueryParam("size") @DefaultValue("50") Integer size);
	
	/**
	 * Gets the coupon by code.
	 *
	 * @param code the code
	 * @return the coupon by code
	 */
	@GET
	@Path("/{code}")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getCouponByCode( @PathParam("code")String code);
	
	/**
	 * Redeem coupon.
	 *
	 * @param code the code
	 * @return the response
	 */
	@PUT
	@Path("/redeem/{code}")
	@Produces({MediaType.APPLICATION_JSON})
	@Transactional
	public Response redeemCoupon( @PathParam("code")String code);
	
	
	/**
	 * Creates the new coupon.
	 *
	 * @return the response
	 */
	@POST
	@Path("/create")
	@Produces({MediaType.APPLICATION_JSON})
	@Transactional
	public Response createNewCoupon();
	
	/**
	 * Creates the new coupon.
	 *
	 * @param coupon the coupon
	 * @return the response
	 */
	@POST
	@Path("/custom/create")
	@Produces({MediaType.APPLICATION_JSON})
	@Transactional
	public Response createNewCoupon(Coupon coupon);
	
	@GET
	@Path("/customers")
	@Produces({MediaType.APPLICATION_JSON})
	@Transactional
	public Response getCustomers(@QueryParam("page") @DefaultValue("0") Integer page,
            @QueryParam("size") @DefaultValue("50") Integer size);

}
