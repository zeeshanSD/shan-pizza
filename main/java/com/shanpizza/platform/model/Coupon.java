package com.shanpizza.platform.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * Coupon Class
 * 
 * This class is an Entity class that gets persisted 
 * into Database
 *
 */
@Entity
@Table(name = "coupon")
public class Coupon {
	
	/** The code. */
	@Column(unique=true)
	private String code;
	
	/** The type. */
	private String type;
	
	/** The sales_channel. */
	private String sales_channel;
	
	/** The issued_date. */
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
	private Date issued_date;
	
	/** The expiration_date. */
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
	private Date expiration_date;
    
    /** The redeemed. */
    private boolean redeemed;
	
	/**
	 * Checks if is redeemed.
	 *
	 * @return true, if is redeemed
	 */
	public boolean isRedeemed() {
		return redeemed;
	}

	/**
	 * Sets the redeemed.
	 *
	 * @param redeemed the new redeemed
	 */
	public void setRedeemed(boolean redeemed) {
		this.redeemed = redeemed;
	}
	
	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private int id;
	
	

    /**
     * Gets the id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }
	
	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(int id)
	{
		this.id = id;
	}
 
	
	/**
	 * Gets the code.
	 *
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	
	/**
	 * Sets the code.
	 *
	 * @param code the new code
	 */
	public void setCode(String code) {
		this.code = code;
	}
	
	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * Gets the issued_date.
	 *
	 * @return the issued_date
	 */
	public Date getIssued_date() {
		return issued_date;
	}
	
	/**
	 * Sets the issued_date.
	 *
	 * @param issued_date the new issued_date
	 */
	public void setIssued_date(Date issued_date) {
		this.issued_date = issued_date;
	}
	
	/**
	 * Gets the expiration_date.
	 *
	 * @return the expiration_date
	 */
	public Date getExpiration_date() {
		return expiration_date;
	}
	
	/**
	 * Sets the expiration_date.
	 *
	 * @param expiration_date the new expiration_date
	 */
	public void setExpiration_date(Date expiration_date) {
		this.expiration_date = expiration_date;
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Coupon [code=" + code + ", type=" + type + ", sales_channel=" + sales_channel + ", issued_date="
				+ issued_date + ", expiration_date=" + expiration_date + ", redeemed=" + redeemed + ", id=" + id + "]";
	}

	/**
	 * Gets the sales_channel.
	 *
	 * @return the sales_channel
	 */
	public String getSales_channel() {
		return sales_channel;
	}

	/**
	 * Sets the sales_channel.
	 *
	 * @param sales_channel the new sales_channel
	 */
	public void setSales_channel(String sales_channel) {
		this.sales_channel = sales_channel;
	}
	
	

}
