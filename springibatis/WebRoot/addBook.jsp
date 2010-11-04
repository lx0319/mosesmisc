<%@ page language="java"  pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>添加图书</title>
  </head>
  
  <body>
  	<s:property value="tips"/>
    <s:form action="/sbook/addSBook.action" >
    	<s:textfield name="sbook.title" label="书名"/>
    	<s:textfield name="sbook.author" label="作者"/>
    	<s:textfield name="sbook.price" label="价格"/>
    	<s:textfield name="sbook.total" label="数量"/>
    	<s:textfield name="sbook.isbn" label="ISBN号"/>
    	<s:textfield name="sbook.publisher" label="出版社"/>
    	<s:submit value="添加"/>
    </s:form>
    <a href="<%=request.getContextPath() %>/sbook/viewSBook.action">查看现有图书</a>
  </body>
</html>
