<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!-- 导入标签库(描述文件) -->    
<%@taglib uri="http://www.atguigu.com/mytag/core" prefix="atguigu" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	
	<atguigu:testJspFragment>helloworld</atguigu:testJspFragment>
	<br>
	<atguigu:printUpper time="10">hello-world</atguigu:printUpper>
		<!-- 父标签打印name到控制台.  -->
	<atguigu:parentTag>
		<!-- 子标签以父标签的标签体存在,  子标签把父标签的name属性打印到 JSP 页面上.  -->
		<atguigu:sonTag/>
	</atguigu:parentTag>
	<br>
	判断标准
		<br>
		age>24 --- 大学毕业
		<br>
		age>20 --- 高中毕业
		<br>
		 其他   ---高中以下
			 <form action="test2.jsp" method="get"> 
       age: <input type="text" name="age"/>
			<input type="submit" value="Submit"/>
		</form> 
		
		c标签：<br>
		<c:choose>
		<c:when test="${param.age > 24}">大学毕业</c:when>
		<c:when test="${param.age > 20}">高中毕业</c:when>
		<c:otherwise>高中以下...</c:otherwise>
	</c:choose>
	<br>自定义标签，父标签：<br>
	<atguigu:choose>
		<atguigu:when test="${param.age > 24}">^大学毕业</atguigu:when>
		<atguigu:when test="${param.age > 20}">^高中毕业</atguigu:when>
		<atguigu:otherwise>^高中以下...</atguigu:otherwise>
	</atguigu:choose>
	<br><br>
</body>
</html>