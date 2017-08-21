package com.shanpizza.platform.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;


/**
 * The Class Customer.
 * 
 * This class is an Entity class that is used to store 
 * customer information in Database
 */
@Entity
@Table(name = "customer")
public class Customer {
	
	
	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private int id;
	
	/** The name. */
	private String name;
	
	/** The coupon. */
	@OneToOne
	private Coupon coupon;
	
    /**
     * Gets the coupon.
     *
     * @return the coupon
     */
    public Coupon getCoupon() {
		return coupon;
	}

	/**
	 * Sets the coupon.
	 *
	 * @param coupon the new coupon
	 */
	public void setCoupon(Coupon coupon) {
		this.coupon = coupon;
	}

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
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}
 
	

}
