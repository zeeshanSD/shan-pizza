package com.shanpizza.platform.rest.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;

import java.util.ArrayList;

import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.DuplicateStatusException;
import org.springframework.social.twitter.api.DirectMessageOperations;
import org.springframework.social.twitter.api.TimelineOperations;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.shanpizza.platform.model.DirectMessage;
import com.shanpizza.platform.model.Error;
import com.shanpizza.platform.rest.resource.SocialCampaignResource;

/**
 * The Class TwitterServiceTest.
 * 
 *  This class contains tests related to twitter service.  It includes both happy path and negative scenarios
 */
@PowerMockIgnore("javax.management.*")
@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class TwitterServiceTest {

	/** The twitter service. */
	@Autowired
	private SocialCampaignResource twitterService;
	
	
	/** The twitter template. */
	@Autowired
	private TwitterTemplate twitterTemplate;


	
    /**
     * Setup.
     *
     * @throws Exception the exception
     */
    @Before
    public void setup() throws Exception
    {
    	PowerMockito.mock(Tweet.class);
    	PowerMockito.mock(org.springframework.social.twitter.api.DirectMessage.class);
    
    	DirectMessageOperations directMessageOperations = PowerMockito.mock(DirectMessageOperations.class);
      	PowerMockito.when(twitterTemplate.directMessageOperations()).thenReturn(directMessageOperations);
      	
    	TimelineOperations timeLineOperations = PowerMockito.mock(TimelineOperations.class);
    	PowerMockito.when(twitterTemplate.timelineOperations()).thenReturn(timeLineOperations);

    	PowerMockito.when(twitterTemplate.timelineOperations().getMentions(Mockito.anyInt(),Mockito.anyLong(), Mockito.anyLong())).thenReturn(new ArrayList<Tweet>());
    	PowerMockito.when(twitterTemplate.timelineOperations().getHomeTimeline(Mockito.anyInt(),Mockito.anyLong(), Mockito.anyLong())).thenReturn(new ArrayList<Tweet>());
        

       
    }
    
    /**
     * Tweet test.
     */
    @Test
	public void tweetTest() {

    	Tweet tweet =  PowerMockito.mock(Tweet.class);

    	PowerMockito.when(twitterTemplate.timelineOperations().updateStatus(anyString())).thenReturn(tweet);
   
    	Response response = twitterService.publishPromotion("Pizza Pizza!");
		assertEquals(200, response.getStatus());


	}
    
    /**
     * Duplicate tweet test.
     */
    @Test
	public void duplicateTweetTest() {


    	DuplicateStatusException dse = new DuplicateStatusException("", "Status is duplicate");

    	PowerMockito.when(twitterTemplate.timelineOperations()).thenThrow(dse);
    	
    	Response response = twitterService.publishPromotion("Pizza Pizza!");
		assertEquals(400, response.getStatus());

		Error error = (Error) response.getEntity();
   		assertEquals(error.getCode(),"SCS-001");
   		assertEquals(error.getType(),"Functional");

	}
    
    /**
     * Direct message test.
     */
    @Test
	public void directMessageTest() {

		org.springframework.social.twitter.api.DirectMessage directMessage = PowerMockito.mock(org.springframework.social.twitter.api.DirectMessage.class);
      
    	PowerMockito.when(twitterTemplate.directMessageOperations().sendDirectMessage(anyString(),anyString())).thenReturn(directMessage);

    	DirectMessage message = new DirectMessage();
    	message.setMessage("test");
    	message.setScreenName("testUser");
    	Response response = twitterService.sendDirectMessage(message);
		assertEquals(200, response.getStatus());

	}
    
    /**
     * Direct message failure test.
     */
    @Test
  	public void directMessageFailureTest() {

      	PowerMockito.when(twitterTemplate.directMessageOperations()).thenThrow(new RuntimeException());
      	DirectMessage message = new DirectMessage();
      	message.setMessage("test");
      	message.setScreenName("testUser");
      	Response response = twitterService.sendDirectMessage(message);
  		assertEquals(500, response.getStatus());

  	}
	
	/**
	 * Invalid tweet test.
	 */
	@Test
	public void invalidTweetTest() {
		
		Response response = twitterService.publishPromotion("");
		assertEquals(400, response.getStatus());
   		Error error = (Error) response.getEntity();
   		assertEquals(error.getCode(),"SCS-001");
   		assertEquals(error.getType(),"Functional");
	}
	
	
	/**
	 * Invalid direct message test.
	 */
	@Test
	public void invalidDirectMessageTest() {
		
		Response response = twitterService.sendDirectMessage(new DirectMessage());
		assertEquals(400, response.getStatus());
   		Error error = (Error) response.getEntity();
   		assertEquals(error.getCode(),"SCS-001");
   		assertEquals(error.getType(),"Functional");
	}

}
