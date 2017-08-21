
package com.shanpizza.platform.rest.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.TweetData;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.stereotype.Component;

import com.shanpizza.platform.dao.CouponDAO;
import com.shanpizza.platform.model.DirectMessage;
import com.shanpizza.platform.model.Error;
import com.shanpizza.platform.rest.resource.SocialCampaignResource;
import com.shanpizza.platform.rest.validator.DirectMessageValidator;

// TODO: Auto-generated Javadoc
/**
 * The Class TwitterService.
 * 
 * This is a Rest API that provide interface to integrate with Twitter API's
 * THe purpose of this API to publish tweets and direct messages
 */
@Component
@Path("/social")
public class TwitterService implements SocialCampaignResource {

	/** The twitter. */
	@Autowired
	private TwitterTemplate twitter;

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(TwitterService.class);

	/** The coupon repo. */
	@Autowired
	private CouponDAO couponRepo;

	/** The direct message validator. */
	@Autowired
	private DirectMessageValidator directMessageValidator;

	/* (non-Javadoc)
	 * @see com.shanpizza.platform.rest.resource.SocialCampaignResource#publishPromotion(java.lang.String)
	 */
	public Response publishPromotion(String tweetText) {
		Response response = null;
		try {
			if (StringUtils.isEmpty(tweetText)) {
				Error error = new Error();
				error.setCode("SCS-001");
				error.setMessage("Invalid Request: Missing tweet text");
				error.setType("Functional");
				response = Response.status(Status.BAD_REQUEST).entity(error).build();

			} else {
				LOGGER.info("Tweeting: " + tweetText);
				twitter.timelineOperations().updateStatus(new TweetData(tweetText));
				response = Response.status(Status.OK).build();
			}
		} catch (org.springframework.social.DuplicateStatusException dse) {
			Error error = new Error();
			error.setCode("SCS-001");
			error.setMessage(dse.getMessage());
			error.setType("Functional");
			response = Response.status(Status.BAD_REQUEST).entity(error).build();
			//dse.printStackTrace();

		} catch (Exception ex) {

			Error error = new Error();
			error.setCode("SCS-002");
			error.setMessage(ex.getMessage());
			error.setType("System");
			response = Response.status(Status.INTERNAL_SERVER_ERROR).entity(error).build();
			//ex.printStackTrace();
		}

		return response;
	}

	/* (non-Javadoc)
	 * @see com.shanpizza.platform.rest.resource.SocialCampaignResource#sendDirectMessage(com.shanpizza.platform.model.DirectMessage)
	 */
	public Response sendDirectMessage(DirectMessage directMessage) {
		Response response = null;
		try {

			directMessageValidator.validate(directMessage);

			LOGGER.info("Direct Message : " + directMessage);
			twitter.directMessageOperations().sendDirectMessage(directMessage.getScreenName(),
					directMessage.getMessage());
			response = Response.status(Status.OK).build();

		} catch (BadRequestException bre) {
			Error error = new Error();
			error.setCode("SCS-001");
			error.setMessage(bre.getMessage());
			error.setType("Functional");
			response = Response.status(Status.BAD_REQUEST).entity(error).build();

		} catch (Exception ex) {

			Error error = new Error();
			error.setCode("SCS-002");
			error.setMessage(ex.getMessage());
			error.setType("System");
			response = Response.status(Status.INTERNAL_SERVER_ERROR).entity(error).build();
		//	ex.printStackTrace();
		}

		return response;
	}

	/**
	 * User mentions.
	 *
	 * @return the list
	 */
	@Path("/mentions")
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Tweet> userMentions() {
		List<Tweet> tweets = null;
		try {
			tweets = twitter.timelineOperations().getMentions();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return tweets;
	}

}
