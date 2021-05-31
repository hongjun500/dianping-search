package com.hongjun.app.controller.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* @Title: ValidateUtil.java 
* @Package com.szngw.nl.util
* @Description: 数据验证工具类 
* @author
* @date 2016年2月22日 上午10:55:35 
* @version V0.1
 */
public class ValidateUtil {


	
	/**
	 * 验证字符串是否为空
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str){
		return str==null || str.trim().length()==0;
	}

	/**
	 * 验证字符串长度
	 * @param str 字符串
	 * @param min 最小长度
	 * @param max 最大长度
	 * @return
	 */
	public static boolean validateStrLen(String str,int min,int max){
		if(isEmpty(str)){
			return false;
		}
		if(max < min){
			return false;
		}
		if(str.trim().length()>=min && str.trim().length()<=max){
			return true;
		}
		return false;
	}
	
	/**
	 * 验证email
	 * @param email
	 * @return
	 */
	public static boolean validateEmail(String email){
		Pattern emailPattern = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
		return emailPattern.matcher(email).matches();
	}
	
	/**
	 * 带长度的验证email
	 * @param email
	 * @param min
	 * @param max
	 * @return
	 */
	public static boolean validateEmail(String email , int min , int max){
		if(!validateStrLen(email,min,max)){
			return false;
		}
		return validateEmail(email);
	}
	
	/**
	 * 验证手机号码
	 * @param mobile
	 * @return
	 */
	public static boolean validateMobile(String mobile){
		if(isEmpty(mobile)){
			return false;
		}
        Pattern phonePattern = Pattern.compile("^[1][3,4,5,8,7][0-9]{9}$"); // 验证手机号
        Matcher matcher = phonePattern.matcher(mobile);
        return matcher.matches();
	}

	/**
	 * 验证固定电话
	 * @param fixedPhone
	 * @return
	 */
	public static boolean validateFixedPhone(String fixedPhone){
		String reg="([0-9]{3,4}-)?[0-9]{7,8}";
		return Pattern.matches(reg, fixedPhone);
	}

	/**
	 * 验证字符串是否是纯数字
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str){
		if(isEmpty(str)){
			return false;
		}
        Pattern phonePattern = Pattern.compile("[0-9]+$"); 
        if(!phonePattern.matcher(str).matches()){
        	return false;
		}
		try{
        	Long.valueOf(str);
        	return true;
		}catch(Exception e){
			return false;
		}
	}
	
	/**
	 * 验证字符串是否是带小数的纯数字
	 * @return
	 */
	public static boolean isNumberDecimal(String str){
		return str.matches("[\\d]+") || str.matches("[\\d.]+");
	}


	/**
	 * 验证字符串str长度,是否在最小最大之间 否就返回false
	 * date:2016年4月26日
	 */
	public static boolean validateStrScopePass(String str,int min,int max){
		if(isEmpty(str) || str.length()<min||str.length()>max)
		      return false;
		return true;
	}
	
