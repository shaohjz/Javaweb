<%@page import="swu.edu.cn.mvc.Student"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%-- 
	<%=request.getAttribute("students") %>
	<%
		List<String> names = (List)request.getAttribute("students");
		for(String name:names){
	%>
	<br>
	<%= name %>
	<% 
		}
	%>
--%>
	
	<%
	List<Student> stus = (List<Student>)request.getAttribute("students");
	%> 
	<table border="1" cellpadding="10" cellspacing="0">
	<tr>
	<th>FlowID</th>
	<th>Type</th>
	<th>IdCard</th>
	<th>ExamCard</th>
	<th>StudentName</th>
	<th>location</th>
	<th>Grade</th>
	<th>Delete</th><%-- 删除操作 --%>
	</tr>
	<%
	for(Student student : stus){
	%>
	<tr>
	<td><%=student.getFlowID() %></td>
	<td><%=student.getType() %></td>
	<td><%=student.getIdCard() %></td>
	<td><%=student.getExamCard() %></td>
	<td><%=student.getStudentName() %></td>
	<td><%=student.getLocation() %></td>
	<td><%=student.getGrade() %></td>
	
	<td><a href="DeleteStudent?flowID=<%=student.getFlowID() %>">Delete</a></td>
	
	</tr>
	<%
	}
	%>
	</table>
</body>
</html>