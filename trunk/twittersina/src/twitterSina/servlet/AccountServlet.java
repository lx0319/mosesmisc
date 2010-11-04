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
package twitterSina.servlet;

import java.io.*; 
import java.net.*;
import java.util.*; 
import java.util.logging.Logger;
import javax.servlet.*; 
import javax.servlet.http.*;
import twitterSina.*;
import twitterSina.common.*;

/**
 * 用户账户相关的Servlet
 * @author Steven Wang <http://steven-wang.appspot.com>
 */
public class AccountServlet extends HttpServlet
{
	private static final long serialVersionUID = -2632036842196276788L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException
	{
		doPost(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException
	{
		String twitterUserName = request.getParameter("twitterUserNameTxt");
		String twitterUserPwd = request.getParameter("twitterUserPwdTxt");
		String sinaUserName = request.getParameter("sinaUserNameTxt");
		String sinaUserPwd = request.getParameter("sinaUserPwdTxt");
		String renrenUserName = request.getParameter("renrenUserNameTxt");
		String renrenUserPwd = request.getParameter("renrenUserPwdTxt");
		String diguUserName = request.getParameter("diguUserNameTxt");
		String diguUserPwd = request.getParameter("diguUserPwdTxt");
		String zuosaUserName = request.getParameter("zuosaUserNameTxt");
		String zuosaUserPwd = request.getParameter("zuosaUserPwdTxt");
		if(twitterUserName == null || twitterUserPwd == null ||
				sinaUserName == null || sinaUserPwd == null ||
				renrenUserName == null || renrenUserPwd == null ||
				diguUserName == null || diguUserPwd == null ||
				zuosaUserName == null || zuosaUserPwd == null)
		{
			response.sendRedirect("account.jsp");
		}
		
		//将各个同步源的账号信息放入一个集合中
		List<Account> accountList = new ArrayList<Account>();
		Account account = null;
		StringBuffer userNameSb = new StringBuffer();   //for write log
		
		if(twitterUserName.length() > 0 && twitterUserPwd.length() > 0)
		{
			account = new Account();
			account.setAccountType(AccountType.TWITTER);
			account.setUserName(twitterUserName);
			account.setUserPwd(twitterUserPwd);
			accountList.add(account);
			
			userNameSb.append(AccountType.TWITTERNAME);
			userNameSb.append("-");
			userNameSb.append(account.getUserName());
			userNameSb.append(",");
		}
		
		if(sinaUserName.length() > 0 && sinaUserPwd.length() > 0)
		{
			account = new Account();
			account.setAccountType(AccountType.SINA);
			account.setUserName(sinaUserName);
			account.setUserPwd(sinaUserPwd);
			accountList.add(account);
			
			userNameSb.append(AccountType.SINANAME);
			userNameSb.append("-");
			userNameSb.append(account.getUserName());
			userNameSb.append(",");
		}
		
		if(renrenUserName.length() > 0 && renrenUserPwd.length() > 0)
		{
			account = new Account();
			account.setAccountType(AccountType.RENREN);
			account.setUserName(renrenUserName);
			account.setUserPwd(renrenUserPwd);
			accountList.add(account);
			
			userNameSb.append(AccountType.RENRENNAME);
			userNameSb.append("-");
			userNameSb.append(account.getUserName());
			userNameSb.append(",");
		}
		
		if(diguUserName.length() > 0 && diguUserPwd.length() > 0)
		{
			account = new Account();
			account.setAccountType(AccountType.DIGU);
			account.setUserName(diguUserName);
			account.setUserPwd(diguUserPwd);
			accountList.add(account);
			
			userNameSb.append(AccountType.DIGUNAME);
			userNameSb.append("-");
			userNameSb.append(account.getUserName());
			userNameSb.append(",");
		}
		
		if(zuosaUserName.length() > 0 && zuosaUserPwd.length() > 0)
		{
			account = new Account();
			account.setAccountType(AccountType.ZUOSA);
			account.setUserName(zuosaUserName);
			account.setUserPwd(zuosaUserPwd);
			accountList.add(account);
			
			userNameSb.append(AccountType.ZUOSANAME);
			userNameSb.append("-");
			userNameSb.append(account.getUserName());
			userNameSb.append(",");
		}
		
		String checkResult = checkAccountLogin(accountList);   //验证账户
		writeLog(userNameSb.toString(), checkResult, request);
		request.getSession().setAttribute("accountList", accountList);
		request.getSession().setAttribute("accountCheckResult", checkResult);
		writeCookies(accountList, response);
		
		if(checkResult == null)
		{
			response.sendRedirect("main.jsp");
		}
		else
		{
			response.sendRedirect("account.jsp");
		}
	}
	
	private String checkAccountLogin(List<Account> accountList)
	{
		StringBuffer sb = new StringBuffer();
		boolean fail = false;
		String result = null;
		for(Account account : accountList)
		{
			switch(account.getAccountType())
			{
				case AccountType.TWITTER:
					if(!new TwitterAPI().loginTwitter
							(account.getUserName(), account.getUserPwd()))
					{
						fail = true;
						sb.append(AccountType.TWITTERNAME);
						sb.append(",");
						account.setAuth(false);
					}
					else
					{
						account.setAuth(true);
					}
					break;
				case AccountType.SINA:
					if(new SinaHttp().loginSina
							(account.getUserName(), account.getUserPwd()) == null)
					{
						fail = true;
						sb.append(AccountType.SINANAME);
						sb.append(",");
						account.setAuth(false);
					}
					else
					{
						account.setAuth(true);
					}
					break;
				case AccountType.RENREN:
					if(new RenrenHttp().loginRenren
							(account.getUserName(), account.getUserPwd()) == null)
					{
						fail = true;
						sb.append(AccountType.RENRENNAME);
						sb.append(",");
						account.setAuth(false);
					}
					else
					{
						account.setAuth(true);
					}
					break;
				case AccountType.DIGU:
					if(!new DiguAPI().loginDigu
							(account.getUserName(), account.getUserPwd()))
					{
						fail = true;
						sb.append(AccountType.DIGUNAME);
						sb.append(",");
						account.setAuth(false);
					}
					else
					{
						account.setAuth(true);
					}
					break;
				case AccountType.ZUOSA:
					if(!new ZuosaAPI().loginZuosa
							(account.getUserName(), account.getUserPwd()))
					{
						fail = true;
						sb.append(AccountType.ZUOSANAME);
						sb.append(",");
						account.setAuth(false);
					}
					else
					{
						account.setAuth(true);
					}
					break;
			}
		}
		if(fail)
		{
			result = sb.toString();
			result = result.substring(0, result.length() - 1);
			result += "身份验证失败！";
		}
		return result;
	}
	
	private void writeCookies(List<Account> accountList, HttpServletResponse response)
	{
		try
		{
			String accountListCode = JsonSerializer.serialize(accountList);
			Cookie cookie = null;
			cookie = new Cookie("accountList",URLEncoder.encode(accountListCode, "utf-8"));
			cookie.setMaxAge(315360000);
			response.addCookie(cookie);
		}
		catch(Exception e){}
	}
	
	private void writeLog(String userName, String result, HttpServletRequest request)
	{
		if(result == null)
			result = "验证成功！";
		Logger log = Logger.getLogger(this.getClass().getName());
		log.info("SaveAccount:" + new Date() + "," + userName + "@" + result + 
			". From " + request.getRemoteAddr() + ":" + request.getHeader("User-Agent"));
	}
}
