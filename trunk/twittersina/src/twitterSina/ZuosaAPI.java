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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import twitter4j.internal.http.BASE64Encoder;
import twitterSina.common.HttpHelp;

/**
 * 利用API对做啥进行相关操作
 * @author Steven Wang <http://steven-wang.appspot.com>
 */
public class ZuosaAPI 
{
	/**
	* 验证做啥账号
	* @param userName，做啥账号
	* @param userPwd，做啥密码
	* @return，是否验证成功
	*/
	public boolean loginZuosa(String userName, String userPwd)
	{
		HttpURLConnection httpURLConnection = null;
		String responseStr = null;
		String auth = userName + ":" + userPwd;
		try
		{
			URLConnection con = new URL(" http://api.zuosa.com/account/verify_credentials.json").openConnection();
			if(con != null)
			{
				httpURLConnection = (HttpURLConnection)con;
			}
			else
			{
				return false;
			}
			httpURLConnection.setRequestMethod("get");
			httpURLConnection.setRequestProperty("Authorization","Basic " + BASE64Encoder.encode(auth.getBytes("utf-8")));
			httpURLConnection.setDoOutput(true);

			BufferedReader httpBufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));   
			responseStr = HttpHelp.readBufferedContent(httpBufferedReader);
			
			if (responseStr != null && responseStr.length() > 0 && 
					responseStr.trim().indexOf("\"authorized\":true") != -1)
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
				httpURLConnection.disconnect();
			}
			catch(Exception e)
			{
				return false;
			}
		}
	}
	
	/**
	* 更新做啥状态
	* @param userName，做啥账号
	* @param userPwd，做啥密码
	* @param publishContent，发布的内容
	* @return，返回发布是否成功
	*/
	public boolean publishZuosa(String userName, String userPwd, String publishContent)
	{
		String auth = userName + ":" + userPwd;
		String postData = null;
		try 
		{
			postData = "status=" + URLEncoder.encode(publishContent,"utf-8");
		}
		catch (UnsupportedEncodingException e1)
		{
		}
		HttpURLConnection httpURLConnection = null;
		OutputStream httpOutputStream = null;
		String responseStr = null;
		try
		{
			URLConnection con = new URL("http://api.zuosa.com/statuses/update.json").openConnection();
			if(con != null)
			{
				httpURLConnection = (HttpURLConnection)con;
			}
			else
			{
				return false;
			}
			httpURLConnection.setRequestMethod("post");
			httpURLConnection.setRequestProperty("Authorization","Basic " + BASE64Encoder.encode(auth.getBytes("utf-8")));
			httpURLConnection.setDoOutput(true);
			httpOutputStream = httpURLConnection.getOutputStream();
			httpOutputStream.write(postData.getBytes("utf-8"));

			BufferedReader httpBufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));   
			responseStr = HttpHelp.readBufferedContent(httpBufferedReader);
			
			if (responseStr != null && responseStr.length() > 0 && responseStr.indexOf("created_at") != -1)
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
}
