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
import java.util.*; 
import java.util.logging.Logger;
import javax.servlet.*; 
import javax.servlet.http.*; 

import net.liuxuan.twitter.sycn.RenrenHcHttp;
import twitterSina.*;
import twitterSina.common.*;

/**
 * 发布信息相关的Servlet
 * @author Steven Wang <http://steven-wang.appspot.com>
 */
public class PublishServlet extends HttpServlet 
{
	private static final long serialVersionUID = -8334749827752190120L;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException
	{
		doPost(request, response);
	}

	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException
	{
		if(!HttpHelp.checkLogin(request))
		{
			response.sendRedirect("/");
			return;
		}
		
		//获取当前用户的同步源账号
		Object accountListObj = request.getSession().getAttribute("accountList");
		if(accountListObj == null)
		{
			response.sendRedirect("/");
			return;
		}
		List<Account> accountList = null;
		Account twitterAccount = null;
		Account sinaAccount = null;
		Account renrenAccount = null;
		Account diguAccount = null;
		Account zuosaAccount = null;
		try
		{
			accountList = (List<Account>)accountListObj;
		}
		catch(Exception ex)
		{
			response.sendRedirect("/");
			return;
		}
		boolean flag = false;
		for(Account account : accountList)
		{
			switch(account.getAccountType())
			{
				case AccountType.TWITTER:
					if(account.isAuth())
					{
						twitterAccount = account;
						flag = true;
					}
					break;
				case AccountType.SINA:
					if(account.isAuth())
					{
						sinaAccount = account;
						flag = true;
					}
					break;
				case AccountType.RENREN:
					if(account.isAuth())
					{
						renrenAccount = account;
						flag = true;
					}
					break;
				case AccountType.DIGU:
					if(account.isAuth())
					{
						diguAccount = account;
						flag = true;
					}
					break;
				case AccountType.ZUOSA:
					if(account.isAuth())
					{
						zuosaAccount = account;
						flag = true;
					}
					break;
			}
		}
		if(!flag)
		{
			response.sendRedirect("/");
			return;
		}
		//很据用户所勾选的同步源，发布消息
		String updateTwitter = request.getParameter("updateTwitter");
		String updateSina = request.getParameter("updateSina");
		String updateRenren = request.getParameter("updateRenren");
		String updateDigu = request.getParameter("updateDigu");
		String updateZuosa = request.getParameter("updateZuosa");
		String publishContent = request.getParameter("contentTxt");
		if(publishContent == null || publishContent.length() == 0)
		{
			response.sendRedirect("/");
			return;
		}
		publishContent = HttpHelp.processContent(publishContent);
		
		StringBuffer sb = new StringBuffer();
		StringBuffer userNameSb = new StringBuffer();   //for write log
		boolean fail = false;
		String publishResult = "";
		if(twitterAccount != null && updateTwitter != null && updateTwitter.toLowerCase().equals("on"))
		{
			TwitterAPI twitter = new TwitterAPI();
			if (!twitter.publishTwitter(twitterAccount.getUserName(), 
					twitterAccount.getUserPwd(), publishContent))
			{
				fail = true;
				sb.append(AccountType.TWITTERNAME);
				sb.append(",");
			}
			userNameSb.append(AccountType.TWITTERNAME);
			userNameSb.append("-");
			userNameSb.append(twitterAccount.getUserName());
			userNameSb.append(",");
		}
		if(sinaAccount != null && updateSina != null && updateSina.toLowerCase().equals("on"))
		{
			SinaHttp sina = new SinaHttp();
			String sinaCookie = sina.loginSina(sinaAccount.getUserName(), 
					sinaAccount.getUserPwd());
			if (sinaCookie == null || !sina.publishSina(sinaCookie, publishContent))
			{
				fail = true;
				sb.append(AccountType.SINANAME);
				sb.append(",");
			}
			userNameSb.append(AccountType.SINANAME);
			userNameSb.append("-");
			userNameSb.append(sinaAccount.getUserName());
			userNameSb.append(",");
		}
		if(renrenAccount != null && updateRenren != null && updateRenren.toLowerCase().equals("on"))
		{
			
			RenrenHcHttp rr = new RenrenHcHttp();
			rr.login(renrenAccount.getUserName(),renrenAccount.getUserPwd());
			String str_res = rr.publish(publishContent);
//			System.out.println(str_res);
			
			if (!str_res.startsWith("{\"allMsg\""))
			{
				fail = true;
				sb.append(AccountType.RENRENNAME);
				sb.append(",");
			}
			
			rr.getHcd().shutdownHC();
			rr.setHcd(null);
			
//			RenrenHttp renren = new RenrenHttp();
//			String renrenCookie = renren.loginRenren(renrenAccount.getUserName(), 
//					renrenAccount.getUserPwd());
//			if (renrenCookie == null || !renren.publishRenren(renrenCookie, publishContent))
//			{
//				fail = true;
//				sb.append(AccountType.RENRENNAME);
//				sb.append(",");
//			}
			userNameSb.append(AccountType.RENRENNAME);
			userNameSb.append("-");
			userNameSb.append(renrenAccount.getUserName());
			userNameSb.append(",");
		}
		if(diguAccount != null && updateDigu != null && updateDigu.toLowerCase().equals("on"))
		{
			DiguAPI digu = new DiguAPI();
			if (!digu.publishDigu(diguAccount.getUserName(), 
					diguAccount.getUserPwd(), publishContent))
			{
				fail = true;
				sb.append(AccountType.DIGUNAME);
				sb.append(",");
			}
			userNameSb.append(AccountType.DIGUNAME);
			userNameSb.append("-");
			userNameSb.append(diguAccount.getUserName());
			userNameSb.append(",");
		}
		if(zuosaAccount != null && updateZuosa != null && updateZuosa.toLowerCase().equals("on"))
		{
			ZuosaAPI zuosa = new ZuosaAPI();
			if (!zuosa.publishZuosa(zuosaAccount.getUserName(), 
					zuosaAccount.getUserPwd(), publishContent))
			{
				fail = true;
				sb.append(AccountType.ZUOSANAME);
				sb.append(",");
			}
			userNameSb.append(AccountType.ZUOSANAME);
			userNameSb.append("-");
			userNameSb.append(zuosaAccount.getUserName());
			userNameSb.append(",");
		}
		if(fail)
		{
			publishResult = sb.toString();
			publishResult = publishResult.substring(0, publishResult.length() - 1);
			publishResult += "发布失败！";
		}
		else
		{
			publishResult += "发布成功！";
		}
		writeLog(userNameSb.toString(), publishContent, publishResult, request);
		request.getSession().setAttribute("publishResult", publishResult + "|" + publishContent);
		response.sendRedirect("/");
	}


	private void writeLog(String userName, String content, 
			String result, HttpServletRequest request)
	{
		Logger log = Logger.getLogger(this.getClass().getName());
		log.info("Push:" + new Date() + "," + userName + "publish:" +
    			content + ",@" + result + ". From " + request.getRemoteAddr() + ":" + request.getHeader("User-Agent"));
	}
}
