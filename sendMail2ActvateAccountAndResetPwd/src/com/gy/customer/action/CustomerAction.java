package com.gy.customer.action;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

import sun.security.krb5.internal.crypto.crc32;

import com.opensymphony.xwork2.ActionSupport;
import com.zc.zcproject.pojo.CustomerPojo;
import com.zc.zcproject.utils.EmailUtil;
import com.zc.zcproject.utils.GenerateLinkUtils;
import com.zc.zcproject.utils.Validate;
import com.zc.zcproject.zzsc.service.customer.CustomerService;

public class CustomerAction extends ActionSupport implements SessionAware{

	private static final long serialVersionUID = 1L;
	private final Logger log = Logger.getLogger(CustomerAction.class);
	private CustomerService customerService;
	private CustomerPojo customerPojo;
	private Map<String, Object> session;
	private String validateCode;
	private String nick;
	
	private boolean validateCode() {
		String securityCode = (String) session.get("rand");
		if (!validateCode.equalsIgnoreCase(securityCode)) {
			addFieldError("validateError", "验证码输入错误!");
			log.warn("验证码输入错误!");
			return false;
		}
		session.remove("rand");
		return true;
	}
	
	private boolean registerValidate() {
		if (customerPojo.getNick()==null || customerPojo.getNick().trim().equals("")) {
			addFieldError("nickError", "用户名不能为空!");
			log.warn("用户名不能为空!");
			return false;
		}
		if (customerPojo.getNick().trim().length()<3 || customerPojo.getNick().trim().length()>20) {
			addFieldError("nickError", "用户名应是3-20个数字、字母、中文!");
			log.warn("用户名应是3-20个数字、字母、中文!");
			return false;
		}
		if(!validatePwd()) return false;
		if (!Validate.validateEmail(customerPojo.getEmail())) {
			addFieldError("emailError", "email地址输入不合法!");
			log.warn("email地址输入不合法!");
			return false;
		}
		if(!validateCode()) return false;
		if (customerService.checkHasNick(customerPojo.getNick().trim())) {
			addFieldError("nickError", "用户名已存在!");
			log.warn("用户名已存在!");
			return false;
		}
		if (!customerService.checkHasEmail(customerPojo.getEmail().trim())) {
			addFieldError("emailError", "email地址已存在!");
			log.warn("email地址已存在!");
			return false;
		}
		return true;
	}

	/**
	 * register
	 * @return
	 */
	public String register() {
		if(!registerValidate()) {
			return ERROR;
		}
		try {
			customerService.register(customerPojo);
		}catch(Exception ex){
			addFieldError("registerError", "注册失败，请稍候再试!");
			customerPojo.setPwd("");
			log.error("注册失败，请稍候再试!", ex);
			return ERROR;
		}
		log.info(customerPojo.getNick()+"用户注册成功!");
		//send emial to activation account
		try {
			EmailUtil.sendRegisterMail(customerPojo);
		} catch (AddressException e) {
			addFieldError("emailError", "邮箱地址不存在!");
			log.error("邮箱地址不存在!", e);
			return ERROR;
		} catch (MessagingException e) {
			addFieldError("registerError", "发送邮件失败，请稍候再试!--"+customerPojo.getEmail());
			log.error("发送邮件失败，请稍候再试!", e);
			return ERROR;
		}
		addActionMessage("注册成功，快去邮箱激活吧!");
		return SUCCESS;
	}

