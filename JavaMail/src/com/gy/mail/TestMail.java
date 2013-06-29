package com.gy.mail;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.gy.util.PropertiesUtil;
/**
 * java Mail send mail(contain html, image, attachment)
 * 
 * Note: sender's email must has been open smtp/pop3 service
 *		 encoding=utf-8
 * @author liqiang  2013-6-28
 *
 */
public class TestMail {
	public static void main(String[] args) throws MessagingException,
			FileNotFoundException, IOException {
		PropertiesUtil util = PropertiesUtil.getInstance("email.properties");
		
		String address = "C:/Documents and Settings/Administrator/My Documents/My Pictures/beautiful.jpg";
		String body = "<a href=http://www.qq.com>腾讯官网</a><hr/><image src='cid:beautiful' />";
		Properties props = new Properties();
		props.load(Class.class.getResourceAsStream("/email.properties"));

		Session session = Session.getInstance(props);

		Message msg = new MimeMessage(session);
		msg.setSubject("小伙");
		msg.setFrom(new InternetAddress(util.getValue("from")));
		msg.setSentDate(new Date());
		MimeMultipart mm = new MimeMultipart("related");	//related, mixed, alternative

		MimeBodyPart htmlMBP = new MimeBodyPart();
		htmlMBP.setContent(body, "text/html;charset=utf-8");
		mm.addBodyPart(htmlMBP);

		MimeBodyPart imageMBP = new MimeBodyPart();
		FileDataSource fds = new FileDataSource(address);
		DataHandler ds = new DataHandler(fds);
		imageMBP.setDataHandler(ds);
		imageMBP.setContentID("beautiful");
		mm.addBodyPart(imageMBP);
		
		MimeBodyPart attachMBP =new MimeBodyPart();
		attachMBP.attachFile(address);
		attachMBP.setFileName("you know!");
		mm.addBodyPart(attachMBP);

		msg.setContent(mm);
		msg.saveChanges();

		Transport transport = session.getTransport();
		transport.connect(util.getValue("user"), util.getValue("pwd"));

		String addList = "xxxxxx@qq.com, xxxx@qq.com, xxxxx@qq.com, xxxxxx@qq.com, xxxxxxxx@163.com";
		transport.sendMessage(msg, InternetAddress.parse(addList));
		transport.close();

	}

}
