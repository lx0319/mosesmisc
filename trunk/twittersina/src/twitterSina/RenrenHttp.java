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
 * 利用原始的HTTP请求与响应对人人网进行相关操作
 * @author Steven Wang <http://steven-wang.appspot.com>
 */
public class RenrenHttp 
{
	public RenrenHttp(){}
	
	/**
	* 登录人人网
	* @param userName，人人网账号
	* @param userPwd，人人网密码
	* @return，登录成功：人人网的Cookie字符串，登录失败：null
	*/
	public String loginRenren(String userName, String userPwd)
	{
		//第一次向PLogin.do请求
		String postData = "email=" + userName + "&password=" + userPwd + "&origURL=http%3A%2F%2Fwww.renren.com%2FHome.do&autologin=true&domain=renren.com";
		HttpURLConnection httpURLConnection = null;
		OutputStream httpOutputStream = null;
		String existsCookie = "";
		String cookieStr = null;
		String callbackUrl = null;
		try
		{
			byte[] postByte = postData.getBytes("utf-8");
			URLConnection con = new URL("http://www.renren.com/PLogin.do").openConnection();
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
			httpURLConnection.setRequestProperty("Referer","http://www.renren.com/SysHome.do");
			httpURLConnection.setRequestProperty("UserAgent","Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0)");
			httpURLConnection.setRequestProperty("ContentType","application/x-www-form-urlencoded");
			httpURLConnection.setRequestProperty("Accept-Language","zh-CN");
			httpURLConnection.setRequestProperty("Accept-Encoding","gzip, deflate");
			httpURLConnection.setRequestProperty("Host","passport.renren.com");
			httpURLConnection.setRequestProperty("ContentLength",String.valueOf(postByte.length));
			httpURLConnection.setRequestProperty("Pragma","no-cache");
			httpURLConnection.setRequestProperty("Cookie",existsCookie);
			httpURLConnection.setInstanceFollowRedirects(false);
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setUseCaches(false);
			
			httpOutputStream = httpURLConnection.getOutputStream();
			httpOutputStream.write(postByte);
			
			BufferedReader httpBufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));   
			String responseStr = HttpHelp.readBufferedContent(httpBufferedReader);
			if(responseStr.length() > 500)
			{
				return null;
			}
			String[] tmp = responseStr.split("\\\"");
			if(tmp == null || tmp.length != 3)
			{
				return null;
			}
			callbackUrl = tmp[1];
			
			for(int i=1;httpURLConnection.getHeaderFieldKey(i) != null; i++)
			{
				if(httpURLConnection.getHeaderFieldKey(i).equals("set-cookie"))
				{
					cookieStr = httpURLConnection.getHeaderField(i) + ",";
					break;
				}
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
		
		//第二次向callbackUrl(由第一次请求返回)请求
		if(cookieStr == null)
		{
			return null;
		}
		
		try
		{
			URLConnection con = new URL(callbackUrl).openConnection();
			if(con != null)
			{
				httpURLConnection = (HttpURLConnection)con;
			}
			else
			{
				return null;
			}
			httpURLConnection.setRequestMethod("get");
			httpURLConnection.setRequestProperty("Accept","image/jpeg, application/x-ms-application, image/gif, application/xaml+xml, image/pjpeg, application/x-ms-xbap, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, application/x-shockwave-flash, */*");
			httpURLConnection.setRequestProperty("Referer","http://www.renren.com/SysHome.do");
			httpURLConnection.setRequestProperty("UserAgent","Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0)");
			httpURLConnection.setRequestProperty("ContentType","application/x-www-form-urlencoded");
			httpURLConnection.setRequestProperty("Accept-Language","zh-CN");
			httpURLConnection.setRequestProperty("Accept-Encoding","gzip, deflate");
			httpURLConnection.setRequestProperty("Host","login.renren.com");
			httpURLConnection.setRequestProperty("Pragma","no-cache");
			httpURLConnection.setRequestProperty("Cookie",cookieStr);
			httpURLConnection.setInstanceFollowRedirects(false);
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setUseCaches(false);
			
			for(int i=1;httpURLConnection.getHeaderFieldKey(i) != null; i++)
			{
				if(httpURLConnection.getHeaderFieldKey(i).equals("set-cookie"))
				{
					cookieStr += httpURLConnection.getHeaderField(i);
					break;
				}
			}
			if (cookieStr != null && cookieStr.length() > 0)
			{
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
	* 更新人人网状态
	* @param renrenCookie，人人网的登录Cookie
	* @param publishContent，发布的内容
	* @return，返回发布是否成功
	*/
	public boolean publishRenren(String renrenCookie, String publishContent)
	{
		String postData = "c=" + publishContent + "&raw=" + publishContent + "&";
		HttpURLConnection httpURLConnection = null;
		OutputStream httpOutputStream = null;
		String responseStr = null;
		try
		{
			byte[] postByte = postData.getBytes("utf-8");
			URLConnection con = new URL("http://status.renren.com/doing/update.do?").openConnection();
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
			httpURLConnection.setRequestProperty("Referer","http://status.renren.com/getdoing.do");
			httpURLConnection.setRequestProperty("ContentType","application/x-www-form-urlencoded");
			httpURLConnection.setRequestProperty("UserAgent","Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0)");
			httpURLConnection.setRequestProperty("Host","status.renren.com");
			httpURLConnection.setRequestProperty("ContentLength",String.valueOf(postByte.length));
			httpURLConnection.setRequestProperty("Pragma","no-cache");
			httpURLConnection.setRequestProperty("Cookie",renrenCookie);
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setUseCaches(false);
			httpOutputStream = httpURLConnection.getOutputStream();
			httpOutputStream.write(postByte);
			
			BufferedReader httpBufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));   
			responseStr = HttpHelp.readBufferedContent(httpBufferedReader);
			
			if (responseStr == null || responseStr.length() > 1000 || responseStr.indexOf("updateStatusId") == -1)
			{
				return false;
			}
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
		
		//退出
		try
		{
			URLConnection con = new URL("http://home.renren.com/Logout.do").openConnection();
			if(con != null)
			{
				httpURLConnection = (HttpURLConnection)con;
			}
			else
			{
				return true;
			}
			httpURLConnection.setRequestMethod("get");
			httpURLConnection.setRequestProperty("Accept","image/jpeg, application/x-ms-application, image/gif, application/xaml+xml, image/pjpeg, application/x-ms-xbap, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, application/x-shockwave-flash, */*");
			httpURLConnection.setRequestProperty("UserAgent","Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0)");
			httpURLConnection.setRequestProperty("Accept-Language","zh-CN");
			httpURLConnection.setRequestProperty("Accept-Encoding","gzip, deflate");
			httpURLConnection.setRequestProperty("Host","home.renren.com");
			httpURLConnection.setRequestProperty("Pragma","no-cache");
			httpURLConnection.setRequestProperty("Cookie",renrenCookie);
			httpURLConnection.setInstanceFollowRedirects(false);
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setUseCaches(false);
			httpURLConnection.getInputStream();
		}
		catch(Exception e)
		{
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
			}
		}
		return true;
	}
}
