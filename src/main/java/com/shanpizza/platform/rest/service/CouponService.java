package com.shanpizza.platform.rest.service;

import java.util.List;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.shanpizza.platform.dao.CouponDAO;
import com.shanpizza.platform.dao.CustomerDAO;
import com.shanpizza.platform.model.Coupon;
import com.shanpizza.platform.model.Customer;
import com.shanpizza.platform.model.Error;
import com.shanpizza.platform.rest.resource.CouponResource;
import com.shanpizza.platform.rest.validator.CouponValidator;

import utils.CouponGenerator;

/**
 * The Class CouponService.
 * 
 * This class implements CouponResource interface.  
*  
*  Implementation is providing a REST API's to perform operations on Coupon Repository.
 * API provides a mechanism to lookup coupons and redeem them as well as ability to create custom coupons.
* 
 */
@Component
@Path("/coupons")
public class CouponService implements CouponResource {

	/** The coupon repo. */
	@Autowired
	private CouponDAO couponRepo;
	
	@Autowired
	private CustomerDAO customerRepo;

	/** The coupon validator. */
	@Autowired
	private CouponValidator couponValidator;

	/* (non-Javadoc)
	 * @see com.shanpizza.platform.rest.resource.CouponResource#getAllCoupons()
	 */
	@Override
	public Response getAllCoupons(Integer page, Integer size) {
		Response response = null;
		try {
			Pageable pageable = new PageRequest(page, size);
			List<Coupon> coupons = couponRepo.findAll(pageable).getContent();
			response = Response.status(Status.OK).entity(coupons).build();
		} catch (Exception ex) {
			Error error = new Error();
			error.setCode("CPS-001");
			error.setMessage(ex.getMessage());
			error.setType("System");

			response = Response.status(Status.INTERNAL_SERVER_ERROR).entity(error).build();
		}

		return response;
	}

	/* (non-Javadoc)
	 * @see com.shanpizza.platform.rest.resource.CouponResource#getCouponByCode(java.lang.String)
	 */
	@Override
	public Response getCouponByCode(String code) {
		Response response = null;
		try {

			if (StringUtils.isEmpty(code)) {

				Error error = new Error();
				error.setCode("CPS-004");
				error.setMessage("Coupon code is required");
				error.setType("Functional");

				response = Response.status(Status.BAD_REQUEST).entity(error).build();

			} else {
				Coupon coupon = couponRepo.getCoupon(code);
				if (coupon != null) {
					response = Response.status(Status.OK).entity(coupon).build();
				} else {
					Error error = new Error();
					error.setCode("CPS-002");
					error.setMessage("Invalid coupon code");
					error.setType("Functional");
					response = Response.status(Status.BAD_REQUEST).entity(error).build();
				}
			}
		} catch (Exception ex) {
			Error error = new Error();
			error.setCode("CPS-001");
			error.setMessage(ex.getMessage());
			error.setType("System");
			ex.printStackTrace();
			response = Response.status(Status.INTERNAL_SERVER_ERROR).entity(error).build();
		}

		return response;
	}

	/* (non-Javadoc)
	 * @see com.shanpizza.platform.rest.resource.CouponResource#redeemCoupon(java.lang.String)
	 */
	@Override
	public Response redeemCoupon(String code) {
		Response response = null;
		try {

			if (StringUtils.isEmpty(code)) {

				Error error = new Error();
				error.setCode("CPS-004");
				error.setMessage("Coupon code is required");
				error.setType("Functional");

				response = Response.status(Status.BAD_REQUEST).entity(error).build();

			} else {
				int count = couponRepo.redeemCoupon(true, code);

				if (count < 1) {
					Error error = new Error();
					error.setCode("CPS-003");
					error.setMessage("Coupon either already redeemed or not found");
					error.setType("Functional");

					response = Response.status(Status.BAD_REQUEST).entity(error).build();
				} else {
					response = Response.status(Status.OK).build();
				}
			}
		} catch (Exception ex) {
			Error error = new Error();
			error.setCode("CPS-001");
			error.setMessage(ex.getMessage());
			error.setType("System");

			response = Response.status(Status.INTERNAL_SERVER_ERROR).entity(error).build();
		}

		return response;
	}

	/* (non-Javadoc)
	 * @see com.shanpizza.platform.rest.resource.CouponResource#createNewCoupon()
	 */
	@Override
	public Response createNewCoupon() {
		Response response = null;
		try {

			Coupon coupon = CouponGenerator.generate();
			couponRepo.save(coupon);
			response = Response.status(Status.OK).entity(coupon).build();

		} catch (Exception ex) {
			Error error = new Error();
			error.setCode("CPS-001");
			error.setMessage(ex.getMessage());
			error.setType("System");

			response = Response.status(Status.INTERNAL_SERVER_ERROR).entity(error).build();
		}

		return response;
	}

	/* (non-Javadoc)
	 * @see com.shanpizza.platform.rest.resource.CouponResource#createNewCoupon(com.shanpizza.platform.model.Coupon)
	 */
	@Override
	public Response createNewCoupon(Coupon coupon) {
		Response response = null;
		try {

			couponValidator.validate(coupon);

			couponRepo.save(coupon);
			response = Response.status(Status.OK).entity(coupon).build();

		} catch (BadRequestException bre) {
			Error error = new Error();
			error.setCode("CPS-004");
			error.setMessage(bre.getMessage());
			error.setType("Functional");

			response = Response.status(Status.BAD_REQUEST).entity(error).build();
		} catch (Exception ex) {
			Error error = new Error();
			error.setCode("CPS-001");
			error.setMessage(ex.getMessage());
			error.setType("System");

			response = Response.status(Status.INTERNAL_SERVER_ERROR).entity(error).build();
		}

		return response;
	}

	@Override
	public Response getCustomers(Integer page, Integer size) {
		Response response = null;
		try {
			Pageable pageable = new PageRequest(page, size);
			List<Customer> customers = customerRepo.findAll(pageable).getContent();
			response = Response.status(Status.OK).entity(customers).build();
		} catch (Exception ex) {
			Error error = new Error();
			error.setCode("CPS-001");
			error.setMessage(ex.getMessage());
			error.setType("System");

			response = Response.status(Status.INTERNAL_SERVER_ERROR).entity(error).build();
		}

		return response;
	}

}
