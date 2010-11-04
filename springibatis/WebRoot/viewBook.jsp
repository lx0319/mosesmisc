<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>浏览图书</title>
	</head>

	<body>
		<table align="center" border="1" style="width:80%;">
			<tr>
				<th colspan="7" align="center">库存图书</th>
			</tr>
			<tr>
				<td align="left" colspan="7"><a href="<%=request.getContextPath()%>/addBook.jsp">添加新书</a></td>
			</tr>
			<tr>
				<td>书名</td>
				<td>作者</td>
				<td>价格</td>
				<td>库存量</td>
				<td>ISBN号</td>
				<td>出版社</td>
				<td>操作</td>
			</tr>
			<s:iterator value="bookList">
				<tr>
					<td>
						<s:property value="title"/>
					</td>
					<td>
						<s:property value="author"/>
					</td>
					<td>
						<s:property value="price"/>
					</td>
					<td>
						<s:property value="total"/>
					</td>
					<td>
						<s:property value="isbn"/>
					</td>
					<td>
						<s:property value="publisher"/>
					</td>
					<td>
						<a href="<%=request.getContextPath()%>/sbook/modifySBook.action?bookId=${id}">修改信息</a>
						<a href="<%=request.getContextPath()%>/sbook/removeSBook.action?bookId=${id}">删除</a>
					</td>
				</tr>
			</s:iterator>
			<s:property value="tips"/>
		</table>
	</body>
</html>
