/**
 * 
 */
package com.madhu.demo.service;

import java.util.List;

import com.madhu.demo.model.Customer;

/**
 * @author 15197
 *
 */
public interface CustomerService {
	public List<Customer> getCustomers();

	public void saveCustomer(Customer theCustomer);

	public Customer getCustomer(int theId);

	public void deleteCustomer(int theId);

	public List<Customer> searchCustomers(String theSearchName);
	
	public List<Customer> getCustomers(int theSortField);
}
