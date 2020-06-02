package cn.com.geovis.monitor.utils;

import java.io.File;

/**
 *  字符串工具类
 * @author wangda
 * @date      20181108
 */
public class StrUtils {

	/**
	 *    通过分隔符分割成数组
	 * @param str
	 * @param split
	 * @return
	 * @throws Exception
	 */
	 public static String[] parseSplitString(String str,String split) throws Exception {

		                       if(str == null ) {
		                    	   throw new Exception("inputParam str is null");
		                       }

		                       if(split == null) {
		                    	    throw new Exception("inputParam split is null");
		                       }

		                    	 return  str.split(split);
	 }

	 /**
	  *  去除路径的最后的斜杠
	  * @param str
	  * @return
	  * @throws Exception
	  */
	 public static String trimEndWithSep(String str) throws Exception {

					 if(str == null) {
						 throw new Exception("inputParam str is null");
					 }

					 int length = str.length();

					 if(length > 1) {
									 if(str.endsWith(File.separator)) {
							       	             return str.substring(0,length -1);
							         }else if(str.endsWith("/")) {
							       	              return str.substring(0,length -1);
							         }
					 }else {
									if(str.endsWith(File.separator)) {
												   return "";
									}else if(str.endsWith("/")) {
												   return "";
									}

					 }

					 return str;

	 }

}
