<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="net.liuxuan.tuan.site.bean.TuanSiteBean"%>
<%@ page import="net.liuxuan.tuan.site.services.TuanSiteUtils"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>

<html>
<body>
<h2>Entries</h2>



<h2>New entry</h2>
<form action="/addressbook" method="post">
	<p>
	站点名<input name="sitename"><br/> 
	站点地址<input name="siteurl"><br/> 
	团购名（类型<input name="productname_processtype">xpath<input name="productname_xpath">regex<input name="productname_regex">multipler<input name="productname_multipler"><br/> 
	<p><input type="submit" value="Submit"></p>
</form>
<br>


</body>
</html>
