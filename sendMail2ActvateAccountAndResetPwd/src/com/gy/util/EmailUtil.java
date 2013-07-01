package com.gy.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.zc.zcproject.pojo.CustomerPojo;

public class EmailUtil {
	public static String getPropertiesValue(String key) {
		Properties props = new Properties();
		InputStream inStream = null;
		try {
			inStream = new FileInputStream(Thread.currentThread().getContextClassLoader().getResource("").getPath()+"email.properties");
			props.load(inStream);
			return props.getProperty(key);
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(inStream!=null) {
				try {
					inStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return "";
	}
	
	
	
	public static boolean sendRegisterMail(CustomerPojo pojo) throws AddressException,
			MessagingException {
		Session session = getSession();
		Message msg = new MimeMessage(session);
		msg.setSubject(getPropertiesValue("subject"));
		msg.setSentDate(new Date());
		msg.setFrom(new InternetAddress(getPropertiesValue("fromEmail")));
		msg.setRecipient(RecipientType.TO, new InternetAddress(getPropertiesValue("fromEmail")));
		String url = GenerateLinkUtils.generateActivateLink(pojo);
		msg.setContent(
				"<h2>恭喜您成功注册!<h2>xgt9(3d模型下载)为室内设计师与绘图员提供全面的3D模型、最新最全的3D贴图下载、强大的搜索优化功能，我们将一直致力为室内设计师提供动力。" +
				"<br/>请点击以下链接，也可复制此链接访问，即可激活您的账号<br/><a href='"
						+ url+ "'>"+url+"</a><br/><hr/>如有疑问,请联系客服：s@xgt9.com", "text/html;charset=utf-8");
		Transport.send(msg);
		return true;
	}
	
	public static boolean sendPwdMail(CustomerPojo pojo) throws MessagingException{		
		Session session = getSession();
		Message msg = new MimeMessage(session);
		msg.setSubject(getPropertiesValue("subject"));
		msg.setDescription("XGT9");
		msg.setSentDate(new Date());
		msg.setFrom(new InternetAddress(getPropertiesValue("fromEmail")));
		msg.setRecipient(RecipientType.TO, new InternetAddress(pojo.getEmail()));
		String url = GenerateLinkUtils.generatePwdLink(pojo);
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd HH:mm:ss");
		String dateStr = sdf.format(date);
		msg.setContent(
				"<h2>亲爱的用户：<h2>您好！<br/>您在"+dateStr+"提交了邮箱找回密码请求，请点击下面的链接修改密码。"+
				"<br/><a href='"+ url+ "'>"+url+"</a><br/>" +
				"<font>(如果您无法点击此链接，请将它复制到浏览器地址栏后访问)</font>" +
				"<br/><hr/>如有疑问,请联系客服：s@xgt9.com", "text/html;charset=utf-8");
		Transport.send(msg);
		return true;
	}
	

	private static Session getSession() {
		Properties props = new Properties();
		props.setProperty("mail.smtp.host", getPropertiesValue("mail.smtp.host"));
		props.setProperty("mail.smtp.auth", getPropertiesValue("mail.smtp.auth"));
		props.setProperty("mail.transport.protocol", getPropertiesValue("mail.transport.protocol"));
		Session session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(getPropertiesValue("user"),
						getPropertiesValue("pwd"));
			}
		});
		return session;
	}

	public static void main(String[] args) throws AddressException,
			MessagingException {
		/*CustomerPojo pojo = new CustomerPojo();
		pojo.setNick("liqiang");
		pojo.setRandom_code(UUID.randomUUID().toString());
		pojo.setEmail("785129039@qq.com");
		EmailUtil.sendPwdMail(pojo);*/
		System.out.println(getPropertiesValue("pwd"));
	}
}
