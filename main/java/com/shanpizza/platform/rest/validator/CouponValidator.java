package com.shanpizza.platform.rest.validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.shanpizza.platform.model.Coupon;
import com.shanpizza.platform.rest.service.BadRequestException;


/**
 * The Class CouponValidator.
 * 
 * This validator is to validate Coupons.
 * 
 */
@Component
public class CouponValidator {

	/**
	 * Validate.
	 *
	 * @param c the c
	 * @throws BadRequestException the bad request exception
	 */
	public void validate(Coupon c) throws BadRequestException {

		StringBuilder errorMessage = new StringBuilder();
		errorMessage.append("Invalid request data");
		boolean foundErrors = false;
		
		if (StringUtils.isEmpty(c.getCode()))
		{
			foundErrors = true;
			errorMessage.append(", Coupon code is required");
		}
		
		if (StringUtils.isEmpty(c.getSales_channel()))
		{
			foundErrors = true;
			errorMessage.append(", Sales Channel is required");
		}
        
		if (StringUtils.isEmpty(c.getType()))
		{
			foundErrors = true;
			errorMessage.append(", Coupon Type is required");
		}
		
		if (c.getIssued_date() == null)
		{
			foundErrors = true;
			errorMessage.append(", Issued Date is required");
		}
		
		if (c.getExpiration_date() == null)
		{
			foundErrors = true;
			errorMessage.append(", Expiration Date is required");
		}

		if (foundErrors)
		{
			throw new BadRequestException(errorMessage.toString());
		}
	}

}
