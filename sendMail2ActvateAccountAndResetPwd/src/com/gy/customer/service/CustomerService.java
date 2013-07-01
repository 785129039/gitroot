package com.gy.customer.service;



public interface CustomerService {

	void register(CustomerPojo customerPojo);

	boolean checkHasEmail(String email);

	boolean checkHasNick(String nick);

	CustomerPojo findCustomerPojoById(int id);

	void activiteCustomer(int id);

	CustomerPojo login(CustomerPojo customerPojo);

	CustomerPojo findCustomerPojoByNick(String nick);

	void updateCustomerPwd(CustomerPojo customerPojo);

	void updatePwdTime(CustomerPojo customerPojo);

}
