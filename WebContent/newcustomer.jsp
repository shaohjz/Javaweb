<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
    <%
        Object mes=request.getAttribute("message");
        if(mes!=null){
           out.print("<br>");
           out.print(mes);
           out.print("<br>");
           out.print("<br>");
        }
     %>
    <h1>添加一条新的customer信息</h1>
     <!--此处add.do依赖于CustomerServlet中的add方法名  -->
     <form action="add.do">
         <table>
             <tr>
               <td>CustomerName:</td>
               <td><input type="text" name="name"
                    value="<%=request.getParameter("name")==null?"":request.getParameter("name")%>"/></td>
             </tr>
             
              <tr>
               <td>CustomerAddress:</td>
               <td><input type="text" name="address"
                   value="<%=request.getParameter("address")==null?"":request.getParameter("address")%>"/></td>
             </tr>
             
              <tr>
               <td>CustomerPhone:</td>
               <td><input type="text" name="phone"
                    value="<%=request.getParameter("phone")==null?"":request.getParameter("phone")%>"/></td>
             </tr>
             
              <tr>
               <td colspan="2"><input type="submit" value="Submit"/></td>
             </tr>
         </table>
      </form>
</body>
</html>