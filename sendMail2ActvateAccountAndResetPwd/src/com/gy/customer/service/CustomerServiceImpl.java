package com.gy.customer.service;

import java.util.Date;

public class CustomerServiceImpl implements CustomerService{
	private CustomerDao customerDao;

	public void register(CustomerPojo customerPojo) {
		customerDao.register(customerPojo);
	}


	public void setCustomerDao(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	public CustomerDao getCustomerDao() {
		return customerDao;
	}


	public boolean checkHasEmail(String email) {
		return customerDao.checkHasEmail(email);
	}


	public boolean checkHasNick(String nick) {
		return customerDao.checkHasNick(nick);
	}


	public CustomerPojo findCustomerPojoById(int id) {
		return customerDao.findCustomerPojoById(id);
	}


	public void activiteCustomer(int id) {
		customerDao.activiteCustomer(id);
	}


	public CustomerPojo login(CustomerPojo customerPojo) {
		return customerDao.login(customerPojo);
	}


	public CustomerPojo findCustomerPojoByNick(String nick) {
		return customerDao.findCustomerPojoByNick(nick);
	}


	public void updateCustomerPwd(CustomerPojo customerPojo) {
		customerDao.updateCustomerPwd(customerPojo);
	}


	public void updatePwdTime(CustomerPojo customerPojo) {
		customerDao.updatePwdTime(customerPojo);
	}

}
