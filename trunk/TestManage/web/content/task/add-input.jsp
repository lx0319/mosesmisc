<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib  prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Input new task</title>
    </head>
    <body>
        <h1>New task.</h1>
		<s:form action="add" method="post" >
			<s:textfield label="Name" name="task.name" />
			<s:textfield label="Description" name="task.description" />
			<s:textfield label="Deadline(2009/3/1-19:31)" name="task.deadline" />
			<s:submit label="Add" />
		</s:form>
    </body>
</html>
