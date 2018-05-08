<a name="目录"></a>
# 目录


<a href="#w1">第一至六周</a>

<a href="#w7">第七周</a>

<a href="#w8">第八周</a>

<a href="#w9">第九周</a>

<a href="#w10">第十周</a>


-------

<a name="w1"></a>
# 第一周——第六周，javaweb 基础学习，servlet 的学习
<a href="#目录">回到目录</a>

### 
<a name="w7"></a>

-------
# 第七周

<a href="#目录">回到目录</a>

实现了数据的增删查，修改部分还没看，下次会跟上进度。

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
<a name="w8"></a>

-------

# 第八周
<a href="#目录">回到目录</a>

# 展示链接：
# [MVC教程例子实现链接](http://shaohjz.site:8080/JAVAWEB_LEARN/)
<a href="#顶部">回到顶部</a>
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
<a name="w9"></a>

-------

# 第九周

<a href="#目录">回到目录</a>

## 整理加深理解mvc的案例，学习cookie部分。
http是无状态协议，所以购物车类的应用，需要一种机制记录用BS的响应。
Servlet两种机制：
1. Cookie
2. Session


### Cookie机制：浏览器访问服务器时，web服务器再http响应头中传送给浏览器的文本文件，浏览器保存后，以后每次访问该浏览器都会传给WEB服务器。
//jsp中Cookie的创建：
    Cookie cookie =new Cookie（“name”，“cookie的name”）；
服务器调用response的addCookie传给客户端的浏览器：
response.addCookie(cookie);
*首次访问，浏览器没有存储该地址的Cookie
*

获取Cookiename和value（cookie只有name和value），有getName和getValue

 **获取cookie：** 
request.getCookies（）

Cookie的发送，放入http响应报文，默认用户退出后被删除。

![cookie传送过程示意图](http://p7mezsuru.bkt.clouddn.com/2018-05-02-15252312909965.jpg "cookie传送过程示意图")


COOKIE API:

1）向客户端浏览器写入Cookie

// 创建一个cookie对象
Cookie cookie =new Cookie（“name”，“cookie的name”）；

// setMaxAge
像存储于磁盘上的Cookie，是需要设置MaxAge，要用setMaxAge（）方法，数值单位是秒：0表示命令浏览器删除内存中的Coookie，负数是永远不会存储Coookie
就是Coookie的持久化

// 设置Cookie的作用范围：可以作用当前目录和当前目录的子目录，但不能作用于当前目录的上一级目录
cookie.setPath(request.get(ContextPath());

// 调用response的一个方法把Cookie传给客户端
response.addCookie(cookie);

2）从浏览器读取Cookie

	//1. 获取 Cookie
	Cookie [] cookies = request.getCookies();
	if(cookies != null && cookies.length > 0){
		for(Cookie cookie: cookies){
	//2. 获取 Cookie 的 name 和 value
		out.print(cookie.getName() + ": " + cookie.getValue());
		out.print("<br>"); 
		}
3）会话cookie和持久化cookie

   

     1.  //如果不设置过期时间，关闭浏览器cookie就消失了
     2.  //如果设置了过期时间，浏览器会把cookie存在硬盘上
4）应用


    1.   //自动登录
    2.   //显示浏览过的商品信息
		
<a name="w10"></a>

-------

# 第十周 session

<a href="#目录">回到目录</a>

## HTTPSession——在服务器端保持http转改信息的方案。
*  产生httpsession对象的过程：
 
1. 有sessid：浏览器请求——服务器检查sessionid，服务器按照sessionid检索出此session使用（检索不到可能服务器认为此session过期，删除对应的session对象，但用户认为在请求的url后面附加一个jsession的参数）
1. 无sessid：服务器创建session和有sessid并保存，且有sessid返给客户端

* 使用cookie跟踪session

    session默认使用cookie实现，为session cookie，存于浏览器内存中，不写在硬盘
## HTTPSession生命周期
    
#### * 创建

1. server端程序调HttpServletReques.getSession(true)或 HttpServletReques.getSession()
2. 第一次访问某jsp，且page指定的session属性属性为true（默认也为 true）

#### * 销毁


1. ①. 直接调用 HttpSession 的 invalidate()
1. ②. HttpSession 超过过期时间. 
	> 返回最大时效: getMaxInactiveInterval() 单位是秒
	> 设置最大时效: setMaxInactiveInterval(int interval)
	> 可以在 web.xml 文件中配置 Session 的最大时效, 单位是分钟. 	
	<session-config>
        <session-timeout>30</session-timeout>
    </session-config>


1. ③. 卸载当前 WEB 应用. 
注意: 关闭浏览器不会销毁 Session!

#### * URL重写
作用：不支持cookie的浏览器与web服务器保持会话
定义：将会话标识号以参数附加在url地址后为url重写

encodeURL方法
encodeRedirectURL方法
用在超链接ahref、和form表单的action属性


## 绝对路径的问题

写绝对路径肯定没问题，相对路径，尤其转发至sevlet时，若jsp还是相对于该jsp的地址，则路径混乱。


#### 绝对路径的概念：相对于当前web应用的根路径的路径
    相对于项目（当前web应用）名字路径、contextpath（当前web应用的上下文路径）
    
######javaweb中的 ————  “/ “”   ————————代表着什么？
    
    1.  当前web应用的根路径 http://localhost:8080/contextpath/  需要交给Servlet容器处理
            >请求转发时
            request.getRequestDispatcher("/path/b .jsp").forword(request.response)
            >web.xml文件中映射servlet访问路径：
            <Servlet-mapping>
            <servlet-name>TestServlet</servlet-name>
            <url-pattern>/TestServlet</servlet-name>
    2. WEB站点的根路径:http://localhost:8080/   交给浏览器来处理
        >超链接<a href="/xxxxx">
        >表表单中的action：<form action="/login.jsp">
        >做请求重定向的时候：response.sendRedirect("/a.jsp")
    
    
    
#### 表单的重复提交
    
    表单的重复提交会加重服务器的负担、日常使用点多次提交可能结账的时候就有很多，所以要避免这种情况。
    
   如何避免表单的重复提交：
   在表单中做一个标记，提交到Servlet，检查标记是否存在且是否和预定义的标记一致，若一致，则手里请求，并销毁标记，若不一致则直接响应提示信息，重复提交。

   方法：把标记放在session中

    >在原表单页面，生成一个随机值token
    >在原表单页面，把token值放入session属性中
    >在原表单页面，把token值放入到隐藏域中
    
    >在目标的Servlet中：获取session和隐藏域中的token值
    >比较两个值是否一致：
        若一致，受理请求，且把session域中的token属性清除
        若不一致，则直接响应提示页面：“重复提交”
        
            
#### 使用HttpSession实现验证码
    >在原表单页面，生成一个验证码的图片，生成图片的同时，需要把该图片中的字符放入到session中。
    >在原表单页面，生成一个文本域
     
    >在目标的Servlet中：获取session和表单域中的验证码的值
    >比较两个值是否一致：
        若一致，受理请求，且把session域中的验证码属性清除
        若不一致，则直接通过重定向的方式返回原表单页面，并提示用户“验证码错误 ”
练习连接：
#### [第十周简单购物车练习](http://shaohjz.site:8080/day_35/shoppingcart/step-1.jsp)
#### [第十周验证码练习](http://shaohjz.site:8080/day_35/check/index.jsp)
心得：
没什么问题，就是大概知道了session的概念原理，应用方面避免表单的重复提交和验证码只看懂原理，但自己出来写有难度

-------
-------
-------
<a href="#目录">回到目录</a>
