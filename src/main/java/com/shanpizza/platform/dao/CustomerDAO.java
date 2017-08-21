package com.shanpizza.platform.dao;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shanpizza.platform.model.Coupon;
import com.shanpizza.platform.model.Customer;

@Repository
public interface CustomerDAO extends JpaRepository<Customer, Integer>
{

	    /**
    	 * Gets the customer.
    	 *
    	 * @param name the name
    	 * @return the customer
    	 */
    	@Query("SELECT c FROM Customer c Where c.name=:name") 
	    @Transactional
	    public Customer getCustomer(@Param("name")String names);

	     
}
