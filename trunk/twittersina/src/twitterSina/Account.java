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

/**
 * 账户实体类
 * @author Steven Wang <http://steven-wang.appspot.com>
 */
public class Account implements java.io.Serializable
{
	private static final long serialVersionUID = 4993571211160268915L;
	private String userName;
	private String userPwd;
	private int accountType;
	private boolean isAuth;
	
	public String getUserName() 
	{
		return userName;
	}
	
	public void setUserName(String userName)
	{
		this.userName = userName;
	}
	
	public String getUserPwd() 
	{
		return userPwd;
	}
	
	public void setUserPwd(String userPwd) 
	{
		this.userPwd = userPwd;
	}
	
	public int getAccountType() 
	{
		return accountType;
	}
	
	public void setAccountType(int accountType) 
	{
		this.accountType = accountType;
	}
	
	public boolean isAuth() 
	{
		return isAuth;
	}

	public void setAuth(boolean isAuth) 
	{
		this.isAuth = isAuth;
	}

	public String getAccountTypeName()
	{
		switch(this.accountType)
		{
			case AccountType.TWITTER:
				return AccountType.TWITTERNAME;
			case AccountType.SINA:
				return AccountType.SINANAME;
			case AccountType.RENREN:
				return AccountType.RENRENNAME;
			case AccountType.DIGU:
				return AccountType.DIGUNAME;
			case AccountType.ZUOSA:
				return AccountType.ZUOSANAME;
			default:
				return "";
		}
	}
	
	public void setAccountTypeName()
	{
		
	}
}
