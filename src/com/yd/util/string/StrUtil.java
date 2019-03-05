package com.yd.util.string;

import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StrUtil {

	
	public static String toStr(Object object){
		if(object!=null){
			return object.toString();
		}
		return null;
	}
	public static long toLong(String s) {
		long r = 0L;
		try {
			r = Long.parseLong(s);
		} catch (Exception e) {
		}
		return r;
	}

	public static int toInt(String s) {
		int r = 0;
		try {
			r = Integer.parseInt(s);
		} catch (Exception e) {
		}
		return r;
	}
	

	public static float toFloat(String s) {
		float r = 0;
		try {
			r = Float.parseFloat(s);
		} catch (Exception e) {
		}
		return r;
	}
	
	public static double toDouble(String s) {
		double r = 0;
		try {
			r = Double.parseDouble(s);
		} catch (Exception e) {
		}
		return r;
	}
	
	public static Date toDate(String s) {
		Date r = new Date(0);
		try {
			r = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(s);
		} catch (ParseException e) {
		}
		return r;
	}

	public static String MD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] buf = s.getBytes();
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(buf);
			byte[] md = md5.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 判断字符串是否为数字
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str) {
		if (str == null) {
			return false;
		}
		int length = str.length();
		if (length == 0) {
			return false;
		}
		int i = 0;
		if (str.charAt(0) == '-') {
			if (length == 1) {
				return false;
			}
			i = 1;
		}
		for (; i < length; i++) {
			char c = str.charAt(i);
			if (c <= '/' || c >= ':') {
				return false;
			}
		}
		return true;
	}
	
	public static boolean isEmpty(String str) {
		if(str != null){
			if(!"".equals(str.trim()) && !"null".equals(str)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 数值取余，字符串取【长度和值和】的余
	 * 
	 * @param key
	 * @param mod
	 * @return
	 */
	public static int getHash(String key, int mod) {
		int r = 0;
		if (isNumber(key)) {
			r = (int) (toLong(key) % mod);
		} else {
			long hash = key.length();
			for (int i = 0; i < key.length(); i++) {
				hash += key.charAt(i);
			}
			r = (int) (hash % mod);
		}

		return r;
	}
	
	/**
	 * 根据字符串返回日期
	 * @param time
	 * @return
	 */
	public static long parse(String time){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = df.parse(time);
		} catch (Exception e) {
			return 0L;
		}
		return date.getTime();
		
	}
	
	public static String dateFormat(long time){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(time);
		return df.format(date);
		
	}
	
	public static boolean compareDate(long date1,long date2){
		@SuppressWarnings("unused")
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		boolean msg=false;
			Date d1 =new Date(date1);
			Date d2 =new Date(date2);
			msg=d1.after(d2);
			return msg;
	}
}
