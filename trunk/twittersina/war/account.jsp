<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" language="java" %>
<%@page import="java.util.*" %>
<%@page import="twitterSina.*" %>
<%@page import="twitterSina.common.*" %>
<%request.setCharacterEncoding("utf-8"); %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	
	<title>围着脖子推</title>
	<style type="text/css">
        body {margin:0 auto; padding:0 auto; width:460px;}
        table{margin-top:5px;}
        .mainFrame{width:450px; height:auto; margin:80px 0px 0px 0px; padding:10px; border:solid 1px #78a3d3; background-color:#f5f8fc;}
        .mainLine{width:450px; height:1px; background-color:#9c9c9c; font-size:1px; margin-top:3px;}
        .accountLine{width:450px; height:1px; background-color:#9c9c9c; font-size:1px; margin-top:3px;}
        .textBox{width:150px; border:1px solid #7f9db9;}
        .notice{width:100%; text-align:center; font-size:12px; color:#6c6c6c; margin-top:10px;}
    </style>
</head>
<body>
<%
	HttpHelp.checkLogin(request);
	Object accountListObj = request.getSession().getAttribute("accountList");
	Object checkResultObj = request.getSession().getAttribute("accountCheckResult");
	request.getSession().setAttribute("accountCheckResult", null);
	List<Account> accountList = null;
	String checkResult = "";
	String twitterUserName = "";
	String twitterUserPwd = "";
	String sinaUserName = "";
	String sinaUserPwd = "";
	String renrenUserName = "";
	String renrenUserPwd = "";
	String diguUserName = "";
	String diguUserPwd = "";
	String zuosaUserName = "";
	String zuosaUserPwd = "";
	if(accountListObj != null)
	{
		accountList = (List<Account>)accountListObj;
		for(Account account : accountList)
		{
			switch(account.getAccountType())
			{
				case AccountType.TWITTER:
					twitterUserName = account.getUserName();
					twitterUserPwd = account.getUserPwd();
					break;
				case AccountType.SINA:
					sinaUserName = account.getUserName();
					sinaUserPwd = account.getUserPwd();
					break;
				case AccountType.RENREN:
					renrenUserName = account.getUserName();
					renrenUserPwd = account.getUserPwd();
					break;
				case AccountType.DIGU:
					diguUserName = account.getUserName();
					diguUserPwd = account.getUserPwd();
					break;
				case AccountType.ZUOSA:
					zuosaUserName = account.getUserName();
					zuosaUserPwd = account.getUserPwd();
					break;
			}
		}
	}
	if(checkResultObj != null)
	{
		checkResult = String.valueOf(checkResultObj);
	}
%>
<form action="/account" method="post" onsubmit="document.getElementById('saveBt').disabled = true;">
	<div class="mainFrame">
        <div style="font-size:14px; font-weight:bold; color:#000;">
			<span>
			 	围着脖子推 V2.0 Beta1
			</span>
			<span style="font-size:12px; font-weight:normal; color:#6c6c6c; display:inline; margin-left:120px;">
				最近更新：2009-12-2 00:00
		    </span>
		</div>
		<div class="mainLine"></div>
		<div class="notice">
            	您可以从以下同步源中任意选填几个来同步您的消息。
        </div>
        <table width="100%" cellpadding="0" cellspacing="5" border="0">
            <tr>
                <td width="180px" align="right">
                    Twitter账号：
                </td>
                <td>
                	<input type="text" id="twitterUserNameTxt" name="twitterUserNameTxt" class="textBox" value="<%=twitterUserName %>"/>
                </td>
            </tr>
            <tr>
                <td width="180px" align="right">
                    Twitter密码：
                </td>
                <td>
                	<input type="password" id="twitterUserPwdTxt" name="twitterUserPwdTxt" class="textBox" value="<%=twitterUserPwd %>"/>
                </td>
            </tr
		</table>
		<table width="100%" cellpadding="0" cellspacing="5" border="0">
            <tr>
                <td width="180px" align="right">
                 	新浪围脖账号：
                </td>
                <td>
             		<input type="text" id="sinaUserNameTxt" name="sinaUserNameTxt" class="textBox" value="<%=sinaUserName %>"/>
                </td>
            </tr>
            <tr>
                <td width="180px" align="right">
                	新浪围脖密码：
                </td>
                <td>
             		<input type="password" id="sinaUserPwdTxt" name="sinaUserPwdTxt" class="textBox" value="<%=sinaUserPwd %>"/>
                </td>
            </tr>
         </table>
         <table width="100%" cellpadding="0" cellspacing="5" border="0">
			<tr>
			    <td width="180px" align="right">
			     	人人网账号：
			    </td>
			    <td>
			 		<input type="text" id="renrenUserNameTxt" name="renrenUserNameTxt" class="textBox" value="<%=renrenUserName %>"/>
			    </td>
			</tr>
			<tr>
			    <td width="180px" align="right">
			    	人人网密码：
			    </td>
			    <td>
			 		<input type="password" id="renrenUserPwdTxt" name="renrenUserPwdTxt" class="textBox" value="<%=renrenUserPwd %>"/>
			    </td>
			</tr>
       	</table>
       	<table width="100%" cellpadding="0" cellspacing="5" border="0">
            <tr>
                <td width="180px" align="right">
                 	嘀咕账号：
                </td>
                <td>
             		<input type="text" id="diguUserNameTxt" name="diguUserNameTxt" class="textBox" value="<%=diguUserName %>"/>
                </td>
            </tr>
            <tr>
                <td width="180px" align="right">
                	嘀咕密码：
                </td>
                <td>
             		<input type="password" id="diguUserPwdTxt" name="diguUserPwdTxt" class="textBox" value="<%=diguUserPwd %>"/>
                </td>
            </tr>
          </table>
          <table width="100%" cellpadding="0" cellspacing="5" border="0">
            <tr>
                <td width="180px" align="right">
                 	做啥账号：
                </td>
                <td>
             		<input type="text" id="zuosaUserNameTxt" name="zuosaUserNameTxt" class="textBox" value="<%=zuosaUserName %>"/>
                </td>
            </tr>
            <tr>
                <td width="180px" align="right">
                	做啥密码：
                </td>
                <td>
             		<input type="password" id="zuosaUserPwdTxt" name="zuosaUserPwdTxt" class="textBox" value="<%=zuosaUserPwd %>"/>
                </td>
            </tr>
            <tr>
                <td colspan="2" align="center" height="30px">
                	<input type="submit" value="保   存" style="font-weight:bold;" id="saveBt" />&nbsp;&nbsp;
                	<input type="button" value="返   回" onclick="window.location.href='/'" />
                </td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <span style="color:red;">
                    	<%
               				out.println(checkResult);
               			%>
                    </span>
                </td>
            </tr>
        </table>
    </div>
    <div style="width:100%; text-align:center; margin-top:20px;">
     	<a href="http://twitterSina.googlecode.com" target="_blank" style="font-size:14px; font-weight:bold; color:#78a3d3; ">查看源代码</a>
	    <a href="http://groups.google.com/group/twittersina?hl=zh-CN" target="_blank" style="font-size:14px; font-weight:bold; color:#78a3d3; ">进入用户Group</a>
     	<div style="margin-top:5px;">
     		<img src="http://code.google.com/intl/zh-CN/appengine/images/appengine-silver-120x30.gif" alt="由 Google App Engine 提供支持" />
     	</div>
     </div>
    </form>    
</form>
</body>