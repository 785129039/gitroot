package com.gy.bean;

import java.util.Date;

public class CustomerPojo {
	public CustomerPojo() {
	}
	private int id;
	private String nick;
	private String realname;
	private int qq;
	private String telephone;
	private String address;
	private int sex;
	private String identity;
	private String pwd;
	private String email;
	private String random_code;
	private int isactivity;
	private Date registDate;
	private Date updatePwdTime;
	
	public String toString() {
		return "CustomerPojo [id=" + id + ", nick=" + nick + ", realname="
				+ realname + ", qq=" + qq + ", telephone=" + telephone
				+ ", address=" + address + ", sex=" + sex + ", identity="
				+ identity + ", pwd=" + pwd + ", email=" + email
				+ ", random_code=" + random_code + ", isactivity=" + isactivity
				+ ", registDate=" + registDate + ", updatePwdTime="
				+ updatePwdTime + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public int getQq() {
		return qq;
	}
	public void setQq(int qq) {
		this.qq = qq;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public String getIdentity() {
		return identity;
	}
	public void setIdentity(String identity) {
		this.identity = identity;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setIsactivity(int isactivity) {
		this.isactivity = isactivity;
	}
	public int getIsactivity() {
		return isactivity;
	}
	public void setRandom_code(String random_code) {
		this.random_code = random_code;
	}
	public String getRandom_code() {
		return random_code;
	}
	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}
	public Date getRegistDate() {
		return registDate;
	}
	public void setUpdatePwdTime(Date updatePwdTime) {
		this.updatePwdTime = updatePwdTime;
	}
	public Date getUpdatePwdTime() {
		return updatePwdTime;
	}
}
