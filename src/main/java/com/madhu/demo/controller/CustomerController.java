/**
 * 
 */
package com.madhu.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.madhu.demo.model.Customer;
import com.madhu.demo.service.CustomerService;
import com.madhu.demo.util.SortUtils;

/**
 * @author 15197
 * Customer Controller
 * With Autowired, Spring will scan for a component that implements CustomerDAO interface
 * no more RequestMapping. use GetMapping or PostMapping.
 * With Service in place now, we no longer need to use DAO in controller. We will use Service layer instead
 * 
 */
@Controller
@RequestMapping("/customer")
public class CustomerController {
	/**
	 * With Service in place now, we no longer need to use DAO in controller. 
	 * We will use Service layer instead
	 * comment @AutoWired private CustomerDAO customerDAO;
	 */
	// need to inject the customer DAO
	//@Autowired
	//private CustomerDAO customerDAO;
	// need to inject the customer service
	@Autowired
	private CustomerService customerService;
	
	/* @RequestMapping("/list") */
	/*@GetMapping("/list")
	public String listCustomers(Model theModel)	{
		// get customer from the DAO
		//List<Customer> theCustomers = customerDAO.getCustomers();
		// get customer from the Service
		List<Customer> theCustomers = customerService.getCustomers();
		
		// add the customer to my Spring MVC model
		theModel.addAttribute("customers", theCustomers);	// customers name and theCustomers value
		
		return "list-customers";
	}*/
	
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel)	{
		// create model attribute to bind form data
		Customer theCustomer = new Customer();
		theModel.addAttribute("customer", theCustomer);
		
		return "customer-form";
	}
	
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("customerId") int theId, Model theModel)	{
		// get the customer from our service
		Customer theCustomer = customerService.getCustomer(theId);
		
		// set customer as a model attribute to pre-populate the form
		theModel.addAttribute("customer", theCustomer);
		
		// send over to our form
		return "customer-form";
	}
	
	@PostMapping("/saveCustomer")
	public String saveCustomer(@ModelAttribute("customer") Customer theCustomer)	{
		customerService.saveCustomer(theCustomer);
		return "redirect:list";
	}
	
	@GetMapping("/delete")
	public String deleteCustomer(@RequestParam("customerId") int theId)	{
		//delete the customer
		customerService.deleteCustomer(theId);
		
		return "redirect:list";
	}
	
	@GetMapping("/search")
	public String searchCustomers(@RequestParam("theSearchName") String theSearchName, Model theModel)	{
		// search customers from the service
		List<Customer> theCustomers = customerService.searchCustomers(theSearchName);
		
		// add the customer to the model
		theModel.addAttribute("customers", theCustomers);
		
		return "list-customers";
	}
	
	// Read Sort Field, if no mapping is provided then default to sortutil.first_name 
	@GetMapping("/list")
	public String listCustomers(Model theModel, @RequestParam(required=false) String sort)	{
		// get customers from the service
		List<Customer> theCustomers = null;
		
		// check for sort field
		if(sort != null)	{
			int theSortField = Integer.parseInt(sort);
			theCustomers = customerService.getCustomers(theSortField);
		}	else	{
			// if no mapping is provided then default to sortutil.first_name
			theCustomers = customerService.getCustomers(SortUtils.FIRST_NAME);
		}
		// add the customer to the model
		theModel.addAttribute("customers", theCustomers);
		
		return "list-customers";
	}

	/**
	 * 
	 */
	public CustomerController() {
	}
	
	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}
}
