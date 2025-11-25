/**
 * 
 */
package com.flipfit.service;

import java.util.List;
import java.util.Optional;

import com.flipfit.bean.Customer;

/**
 * 
 */
public interface CustomerService {
	
	public List<Customer> findAllCustomers();
	public Optional<Customer> findCustomerById(Long id);
	public Customer saveCustomer(Customer customer);
	public Customer updateCustomer(Long id, Customer customerDetails);
	public void deleteCustomer(Long id);

}
