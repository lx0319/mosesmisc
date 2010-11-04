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

import twitter4j.*;

/**
 * 利用API对Twitter进行相关操作
 * @author Steven Wang <http://steven-wang.appspot.com>
 */
public class TwitterAPI 
{
	public TwitterAPI(){}
	
	/**
	* 登录loginTwitter
	* @param userName，twitter账号
	* @param userPwd，twitter密码
	* @return，返回登录是否成功
	*/
	public boolean loginTwitter(String userName, String userPwd)
	{
		try
		{
			User user = new Twitter(userName,userPwd).verifyCredentials();
			if(user != null)
				return true;
		}
		catch(TwitterException e){}
		return false;
//		return true;
	}
	
	/**
	* 发布Twitter消息
	* @param twitterUserName，twitter账号
	* @param twitterUserPwd，twitter密码
	* @param publishContent，发布的内容
	* @return，返回发布是否成功
	*/
	public boolean publishTwitter(String twitterUserName, String twitterUserPwd, String publishContent)
	{
		TwitterFactory TF = new TwitterFactory();
//		Twitter twitter = new Twitter(twitterUserName,twitterUserPwd);
		Twitter twitter = TF.getInstance(twitterUserName,twitterUserPwd);
		try
		{
//			twitter = new Twitter(twitterUserName,twitterUserPwd);
			twitter = TF.getInstance(twitterUserName,twitterUserPwd);
//			twitter.setClientURL("http://twittersina.appspot.com");
//			twitter.setClientVersion("http://twittersina.appspot.com");
//			twitter.setSource("twittersina");
			twitter.updateStatus(publishContent);
			return true;
		}
		catch(TwitterException e)
		{
			return false;
		}
//		return false;
	}
}
