# 展示链接：
# 1.[MVC教程例子实现链接](http://shaohjz.site:8080/JAVAWEB_LEARN/)
# 2.
# 第一周——第六周，javaweb 基础学习，servlet 的学习
### 

# 第七周


实现了数据的增删查，修改部分还没看，下次会跟上进度。
按照教程做的发现中文编码有问题，输入中文 name、address 会变成“？”，问号，修改了 jspcharset 以及 pageencoding 属性和 tomcat 中的server.xml配置文件，没有奏效。。。还在尝试中。。。。。。
对这个项目里的，jdbc、dao 部分写的时候，不懂，
    private Class<T> clazz;
    public DAO(){
    	//讲反射的视频？？
        //Type通过Ctrl+Shift+O进行反射Type选择
        Type superClass=getClass().getGenericSuperclass();
        
        if(superClass instanceof ParameterizedType){
            ParameterizedType parameterizedType=(ParameterizedType)superClass;
            
            Type[] typeArgs=parameterizedType.getActualTypeArguments();
            if(typeArgs!=null && typeArgs.length>0){
                if(typeArgs[0] instanceof Class)    clazz=(Class<T>)typeArgs[0];
            }
        }
    }  
 **_这个 class T clazz、和
反射是在干什么_** 希望老师能讲一下，以及花几分钟大概讲解一些 jdbc、dao 
# 第八周
继续学习完成上周没完成的 MVC部分
1. 删除操作
超链接：delete.do?id=<%=customer.getId()%>
Servlet 的 delete 方法
获取 id
调用 DAO 执行删除
重定向到 query.do（若目标页面不需要读取当前请求的 request 属性，就可以使用重定向），将显示删除后的 Customer 的 List
2. JSP 上的 jQuery 提示：
确定要删除 xx 的信息吗？

新建 scrips 文件夹导入jquery-1.7.2.js 文件
jsp 文件中 jquery 的写法：
```
<script type="text/javascript" src="scripts/jquery-1.7.2.js"></script>
<script type="text/javascript">
	
	$(function(){
		$(".delete").click(function(){
			var content = $(this).parent().parent().find("td:eq(1)").text();
			var flag = confirm("确定要是删除" + content + "的信息吗?");
			return flag;
		});
	});
</script>
```
1.MVC整体的架构：
![MVC整体的架构](http://p7mezsuru.bkt.clouddn.com/15244576395628.jpg "在这里输入图片标题")

2.多个请求对应一个 servlet：
![MVC 案例小结1](http://p7mezsuru.bkt.clouddn.com/15244565673805.jpg "在这里输入图片标题")
3.查询：MVC 的整个的流程
query.do-->doPost-->query-->JSP
query方法的代码：
```
//1. 调用 CustomerDAO 的 getForListWithCriteriaCustomer() 得到 Customer 的集合
		List<Customer> customers = customerDAO.getForListWithCriteriaCustomer(cc);
		
		//2. 把 Customer 的集合放入 request 中
		request.setAttribute("customers", customers);
		
		//3. 转发页面到 index.jsp(不能使用重定向)
		request.getRequestDispatcher("/index.jsp").forward(request, response);
```
JSP：获取 request 中的 customers 属性，遍历显示
```
<%
          List<Customer> customers=(List<Customer>)request.getAttribute("customers");
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
```
     
4.模糊查询
1）.SQL语句：CustomerDAOJdbcimpl.java 文件的List<Customer> getForListWithCriteriaCustomer(CriteriaCustomer cc)方法
		
String sql = "SELECT id, name, address, phone FROM customers WHERE " +
				"name LIKE ? AND address LIKE ? AND phone LIKE ?";
2）.填充占位符的技巧：
```
	public String getName() {
		if(name == null)
			name = "%%";
		else
			name = "%" + name + "%";
		return name;
	}

```
3）.把查阅条件封装为一个 JavaBean
```
public class CriteriaCustomer {

	private String name;
	
	private String address;
	
	private String phone;
        //...

}

```
4）.

 **# 第九周** 

## 整理加深理解mvc的案例，学习cookie部分。
http是无状态协议，所以购物车类的应用，需要一种机制记录用BS的响应。
Servlet两种机制：
1. Cookie
2. Session


### Cookie机制：浏览器访问服务器时，web服务器再http响应头中传送给浏览器的文本文件，浏览器保存后，以后每次访问该浏览器都会传给WEB服务器。
jsp中Cookie的创建：
    Cookie cookie =new Cookie（“name”，“cookie的name”）；
服务器调用response的addCookie传给客户端的浏览器：
response.addCookie(cookie);
*首次访问，浏览器没有存储该地址的Cookie
*

获取Cookiename和value（cookie只有name和value），有getName和getValue

 **获取cookie：** 
request.getCookies（）

Cookie的发送，放入http响应报文，默认用户退出后被删除。

像存储于磁盘上的Cookie，是需要设置MaxAge，要用setMaxAge（）方法，数值单位是秒：0表示命令浏览器删除内存中的Coookie，负数是永远不会存储Coookie
就是Coookie的持久化

