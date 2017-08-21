package com.shanpizza.platform.model;


/**
 * The Class DirectMessage.
 * 
 * This is a POJO used to connect to interact with users on Social Media
 */
public class DirectMessage {
	
	/** The message. */
	private String message;
	
	/** The screen name. */
	private String screenName;

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DirectMessage [message=" + message + ", screenName=" + screenName + "]";
	}
	

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the message.
	 *
	 * @param message the new message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Gets the screen name.
	 *
	 * @return the screen name
	 */
	public String getScreenName() {
		return screenName;
	}

	/**
	 * Sets the screen name.
	 *
	 * @param screenName the new screen name
	 */
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

}
