/**
 * 
 */
package com.flipfit.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.flipfit.bean.Customer;

/**
 * 
 */
@Service
public class CustomerServiceImpl implements CustomerService {

	private final List<Customer> customers = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong();
    @Override
    public List<Customer> findAllCustomers() {
        return new ArrayList<>(customers); // Return a copy to prevent external modification
    }
    @Override
    public Optional<Customer> findCustomerById(Long id) {
        return customers.stream().filter(c -> c.getId().equals(id)).findFirst();
    }
    @Override
    public Customer saveCustomer(Customer customer) {
        customer.setId(counter.incrementAndGet());
        customers.add(customer);
        return customer;
    }
    @Override
    public Customer updateCustomer(Long id, Customer customerDetails) {
        return findCustomerById(id).map(customer -> {
            customer.setFirstName(customerDetails.getFirstName());
            customer.setLastName(customerDetails.getLastName());
            customer.setEmail(customerDetails.getEmail());
            return customer;
        }).orElse(null);
    }
    @Override
    public void deleteCustomer(Long id) {
        customers.removeIf(c -> c.getId().equals(id));
    }

}
