/**
 * 
 */
package com.madhu.demo.util;

import java.util.Comparator;

import com.madhu.demo.model.Customer;

/**
 * @author 15197
 *
 */
public abstract class LastNameComparator implements Comparator<Customer> {

	@Override
	public int compare(Customer o1, Customer o2) {
		return o1.getLastName().compareTo(o2.getLastName());
	}

}
