package com.shanpizza.platform.dao;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shanpizza.platform.model.Coupon;

/**
 * The Interface CouponDAO.
 */
@Repository
public interface CouponDAO extends JpaRepository<Coupon, Integer>
{
	    
    	/**
    	 * Get all the coupons.
    	 *
    	 * @return the coupons
    	 */
    	@Query("SELECT c FROM Coupon c") 
	    @Transactional
	    public List<Coupon> getCoupons();
	     
	    /**
    	 * Gets the coupon.
    	 *
    	 * @param code the code
    	 * @return the coupon
    	 */
    	@Query("SELECT c FROM Coupon c Where c.code=:code") 
	    @Transactional
	    public Coupon getCoupon(@Param("code")String code);
	     

	    
	    /**
    	 * Redeem coupon.
    	 *
    	 * @param status the status
    	 * @param code the code
    	 * @return the int
    	 */
    	@Modifying
	    @Query("update Coupon c set c.redeemed = ?1 where c.code = ?2")
	    @Transactional
	    public int redeemCoupon(boolean status, String code);
	     
}
