<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>list of all tasks</title>
	</head>
	<body>
		<h1>List of all tasks.</h1>
		<table>
			<thead>
				<th>name</th>
				<th>description</th>
				<th>deadline</th>
				<th>complete</th>
				<th>del</th>
			</thead>
			<tbody>
				<s:iterator value="tasks">
					<tr>
						<s:if test="complete == true" >
							<td><del><s:property value="name" /></del></td>
							<td><del><s:property value="description" /></del></td>
							<td><del><s:property value="deadline" /></del></td>
						</s:if>
						<s:else>
							<td><s:property value="name" /></td>
							<td><s:property value="description" /></td>
							<td><s:property value="deadline" /></td>
						</s:else>
						<td>
							<s:url action="complete" var="compUrl">
								<s:param name="taskId" value="id" />
							</s:url>
							<a href='<s:property value="compUrl" />'>Complete</a>
						</td>
						<td>
							<s:url action="delete" var="delUrl">
								<s:param name="taskId" value="id" />
							</s:url>
							<a href='<s:property value="delUrl" />'>Delete</a>
						</td>
					</tr>
				</s:iterator>
			</tbody>
		</table>
                <s:url value="add-input.jsp" var="addUrl" />
		<a href='<s:property value="#addUrl" />'>Add New Task</a>
	</body>
</html>
