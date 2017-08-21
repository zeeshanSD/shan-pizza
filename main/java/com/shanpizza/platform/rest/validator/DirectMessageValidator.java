package com.shanpizza.platform.rest.validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.shanpizza.platform.model.Coupon;
import com.shanpizza.platform.model.DirectMessage;
import com.shanpizza.platform.rest.service.BadRequestException;


/**
 * The Class DirectMessageValidator.
 * 
 * This class is used to validate direct messages.
 */
@Component
public class DirectMessageValidator {

	/**
	 * Validate.
	 *
	 * @param directMessage the direct message
	 * @throws BadRequestException the bad request exception
	 */
	public void validate(DirectMessage directMessage) throws BadRequestException {

		StringBuilder errorMessage = new StringBuilder();
		errorMessage.append("Invalid request data");
		boolean foundErrors = false;
		
		if (StringUtils.isEmpty(directMessage.getMessage()))
		{
			foundErrors = true;
			errorMessage.append(", Message s required");
		}
		
		if (StringUtils.isEmpty(directMessage.getScreenName()))
		{
			foundErrors = true;
			errorMessage.append(", Screen name is required");
		}
        
		if (foundErrors)
		{
			throw new BadRequestException(errorMessage.toString());
		}
	}

}
