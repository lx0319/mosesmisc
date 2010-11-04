/*
 *  Copyright (c) 2009, Steven Wang
 *  
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *      
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  
 *  twitterSina at http://twitterSina.appspot.com
 *  twitterSina code at http://twitterSina.googlecode.com
 * 	
 */
package twitterSina;

import java.net.*;
import java.io.*; 
import twitterSina.common.*;

/**
 * 利用原始的HTTP请求与响应对新浪围脖进行相关操作
 * @author Steven Wang <http://steven-wang.appspot.com>
 */
public class SinaHttp 
{
	public SinaHttp(){}
	
	/**
	* 登录新浪围脖
	* @param userName，新浪围脖账号
	* @param userPwd，新浪围脖密码
	* @return，登录成功：新浪围脖的Cookie字符串，登录失败：null
	*/
	public String loginSina(String userName, String userPwd)
	{
		String postData = "username=" + userName + "&password=" + userPwd + "&service=miniblog&encoding=utf-8&gateway=1&savestate=7&useticket=0&url=http%3A%2F%2Ft.sina.com.cn%2Fajaxlogin.php%3Fframelogin%3D1%26callback%3Dparent.sinaSSOController.feedBackUrlCallBack&returntype=META";
		HttpURLConnection httpURLConnection = null;
		OutputStream httpOutputStream = null;
		String existsCookie = "_s_upa=5; Apache=74.125.155.141.788991254079255269";
		try
		{
			byte[] postByte = postData.getBytes("utf-8");
			URLConnection con = new URL("http://login.sina.com.cn/sso/login.php").openConnection();
			if(con != null)
			{
				httpURLConnection = (HttpURLConnection)con;
			}
			else
			{
				return null;
			}
			httpURLConnection.setRequestMethod("post");
			httpURLConnection.setRequestProperty("Accept","image/jpeg, application/x-ms-application, image/gif, application/xaml+xml, image/pjpeg, application/x-ms-xbap, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, application/x-shockwave-flash, */*");
			httpURLConnection.setRequestProperty("Referer","http://t.sina.com.cn/login.php?url=http%3A%2F%2Ft.sina.com.cn%2F");
			httpURLConnection.setRequestProperty("UserAgent","Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0)");
			httpURLConnection.setRequestProperty("ContentType","application/x-www-form-urlencoded");
			httpURLConnection.setRequestProperty("Accept-Language","zh-CN");
			httpURLConnection.setRequestProperty("Accept-Encoding","gzip, deflate");
			httpURLConnection.setRequestProperty("Host","login.sina.com.cn");
			httpURLConnection.setRequestProperty("ContentLength",String.valueOf(postByte.length));
			httpURLConnection.setRequestProperty("Pragma","no-cache");
			httpURLConnection.setRequestProperty("Cookie",existsCookie);
			httpURLConnection.setRequestProperty("Cache-Control","no-cache");
			httpURLConnection.setInstanceFollowRedirects(false);
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setUseCaches(false);
			
			httpOutputStream = httpURLConnection.getOutputStream();
			httpOutputStream.write(postByte);
			
			String cookieStr = null;
			for(int i=1;httpURLConnection.getHeaderFieldKey(i) != null; i++)
			{
				if(httpURLConnection.getHeaderFieldKey(i).equals("set-cookie"))
				{
					cookieStr = httpURLConnection.getHeaderField(i);
					break;
				}
			}
			if (cookieStr != null && cookieStr.length() > 0 && cookieStr.indexOf("delete") == -1)
			{
//				cookieStr = replace(cookieStr, "path=/;domain=.sina.com.cn;Httponly,", "");
//				cookieStr = replace(cookieStr, "path=/;domain=.sina.com.cn,", "");
//				cookieStr = replace(cookieStr, "path=/;domain=login.sina.com.cn;Httponly,", "");
//				cookieStr = replace(cookieStr, "domain=login.sina.com.cn; path=/; Httponly,", "");
//				cookieStr = replace(cookieStr, "domain=sina.com.cn;path=/;Httponly;", "");
//				cookieStr = replace(cookieStr, "domain=sina.com.cn;path=/;,", "");
//				cookieStr = replace(cookieStr, "path=/;domain=.sina.com.cn", "");
				cookieStr = replace(cookieStr, "Httponly,", "");
				cookieStr = replace(cookieStr, "Httponly;,", "");
//				cookieStr = cookieExpirefilter(cookieStr);
				
				return cookieStr;
			}
		}
		catch(Exception e)
		{
			return null;
		}
		finally
		{
			try
			{
				httpOutputStream.close();
				httpURLConnection.disconnect();
			}
			catch(Exception e)
			{
				return null;
			}
		}
		return null;
	}
	
