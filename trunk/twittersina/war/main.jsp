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
        .mainFrame{width:450px; height:auto; margin:80px 0px 0px 0px; padding:10px; border:solid 1px #78a3d3; background-color:#f5f8fc;}
        .mainLine{width:450px; height:1px; background-color:#9c9c9c; font-size:1px; margin-top:3px;}
        .userName{color:#5FA207; font-weight:bold;}
        .checkLbl{font-size:14px; margin-left:15px}
        .explain{font-size:12px; color:#6c6c6c;}
        .explain a{color:#5FA207; text-decoration:none;}
        .explain a:hover{color:#5FA207; text-decoration:underline;}
    </style>
</head>
<body>
<%
	HttpHelp.checkLogin(request);
	Object accountListObj = request.getSession().getAttribute("accountList");
	Object publishResultObj = request.getSession().getAttribute("publishResult");
	request.getSession().setAttribute("publishResult", null);
	List<Account> accountList = null;
	String publishResult = "";
	String publishContent = "";
	Account twitterAccount = null;
	Account sinaAccount = null;
	Account renrenAccount = null;
	Account diguAccount = null;
	Account zuosaAccount = null;
	boolean isfail = false;
	if(accountListObj != null)
	{
		accountList = (List<Account>)accountListObj;
		for(Account account : accountList)
		{
			switch(account.getAccountType())
			{
				case AccountType.TWITTER:
					twitterAccount = account;
					break;
				case AccountType.SINA:
					sinaAccount = account;
					break;
				case AccountType.RENREN:
					renrenAccount = account;
					break;
				case AccountType.DIGU:
					diguAccount = account;
					break;
				case AccountType.ZUOSA:
					zuosaAccount = account;
					break;
			}
		}
	}
	if(publishResultObj != null)
	{
		String[] tmp = String.valueOf(publishResultObj).split("\\|");
		if(tmp != null && tmp.length == 2)
		{
			publishResult = tmp[0];
			if(publishResult.indexOf("成功") == -1)
			{
				isfail = true;
				publishContent = tmp[1];
			}
		}
	}
%>
<form action="/publish" method="post" onsubmit="return checkPublish();">
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
        <table width="100%" cellpadding="0" cellspacing="5" border="0" style="margin-top:5px;">
            <tr>
                <td align="left">
                    <span style="margin-right:5px;">Welcome!</span>
                    <a href="account.jsp" style="font-size:14px; font-weight:bold; color:#78a3d3; ">管理同步源</a>                    
                    <div style="margin-top:3px;">
                    <%
                    if(twitterAccount != null && twitterAccount.isAuth())
                    {
                    %>
	                    <span class="userName"><%=twitterAccount.getUserName() %></span>
	                    <span>(Twitter)</span>&nbsp;
                    <%
                    }
                    if(sinaAccount != null && sinaAccount.isAuth())
                    {
                    %>
	                    <span class="userName"><%=sinaAccount.getUserName() %></span>
	                    <span>(新浪)</span>&nbsp;
                    <%
                    }
                    if(renrenAccount != null && renrenAccount.isAuth())
                    {
                    %>
	                    <span class="userName"><%=renrenAccount.getUserName() %></span>
	                    <span>(人人网)</span>&nbsp;
                    <%
                    }
                    if(diguAccount != null && diguAccount.isAuth())
                    {
                    %>
	                    <span class="userName"><%=diguAccount.getUserName() %></span>
	                    <span>(嘀咕)</span>&nbsp;
                    <%
                    }
                    if(zuosaAccount != null && zuosaAccount.isAuth())
                    {
                    %>
	                    <span class="userName"><%=zuosaAccount.getUserName() %></span>
	                    <span>(做啥)</span>&nbsp;
                    <%
                    }
                    %>
                    </div>
                </td>
            </tr>
            <tr>
                <td>
                    <textarea name="contentTxt" id="contentTxt" onkeydown="document.getElementById('noticeLbl').innerHTML=''" style="height:70px;width:99%;"><%=publishContent %></textarea>
                </td>
            </tr>
            <tr valign="middle">
                <td align="left" valign="middle" width="320px">
                    <span id="contentLength">140</span>
                    <%
                    if(twitterAccount != null && twitterAccount.isAuth())
                    {
                    %>
                    	<span class="checkLbl"><input type="checkbox" id="updateTwitter" name="updateTwitter" checked="checked" /><label for="updateTwitter">Twitter</label></span>
                   	<%
                    }
                   	if(sinaAccount != null && sinaAccount.isAuth())
                   	{
                   	%>
                    	<span class="checkLbl"><input type="checkbox" id="updateSina" name="updateSina" checked="checked" /><label for="updateSina">围脖</label></span>
                    <%
                   	}
                   	if(renrenAccount != null && renrenAccount.isAuth())
                   	{
                   	%>
                    	<span class="checkLbl"><input type="checkbox" id="updateRenren" name="updateRenren" checked="checked" /><label for="updateRenren">人人网</label></span>
                    <%
                   	}
                   	if(diguAccount != null && diguAccount.isAuth())
                   	{
                   	%>
                    	<span class="checkLbl"><input type="checkbox" id="updateDigu" name="updateDigu" checked="checked" /><label for="updateDigu">嘀咕</label></span>
                   	<%
                   	}
                   	if(zuosaAccount != null && zuosaAccount.isAuth())
                   	{
                   	%>
                    	<span class="checkLbl"><input type="checkbox" id="updateZuosa" name="updateZuosa" checked="checked" /><label for="updateZuosa">做啥</label></span>
                	<%
                	}
                	%>
                </td>
            </tr>
            <tr>
                <td align="center" height="30px">
                	<%
                	if(accountList != null && accountList.size() > 0)
                	{
                	%>
                    	<input type="submit" id="publishBt" name="publishBt" value="发   布" style="font-weight:bold;" />
                    <%
                	}
                	else
                	{
                    %>
                    	<input type="submit" id="publishBt" name="publishBt" value="发   布" style="font-weight:bold;" disabled="disabled" />
                    <%
                	}
                    %>
                </td>
            </tr>
            <tr>
                <td align="center">
                	<%
                	if(isfail)
                	{
                	%>
	                	<span id="noticeLbl" style="color:red;">
                	<%
	                }
	                else
	                {
                	%>
	                	<span id="noticeLbl">
                	<%	
	                }
           			out.println(publishResult);
            		%>
	                </span>
                </td>
            </tr>
        </table>
        <div class="explain">
            <p>说明：1、本工具提供同步更新<a href="http://twitter.com" target="_blank">Twitter</a>、
            	<a href="http://t.sina.com.cn" target="_blank">新浪微博</a>、
            	<a href="http://www.renren.com" target="_blank">人人网</a>、
            	<a href="http://digu.com" target="_blank">嘀咕</a>、
            	<a href="http://www.zuosa.com" target="_blank">做啥</a>的功
           	</p>
            <p style="margin-left:54px;">能，您可以在<a href="account.jsp">管理同步源</a>中设置同步账号。</p>
            <p style="margin-left:36px;">2、对您提供的账号和密码，本工具不会做任何记录。</p>
            <p style="margin-left:36px;">3、本工具目前为2.0 Beta版，不稳定之处在所难免，欢迎各位提出宝贵意见。</p>
            <p style="margin-left:36px;">4、作者Twitter账号：<a id="HyperLink1" href="http://twitter.com/StevenWang87" target="_blank">StevenWang87</a>，围脖账号：<a id="HyperLink2" href="http://t.sina.com.cn/StevenWang" target="_blank">StevenWang</a>。</p>
        </div>
    </div>
    <div style="width:100%; text-align:center; margin-top:20px;">
     	<a href="http://twitterSina.googlecode.com" target="_blank" style="font-size:14px; font-weight:bold; color:#78a3d3; ">查看源代码</a>
	    <a href="https://groups.google.com/group/twittersina?hl=zh-CN" target="_blank" style="font-size:14px; font-weight:bold; color:#78a3d3; ">进入用户Group</a>
     	<div style="margin-top:5px;">
     		<img src="http://code.google.com/intl/zh-CN/appengine/images/appengine-silver-120x30.gif" alt="由 Google App Engine 提供支持" />
     	</div>
     </div>
    </form>
    <script type="text/javascript" charset="utf-8">
        function updateContentLength() {
            document.getElementById('contentLength').innerHTML = 140 - document.getElementById('contentTxt').value.length;
            setTimeout(updateContentLength, 200);
        }

        updateContentLength();
        
        function checkPublish() {
            if (document.getElementById('contentTxt').value.length == 0) {
                alert('您发布的内容不能为空!');
                return false;
            }
            if (document.getElementById('contentTxt').value.length > 140) {
                alert('您发布的内容超过了140个字!');
                return false;
            }
            var selected = false;
            var objs = document.getElementsByTagName('input');
            for(var i=0;i < objs.length; i++) 
            {
                if(objs[i].type.toLowerCase() == "checkbox" && objs[i].checked)
                {
                	selected = true;
                }
            }
            if(!selected)
            {
            	alert('您必须选择一个地方发布!');
                return false;
            }
            document.getElementById('publishBt').disabled = true;
            return true;
        }
    </script>
    
</form>
</body>