package com.shanpizza.platform.rest.resource;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.shanpizza.platform.model.DirectMessage;

import io.swagger.annotations.ApiParam;


/**
 * The Interface SocialCampaignResource.
 * 
 * This interface is providing a REST API contract to integrate with Social media.
 * Contract includes publishing message to social media as well as ability to directly communicate
 * with social media friends/connections.
 */
public interface SocialCampaignResource {
	
	/**
	 * Publish promotion.
	 *
	 * @param campaignMessage the campaign message
	 * @return the response
	 */
	@Path("/publish")
	@POST
	@Produces(
			{ MediaType.APPLICATION_JSON})
	public Response publishPromotion(@ApiParam(value = "Message to Social Media",  required = true) String campaignMessage);

	/**
	 * Send direct message.
	 *
	 * @param message the message
	 * @return the response
	 */
	@Path("/direct-message")
	@POST
	@Produces(
			{ MediaType.APPLICATION_JSON})
	public Response sendDirectMessage(@ApiParam(value = "Direct Message",  required = true) DirectMessage message);
}