	/**
	* 发布围脖消息
	* @param sinaCookie，围脖的登录Cookie
	* @param publishContent，发布的内容
	* @return，返回发布是否成功
	*/
	public boolean publishSina(String sinaCookie, String publishContent)
	{
		String postData = "content=" + publishContent + "&pic=&from=myprofile";
		HttpURLConnection httpURLConnection = null;
		OutputStream httpOutputStream = null;
		String responseStr = null;
		try
		{
			byte[] postByte = postData.getBytes("utf-8");
			URLConnection con = new URL("http://t.sina.com.cn/mblog/publish.php").openConnection();
			if(con != null)
			{
				httpURLConnection = (HttpURLConnection)con;
			}
			else
			{
				return false;
			}
			httpURLConnection.setRequestMethod("post");
			httpURLConnection.setRequestProperty("Accept","*/*");
			httpURLConnection.setRequestProperty("Accept-Language","zh-CN");
			httpURLConnection.setRequestProperty("Referer","http://t.sina.com.cn");
			httpURLConnection.setRequestProperty("ContentType","application/x-www-form-urlencoded");
			httpURLConnection.setRequestProperty("UserAgent","Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0)");
			httpURLConnection.setRequestProperty("Host","t.sina.com.cn");
			httpURLConnection.setRequestProperty("ContentLength",String.valueOf(postByte.length));
			httpURLConnection.setRequestProperty("Pragma","no-cache");
			httpURLConnection.setRequestProperty("Cookie",sinaCookie);
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setUseCaches(false);
			httpOutputStream = httpURLConnection.getOutputStream();
			httpOutputStream.write(postByte);
			
			BufferedReader httpBufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));   
			responseStr = HttpHelp.readBufferedContent(httpBufferedReader);
			if (responseStr != null && responseStr.length() > 70 && responseStr.indexOf("A00006") != -1)
			{
				return true;
			}
            return false;
		}
		catch(Exception e)
		{
			return false;
		}
		finally
		{
			try
			{
				httpOutputStream.close();
				httpURLConnection.disconnect();
			}
			catch(Exception e)
			{
				return false;
			}
		}
	}
	
	private String replace(String line, String oldString, String newString) 
	{
		if (line == null) 
		{
			return null;
		}
		int i = 0;
		if ((i = line.indexOf(oldString, i)) >= 0) 
		{
			char[] line2 = line.toCharArray();
			char[] newString2 = newString.toCharArray();
			int oLength = oldString.length();
			StringBuffer buf = new StringBuffer(line2.length);
			buf.append(line2, 0, i).append(newString2);
			i += oLength;
			int j = i;
			while ((i = line.indexOf(oldString, i)) > 0) 
			{
				buf.append(line2, j, i - j).append(newString2);
				i += oLength;
				j = i;
			}
			buf.append(line2, j, line2.length - j);
			return buf.toString();
		}
		return line;
	}
	
	@SuppressWarnings("unused")
	private String cookieExpirefilter(String cookieStr)
	{
		int expireIndex = cookieStr.indexOf("expires");
		int currentIndex = 0;
		while(expireIndex != -1)
		{
			currentIndex = expireIndex + 1;
			String tmpStr = cookieStr.substring(0, expireIndex);
			while(cookieStr.charAt(currentIndex) != ';')
			{
				currentIndex++;
			}
			tmpStr += cookieStr.substring(++currentIndex, cookieStr.length());
			cookieStr = tmpStr;
			
			expireIndex = cookieStr.indexOf("expires");
		}
		return cookieStr;
	}
}