	/**
	 * 验证年月日字符串位数必须为10位如下
	 * 2008-01-01
	 * @param date
	 * @return
	 */
	public static boolean validateDate(String date){
		if(isEmpty(date)){
			return false;
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			format.parse(date);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}
	
	/**
	 * 验证年月日字符串位数
	 * 2008-01-01 12:12:12
	 * @param str
	 * @return
	 */
	public static boolean validateDateTime(String str) {
		if(isEmpty(str)){
			return false;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			sdf.parse(str);
			return true;
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 验证年月日字符串位数
	 * 2008-01-01 12:12:12
	 * @param str
	 * @return
	 */
	public static boolean validateDateHouse(String str) {
		if(isEmpty(str)){
			return false;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			sdf.parse(str);
			return true;
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 验证年月 
	 * @param yearMonth
	 * @return
	 */
	public static boolean validateYearMonth(String yearMonth){
		if(isEmpty(yearMonth)){
			return false;
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		try {
			format.parse(yearMonth);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}
	
	/**
	 * 校验身份证号 15和18位都可以校验
	 * date:2016年12月2日
	 */
	public static boolean validateCarId(String carId){
		Pattern phonePattern = Pattern.compile("^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$|^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$");
		return phonePattern.matcher(carId).matches();
	}

	/**
	 * 校验特殊字符
	 * @param character
	 * @return
	 */
	public static boolean validateSpeChar(String character){
		String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		Pattern p = Pattern.compile(regEx);
		return p.matcher(character).matches();
	}

	/**
	 * 过滤特殊字符
	 * @param str
	 * @return
	 */
	public static String filterSpeChar(String str){
		String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}

	/**
	 * 校验qq号
	 * @param qq
	 * @return
	 */
	public static boolean validateQQ(String qq){
		Pattern pattern = Pattern.compile("[1-9][0-9]{5,9}");
		return pattern.matcher(qq).matches();
	}

	/**
	 * 校验微信号
	 * @param weixin
	 * @return
	 */
	public static boolean validateWeixin(String weixin){
		Pattern pattern = Pattern.compile("^[a-zA-Z0-9_-]{6,20}$");
		return pattern.matcher(weixin).matches();
	}

	/**
	 * 校验支付宝账号
	 * @param zfb
	 * @return
	 */
	public static boolean validateZfb(String zfb){
		return validateEmail(zfb) || validateMobile(zfb);
	}

	/**
	 * 校验上传文件名称
	 * @param uploadFileName
	 * @return
	 */
	public static  boolean validateUploadFileName(String uploadFileName){
		if(isEmpty(uploadFileName)){
			return false;
		}
		if(!validateStrLen(uploadFileName,13,20)){
			return false;
		}
		if(uploadFileName.indexOf(".") == -1){
			return false;
		}
		String[] spl = uploadFileName.split("\\.");
		Pattern pattern = Pattern.compile("[0-9]{13}+$");
		if(!pattern.matcher(spl[0]).matches()){
			return false;
		}
		if(!validateStrLen(spl[1],1,6)){
			return false;
		}
		return true;
	}

	/**
	 * 校验经度
	 * @param lng
	 * @return
	 */
	public static boolean validateLng(String lng){
		if(isEmpty(lng) || !isNumberDecimal(lng)){
			return false;
		}
		if(lng.indexOf("\\.") != -1){
			String suffix = lng.substring(lng.indexOf("."),lng.length());
			if(suffix.length() > 6){
				return false;
			}
		}
		double ln = Math.abs(Double.valueOf(lng));
		return ln>=0 && ln<=180;
	}

	/**
	 * 校验经度
	 * @param lat
	 * @return
	 */
	public static boolean validateLat(String lat){
		if(isEmpty(lat) || !isNumberDecimal(lat)){
			return false;
		}
		if(lat.indexOf("\\.") != -1){
			String suffix = lat.substring(lat.indexOf("."),lat.length());
			if(suffix.length() > 6){
				return false;
			}
		}
		double la = Math.abs(Double.valueOf(lat));
		return la>=0 && la<=90;
	}
	
	/**
	 * 校验ip地址的正确性（IPv4）
	 * @param ip
	 * @return
	 */
	public static boolean validateIp(String ip){
		Pattern pattern = Pattern.compile("\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b");
		Matcher matcher = pattern.matcher(ip);
		return matcher.matches();
	}
	
	/**
	 * 正则校验MAC合法性
	 * @param mac
	 * @return
	 */
	public static boolean validateMac(String mac){
		Pattern pattern =  Pattern.compile("[0-9A-F]{2}-[0-9A-F]{2}-[0-9A-F]{2}-[0-9A-F]{2}-[0-9A-F]{2}-[0-9A-F]{2}");  
		Matcher matcher = pattern.matcher(mac);
		return matcher.matches();
	}


	/**
	 * 返回校验结果(map转为json对象字符串，便于浏览器展示)
	 * @param map
	 * @param code 0成功 1失败
	 * @param msg 提示信息
	 * @return
	 */

	/**
	 * 校验密码
	 *
	 * date:2016年11月25日
	 */
	public static boolean validatePassWord(String passWord){
		Pattern phonePattern = Pattern.compile("^([A-Z]|[a-z]|[0-9]|[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]){6,15}$");
		return phonePattern.matcher(passWord).matches();
	}

	/**
	 * 根据正则校验
	 * @param str
	 * @return
	 */
	public static boolean validateByPattern(String pat,String str){
		if(isEmpty(pat) || isEmpty(str)){
			return false;
		}
		return Pattern.compile(pat).matcher(str).matches();
	}
}