	/**
	 * activation account
	 * @return
	 */
	public String activiteAccount() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String idStr = request.getParameter("id");
		int id = -1;
		try {
			id = Integer.valueOf(idStr);
		}catch (Exception e) {
			log.error("传入的用户id不是数字----"+idStr, e);
			addActionError("无效的用户");
			return ERROR;
		}
		try {
			customerPojo = customerService.findCustomerPojoById(id);
			Date curDate = new Date();		//current dateTime
			if((curDate.getTime()-customerPojo.getRegistDate().getTime())>(5000*60)) {
				log.error("该链接已超时,超时时间是5分钟!");
				addActionError("该链接已超时,超时时间是5分钟!");
				return ERROR;
			}
		}catch (Exception e) {
			log.error("传入用户id不存在---"+id, e);
			addActionError("无效的用户");
			return ERROR;
		}
		String check_code = request.getParameter("checkCode");
		if (!GenerateLinkUtils.verifyCheckCode(check_code, customerPojo)) {	
			log.error("check_code不匹配");
			addActionError("无效的用户");
			return ERROR;
		}
		customerService.activiteCustomer(customerPojo.getId());
		return SUCCESS;
	}
	
	
	/**
	 * login
	 * 
	 * @return
	 */
	public String login() {
		if(!validateLogin()) {
			return ERROR;
		}
		if (customerService.login(customerPojo)==null) {
			log.warn("登录失败,您输入的用户名或密码不对！");
			addActionError("您输入的用户名或密码不对！");
			return ERROR;
		}
		session.put("customer", customerPojo);
		return SUCCESS;
	}
	
	private boolean validateLogin() {
		if(customerPojo.getNick()==null || customerPojo.getNick().trim().equals("")) {
			addFieldError("nickError", "用户名不能为空!");
			log.warn("用户名不能为空!");
			return false;
		}
		if (customerPojo.getNick().trim().length()<3 || customerPojo.getNick().trim().length()>20) {
			addFieldError("nickError", "用户名应是3-20个数字、字母、中文!");
			log.warn("用户名应是3-20个数字、字母、中文!");
			return false;
		}
		if(!validatePwd()) return false;
		if(!validateCode()) return false;
		return true;
	}
	/**
	 * logout
	 * @return
	 */
	public String logout() {
		session.remove("customer");
		return SUCCESS;
	}

	public String to_register(){
		ServletActionContext.getRequest().setAttribute("customerPojo", new CustomerPojo());
		return SUCCESS;
	}
	public String to_login() {
		ServletActionContext.getRequest().setAttribute("customerPojo", new CustomerPojo());
		return SUCCESS;
	}
	
	public String to_passwordSupport(){
		return SUCCESS;
	}
	
	/*
	 * check this user by nick
	 */
	public String hasNick() throws IOException {
		//check nick not null
		if (nick==null || nick.trim().equals("")) {
			log.warn("用户名不能为空!");
			addFieldError("nickError", "用户名不能为空!");
			return ERROR;
		}
		
		//check verifyCode
		if(!validateCode()) return ERROR;
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		if(!customerService.checkHasNick(nick)) {
			log.warn("无此用户----"+nick);
			addFieldError("nickError", "无此用户!");
			return ERROR;
		}else {		//has a customer
			customerPojo = customerService.findCustomerPojoByNick(nick);
		}
		return SUCCESS;
	}
	
	/**
	 * Send email to reset customer's pwd
	 * @return
	 */
	public String sendEmailResetPwd()  {
		try {
			EmailUtil.sendPwdMail(customerPojo);
			//update customerPojo updatePwdTime
			customerService.updatePwdTime(customerPojo);
		} catch (Exception ex) {
			log.error("发送邮件失败!", ex);
			addActionError("发送邮件失败,请确认您邮箱的正确性,稍候再试!");
			return ERROR;
		}
		addActionMessage("邮件发送成功,快去邮箱确认吧!");
		return SUCCESS;
	}
	
	/**
	 * check reset pwd linkUrl
	 * @return
	 */
	public String resetPwdVerify() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String idStr = request.getParameter("id");
		int id = -1;
		try {
			id = Integer.valueOf(idStr);
		}catch (Exception e) {
			log.error("传入的用户id不是数字----"+idStr, e);
			addActionError("无效的用户");
			return ERROR;
		}
		try {
			customerPojo = customerService.findCustomerPojoById(id);
			Date curDate = new Date();		//current dateTime
			System.out.println(curDate+"===="+curDate.getTime());
			System.out.println(customerPojo.getUpdatePwdTime()+"===="+customerPojo.getUpdatePwdTime().getTime());
			System.out.println((curDate.getTime()-customerPojo.getUpdatePwdTime().getTime()));
			if((curDate.getTime()-customerPojo.getUpdatePwdTime().getTime())>(5000*60)) {
				log.error("该链接已超时,超时时间是5分钟!");
				addActionError("该链接已超时,超时时间是5分钟!");
				return ERROR;
			}
		}catch (Exception e) {
			log.error("传入用户id不存在---"+id, e);
			addActionError("无效的用户");
			return ERROR;
		}
		String check_code = request.getParameter("checkCode");
		if (!GenerateLinkUtils.verifyCheckCode(check_code, customerPojo)) {	
			log.error("check_code不匹配");
			addActionError("无效的用户");
			return ERROR;
		}
		return SUCCESS;
	}
	
	private boolean validatePwd() {
		if (customerPojo.getPwd()==null || customerPojo.getPwd().trim().equals("")) {
			addFieldError("pwdError", "密码不能为空!");
			log.warn("密码不能为空!");
			return false;
		}
		if (customerPojo.getPwd().trim().length()<6 || customerPojo.getPwd().trim().length()>20) {
			addFieldError("pwdError", "密码应为6-20个合法字符!");
			log.warn("密码应为6-20个合法字符!");
			return false;
		}
		return true;
	}
	
	/**
	 * reset pwd
	 * @return
	 */
	public String resetPwd() {
		if(!validatePwd()) return ERROR;
		try {
			customerService.updateCustomerPwd(customerPojo);
		}catch (Exception e) {
			log.error("修改密码失败,请稍候再试!", e);
			addActionError("修改密码失败,请稍候再试!");
			return ERROR;
		}
		addActionMessage("修改密码成功!");
		return SUCCESS;
	}
	
	public void setCustomerPojo(CustomerPojo customerPojo) {
		this.customerPojo = customerPojo;
	}

	public CustomerPojo getCustomerPojo() {
		return customerPojo;
	}

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	public CustomerService getCustomerService() {
		return customerService;
	}

	public void setSession(Map arg0) {
		this.session = arg0;
	}


	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}


	public String getValidateCode() {
		return validateCode;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getNick() {
		return nick;
	}
}
