package com.gy.customer.dao;

import java.util.Date;

import com.zc.zcproject.pojo.CustomerPojo;

public interface CustomerDao {

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
