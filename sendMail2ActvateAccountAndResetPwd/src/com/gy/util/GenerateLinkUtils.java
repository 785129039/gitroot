package com.gy.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import com.zc.zcproject.pojo.CustomerPojo;

public class GenerateLinkUtils {
	private static final String CHECK_CODE = "checkCode";
	
	public static String generateActivateLink(CustomerPojo pojo) {
		return EmailUtil.getPropertiesValue("project_url")+"zzsc/customer/activiteAccount.html?id="
				+ pojo.getId()+"&"+CHECK_CODE+"="+generateCheckCode(pojo);
	}
	private static String generateCheckCode(CustomerPojo pojo) {
		String nick = pojo.getNick();
		String random = pojo.getRandom_code();
		return md5(nick+":"+random);
	}
	public static String md5(String string) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("md5");
			md.update(string.getBytes());
			byte[] md5Byte = md.digest();
			return byte2Hex(md5Byte);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
	private static String byte2Hex(byte[] md5Byte) {
		StringBuffer strBuf = new StringBuffer();  
        for (int i = 0; i < md5Byte.length; i++)  
        {  
            if(md5Byte[i] >= 0 && md5Byte[i] < 16)  
            {  
                strBuf.append("0");  
            }  
            strBuf.append(Integer.toHexString(md5Byte[i] & 0xFF));  
        }  
        return strBuf.toString();  
	}
	
	public static boolean verifyCheckCode(String check_code2,
			CustomerPojo customerPojo) {
		if (check_code2.equals(generateCheckCode(customerPojo))) {
			return true;
		}
		return false;
	}
	
	public static String generatePwdLink(CustomerPojo pojo) {
		return EmailUtil.getPropertiesValue("project_url")+"zzsc/customer/resetPwdVerify.html?id="
		+ pojo.getId()+"&"+CHECK_CODE+"="+generateCheckCode(pojo);
	}
}
