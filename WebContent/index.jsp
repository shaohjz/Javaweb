<%@page import="swu.edu.cn.mvcapp.dao.domain.Customer"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
    
  <script type="text/javascript" src="scripts/jquery.min.js"></script>
  <script type="text/javascript">
     $(function(){
         $('.delete').click(function(){
            var content=$(this).parent().parent().find("td:eq(1)").text();
            var flag=confirm("确定要删除此"+content+"信息？"); 
            return flag; 
         });
     });
  </script>
  </head>
  
  <body>
      <form action="query.do">
         <table>
             <tr> 
               <td>CustomerName:</td>
               <td><input type="text" name="name"/></td>
             </tr>
             
              <tr>
               <td>CustomerAddress:</td>
               <td><input type="text" name="address"/></td>
             </tr>
             
              <tr>
               <td>CustomerPhone:</td>
               <td><input type="text" name="phone"/></td>
             </tr>
             
              <tr>
               <td><input type="submit" value="Query"/></td>
               <td><a href="newcustomer.jsp">Create New Customer</a></td>
             </tr>
         </table>
      </form>
      <br/><br/>
      <%
          List<Customer> customers=(List<Customer>)request.getAttribute("customers");
          if(customers!=null && customers.size()>0){
       %>
       <hr>
       <br/><br/>
       <table border="1" cellpadding="10" cellspacing="0">
            <tr>
               <th>ID</th>
               <th>CustomerName</th>
               <th>CustomerAddress</th>
               <th>CustomerPhone</th>
               <th>Update/Delete</th>
            </tr> 
            <% 
              for(Customer customer:customers){
            %>
            <tr>
               <td><%=customer.getId() %></td>
               <td><%=customer.getName() %></td>
               <td><%=customer.getAddress() %></td>
               <td><%=customer.getPhone() %></td>
               <td>
                   <a  href="edit.do?id=<%=customer.getId() %>">Update</a>
                   <a href="delete.do?id=<%=customer.getId() %>" class="delete">Delete</a>
               </td> 
               </tr>
               <%
                }
                %>
       </table>
       <%
           }
        %>

  </body>
</html>