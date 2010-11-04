<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>修改图书信息</title>
	</head>

	<body>
		<s:form action="/sbook/updateSBook.action">
			<s:hidden name="sbook.id">${id}</s:hidden>
			<s:textfield name="sbook.title" label="书名" readonly="true">${title}</s:textfield>
			<s:textfield name="sbook.author" label="作者">${author}</s:textfield>
			<s:textfield name="sbook.price" label="价格">${price}</s:textfield>
			<s:textfield name="sbook.total" label="总量">${total}</s:textfield>
			<s:textfield name="sbook.isbn" label="ISBN">${isbn}</s:textfield>
			<s:textfield name="sbook.publisher" label="出版社">${publisher}</s:textfield>
			<s:submit/>
		</s:form>
		<s:property value="tips" />
	</body>
</html>
