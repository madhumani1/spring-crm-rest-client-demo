/**
 * 
 */
package com.madhu.demo.service;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.madhu.demo.model.Customer;
import com.madhu.demo.util.EmailComparator;
import com.madhu.demo.util.FirstNameComparator;
import com.madhu.demo.util.LastNameComparator;
import com.madhu.demo.util.SortUtils;

/**
 * @author 15197
 * This is implementation class of CustomerService Interface.
 * @Service – It is used for Service Facade implementations. 
 * Through component-scanning, Spring will register this component.  
 * 
 */
@Service
@PropertySource("classpath:application.properties")
public class CustomerServiceImpl implements CustomerService {

	private RestTemplate restTemplate;
	private String crmRestUrl;
	private Logger logger = Logger.getLogger(getClass().getName());
	
	public CustomerServiceImpl()	{
	}
	
	@Autowired
	public CustomerServiceImpl(RestTemplate theRestTemplate, @Value("${crm.rest.url}") String theUrl)	{
		restTemplate = theRestTemplate;
		//logger.info("Madhukar: " + theUrl);
		crmRestUrl = theUrl; //"http://localhost:2183/spring-crm-rest-demo/api/customers";
		
		logger.info("Loaded property:  crm.rest.url=" + crmRestUrl);
	}
	
	// Mark for transactional. 
	/*@Override
	@Transactional
	public List<Customer> getCustomers(int theSortField) {
		// return results
		//return customerDAO.getCustomers();
		return customerDAO.getCustomers(theSortField);
	}*/
	
	@Override
	public void saveCustomer(Customer theCustomer)	{
		logger.info("in saveCustomer(): Calling REST API " + crmRestUrl);
		
		int employeeId = theCustomer.getId();
		// make REST call
		if (employeeId == 0) {
			// add employee
			restTemplate.postForEntity(crmRestUrl, theCustomer, String.class);			
		
		} else {
			// update employee
			restTemplate.put(crmRestUrl, theCustomer);
		}

		logger.info("in saveCustomer(): success");
	}

	@Override
	public Customer getCustomer(int theId) {
		logger.info("in getCustomer(): Calling REST API " + crmRestUrl);
		
		// make REST call
		Customer theCustomer = restTemplate.getForObject(crmRestUrl + "/" + theId, Customer.class);
		logger.info("in getCustomer(): theCustomer=" + theCustomer);
		return theCustomer;
	}

	@Override
	public void deleteCustomer(int theId) {
		logger.info("in deleteCustomer(): Calling REST API " + crmRestUrl);
		
		// make REST call
		restTemplate.delete(crmRestUrl + "/" + theId);
		logger.info("in deleteCustomer(): deleted customer theId=" + theId);
	}

	@Override
	public List<Customer> searchCustomers(String theSearchName) {
		return null;
	}

	@Override 
	public List<Customer> getCustomers(int theSortField) { 
		logger.info("in getCustomers(): Calling REST API " + crmRestUrl);
		// make REST call
		ResponseEntity<List<Customer>> responseEntity = restTemplate.exchange(crmRestUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<Customer>>() {});
		// get the list of customers from response
		List<Customer> customers = responseEntity.getBody();
		//Collections.sort((List<Customer>) customers);
		if(SortUtils.FIRST_NAME==theSortField)	{
			Collections.sort(customers,new FirstNameComparator() {} );
		} else if(SortUtils.LAST_NAME==theSortField)	{
			Collections.sort(customers,new LastNameComparator() {} );
		} else if(SortUtils.EMAIL==theSortField)	{
			Collections.sort(customers,new EmailComparator() {} );
		}
		logger.info("in getCustomers(theSortField): customers" + customers);
		return customers; 
	}
	

	@Override
	public List<Customer> getCustomers() {
		logger.info("in getCustomers(): Calling REST API " + crmRestUrl);
		// make REST call
		ResponseEntity<List<Customer>> responseEntity = restTemplate.exchange(crmRestUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<Customer>>() {});
		// get the list of customers from response
		List<Customer> customers = responseEntity.getBody();
		logger.info("in getCustomers(): customers" + customers);
		return customers;
	}
}
