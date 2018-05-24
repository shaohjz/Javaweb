<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!-- 导入标签库(描述文件) -->    
<%@taglib uri="http://www.atguigu.com/mytag/core" prefix="atguigu" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>easytag练习一</h1>

	<%-- <atguigu:max num2="10" num1="11"/> --%>
	 <atguigu:max num2="${param.a }" num1="${param.b }"/>
	<br>  
		 <form action="test.jsp" method="get"> 
       num1: <input type="text" name="a"/>
		num2: <input type="text" name="b"/>
			<input type="submit" value="Submit"/>
		</form> 
	<%-- <atguigu:hello value="atguigu" count="10"/> --%>
		<h1>easytag练习二</h1>
	
	<atguigu:readerFile src="/WEB-INF/note.txt"/>
	
</body>
</html>