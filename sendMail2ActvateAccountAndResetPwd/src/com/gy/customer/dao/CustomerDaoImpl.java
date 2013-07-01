package com.gy.customer.dao;

import java.util.Date;
import java.util.UUID;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import com.zc.zcproject.pojo.CustomerPojo;
import com.zc.zcproject.utils.EmailUtil;
import com.zc.zcproject.utils.GenerateLinkUtils;


public class CustomerDaoImpl extends SqlMapClientDaoSupport implements CustomerDao{

	public void register(CustomerPojo customerPojo) {
		customerPojo.setPwd(GenerateLinkUtils.md5(customerPojo.getPwd()));	//MD5加密
		customerPojo.setRandom_code(UUID.randomUUID().toString());
		getSqlMapClientTemplate().insert("customer.register", customerPojo);
	}

	public boolean checkHasEmail(String email) {
		int count = (Integer) getSqlMapClientTemplate().queryForObject("customer.checkHasEmail", email);
		if (count==1) {
			return false;
		}
		return true;
	}

	public boolean checkHasNick(String nick) {
		int count = (Integer) getSqlMapClientTemplate().queryForObject("customer.checkHasNick", nick);
		if (count==1) {
			return true;
		}
		return false;
	}

	public CustomerPojo findCustomerPojoById(int id) {
		return (CustomerPojo) getSqlMapClientTemplate().queryForObject("customer.findCustomerPojoById", id);
	}

	public void activiteCustomer(int id) {
		getSqlMapClientTemplate().update("customer.activiteCustomer", id);
	}

	public CustomerPojo login(CustomerPojo customerPojo) {
		customerPojo.setPwd(GenerateLinkUtils.md5(customerPojo.getPwd()));
		System.out.println(customerPojo.getPwd());
		int count = (Integer) getSqlMapClientTemplate().queryForObject("customer.login", customerPojo);
		if (count==1) {
			customerPojo = (CustomerPojo) getSqlMapClientTemplate().queryForObject("customer.findCustomerPojoByLogin", customerPojo);
			return customerPojo;
		}
		return null;
	}

	public CustomerPojo findCustomerPojoByNick(String nick) {
		return (CustomerPojo) getSqlMapClientTemplate().queryForObject("customer.findCustomerPojoByNick", nick) ;
	}

	public void updateCustomerPwd(CustomerPojo customerPojo) {
		customerPojo.setPwd(GenerateLinkUtils.md5(customerPojo.getPwd()));
		getSqlMapClientTemplate().update("customer.updateCustomerPwd", customerPojo);
	}

	public void updatePwdTime(CustomerPojo customerPojo) {
		getSqlMapClientTemplate().update("customer.updatePwdTime", customerPojo);
	}

}
