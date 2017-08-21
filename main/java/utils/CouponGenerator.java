package utils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.UUID;

import com.shanpizza.platform.model.Coupon;


/**
 * The Class CouponGenerator.
 */
public class CouponGenerator {
	
	/**
	 * Generate a dynamic coupon 
	 *
	 * @return the coupon
	 * @throws Exception the exception
	 */
	public static Coupon generate() throws Exception
	{
		Coupon coupon = new Coupon();
        coupon.setCode(UUID.randomUUID().toString());
        coupon.setRedeemed(false);
        coupon.setSales_channel("twitter");
        coupon.setType("Buy 1 Get 1 free");
        coupon.setIssued_date(new SimpleDateFormat("yyyy-MM-dd").parse(LocalDate.now().toString()));
        coupon.setExpiration_date(new SimpleDateFormat("yyyy-MM-dd").parse(LocalDate.now().plusDays(7).toString()));
        
        return coupon;
	}

}
