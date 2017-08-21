package com.shanpizza.platform.rest.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.shanpizza.platform.dao.CouponDAO;
import com.shanpizza.platform.model.Coupon;
import com.shanpizza.platform.rest.resource.CouponResource;
import com.shanpizza.platform.model.Error;

import utils.CouponGenerator;

/**
 * The Class CouponServiceTest.
 * 
 *  This class contains tests related to coupon service.  It includes both happy path and negative scenarios
 */
@PowerMockIgnore("javax.management.*")
@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class CouponServiceTest {

	/** The coupon service. */
	@Autowired
	private CouponResource couponService;
	
	/** The coupon repo. */
	@Autowired
	private CouponDAO couponRepo;

	
    /**
     * Setup.
     *
     * @throws Exception the exception
     */
    @SuppressWarnings("unchecked")
    @Before
    public void setup() throws Exception
    {
       Coupon coupon = CouponGenerator.generate();
       couponRepo.save(coupon);
       
    }
	
	/**
	 * Gets the coupons test.
	 *
	 * @return the coupons test
	 */
	@Test
	@SuppressWarnings("unchecked")
	public void getCouponsTest() {
		
		Response response = couponService.getAllCoupons(0,1);
		List<Coupon> coupons = (List<Coupon>) response.getEntity();
		System.out.println(coupons);
		assertEquals(200, response.getStatus());
		assertNotNull(coupons);
	}
	
	/**
	 * Creates the and verify coupon test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@SuppressWarnings("unchecked")
	public void createAndVerifyCouponTest() throws Exception {
		
		Coupon coupon = new Coupon();
		coupon.setCode("12345");
		
		LocalDate issuedDate = LocalDate.of(2018, 8, 15);
		LocalDate expirationDate = LocalDate.of(2018, 8, 30);
		
        coupon.setIssued_date(new SimpleDateFormat("yyyy-MM-dd").parse(issuedDate.toString()));
        coupon.setExpiration_date(new SimpleDateFormat("yyyy-MM-dd").parse(expirationDate.toString()));
        coupon.setSales_channel("Test");
        coupon.setType("FakeCoupon");
        
        Response response = couponService.createNewCoupon(coupon);
		assertEquals(200, response.getStatus());
		
		response = couponService.getCouponByCode(coupon.getCode());
		assertEquals(200, response.getStatus());
		Coupon persistedCoupon = (Coupon) response.getEntity();
		
		assertEquals(coupon.getCode(), persistedCoupon.getCode());
		assertEquals(coupon.getExpiration_date(), persistedCoupon.getExpiration_date());
		assertEquals(coupon.getIssued_date(), persistedCoupon.getIssued_date());
		assertEquals(coupon.getSales_channel(), persistedCoupon.getSales_channel());
		assertEquals(coupon.getType(), persistedCoupon.getType());
		assertEquals(Boolean.FALSE, persistedCoupon.isRedeemed());
		
		

	}
	
    /**
     * Redeem and verify coupon test.
     *
     * @throws Exception the exception
     */
    @Test
	@SuppressWarnings("unchecked")
	public void redeemAndVerifyCouponTest() throws Exception {

		Coupon coupon = new Coupon();

		String code = UUID.randomUUID().toString();
		coupon.setCode(code);

		LocalDate issuedDate = LocalDate.of(2018, 8, 15);
		LocalDate expirationDate = LocalDate.of(2018, 8, 30);

		coupon.setIssued_date(new SimpleDateFormat("yyyy-MM-dd").parse(issuedDate.toString()));
		coupon.setExpiration_date(new SimpleDateFormat("yyyy-MM-dd").parse(expirationDate.toString()));
		coupon.setSales_channel("Test");
		coupon.setType("FakeCoupon");
		Response response = couponService.createNewCoupon(coupon);
		assertEquals(200, response.getStatus());

		response = couponService.redeemCoupon(code);
		assertEquals(200, response.getStatus());

		response = couponService.getCouponByCode(code);
		assertEquals(200, response.getStatus());
		Coupon persistedCoupon = (Coupon) response.getEntity();
		assertEquals(Boolean.TRUE, persistedCoupon.isRedeemed());

	}
    
    
    /**
     * Invalid code remption test.
     *
     * @throws Exception the exception
     */
    @Test
   	@SuppressWarnings("unchecked")
   	public void invalidCodeRemptionTest() throws Exception {


   		Response response = couponService.redeemCoupon("");
   		assertEquals(400, response.getStatus());
   		Error error = (Error) response.getEntity();
   		assertEquals(error.getCode(),"CPS-004");
   		assertEquals(error.getType(),"Functional");
   		

   	}
    
    /**
     * Invalid coupon test.
     *
     * @throws Exception the exception
     */
    @Test
	@SuppressWarnings("unchecked")
	public void invalidCouponTest() throws Exception {
		
		Coupon coupon = new Coupon();
		
        
        Response response = couponService.createNewCoupon(coupon);
		assertEquals(400, response.getStatus());
	
		Error error = (Error) response.getEntity();
		System.out.println("Error details " + error.getMessage());
   		assertEquals(error.getCode(),"CPS-004");
   		assertEquals(error.getType(),"Functional");
		

	}
	

}
