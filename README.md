<a name="目录"></a>
# 目录


<a href="#w1">第一至六周</a>

<a href="#w7">第七周</a>

<a href="#w8">第八周</a>

<a href="#w9">第九周</a>

<a href="#w10">第十周</a>

<a href="#w11">第十一周</a>

<a href="#w12">第十二周</a>




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
![MVC整体的架构](http://p7mezsuru.bkt.clouddn.com/2018-05-08-15257602281839.jpg)

2.多个请求对应一个 servlet：
    xml文件映射 *.do可以处理一切.do操作：
```
  <servlet-mapping>
    <servlet-name>CustomerServlet</servlet-name>
    <url-pattern>*.do</url-pattern>
  </servlet-mapping>
```
    在Servlet文件的doget和dopost方法中：

```

		//1. 获取 ServletPath: /edit.do 或 /addCustomer.do
		String servletPath = req.getServletPath();

		//2. 去除 / 和 .do, 得到类似于 edit 或 addCustomer 这样的字符串
		String methodName = servletPath.substring(1);
		methodName = methodName.substring(0, methodName.length() - 3);
		
		try {
			//3. 利用反射获取 methodName 对应的方法
			Method method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class, 
					HttpServletResponse.class);
			//4. 利用反射调用对应的方法
			method.invoke(this, req, resp);
		} catch (Exception e) {
			e.printStackTrace();
			//可以有一些响应.
			resp.sendRedirect("error.jsp");
		}
		
	}

```

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

# 第十周 session和bootstrap

## bootstrap

bootstrap是Twitter旗下的开源前段框架，使用比较简单。
jsp代码头部加入源，然后在要用的地方选择，比如button（发现form表单的sumbit按钮通用）选择输入相应的class即可。

练习中选择了两个按钮的样式，测试了一下

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

<a name="w11"></a>

-------
# 第十一周 Javabean和EL表达式
-------
<h3 id="toc_0">Javabean</h3>
一种JAVA语言写成的可重用组件。为写成JavaBean，类必须是具体的和公共的，并且具有无参数的构造器。

<strong>在JSP中如何使用JavaBean</strong>
<blockquote>
<pre><code>  &lt;jsp:useBean&gt;标签   创建和查找JavaBean的实例对象
  &lt;jsp:setProperty&gt;标签  设置JavaBean对象的属性
  &lt;jsp:getProperty&gt;标签  读取JavaBean对象的属性。
</code></pre>
</blockquote>
<h3 id="toc_1">EL表达式(expression language)</h3>
语法：${sessionScope.user.sex}
<pre><code>${sessionScope.user["sex”]} 等同于 ${sessionScope.user.sex}
    作用：
    (1) 当要存取的属性名称中包含一些特殊字符，如 . 或 – 等并非字母或数字的符号，就一定 要使用 [ ]，例如:
        ${user.My-Name }是错的
        ${user["My-Name"] }是对的

</code></pre>
<pre><code>${session Scope.username}取出session中的username变量 ==》String username =(String) session.getAttribute(“username”);

</code></pre>
<strong>EL自动转变类型</strong>，
因为jsp从窗体传来的值，他们的类型一律是String，接收后，再将他转为其它类型。
EL转换规则？
···
EL保留字(了解一下)，所谓保留字的意思是指变量在命名时，应该避开这些名字，以免编译错误

<img src="http://shaohjz.site/wp-content/uploads/2018/05/15262977232313.jpg" alt="" />￼

<strong>EL 隐含对象</strong>
JSP有9个隐含对象，而EL有11个
可分为三类：
<blockquote>
<pre><code> 1.与范围有关的隐含变量
 2.与输入有关的隐含变量
 3.其它隐含对象
</code></pre>
</blockquote>
<h5 id="toc_2">1）与范围有关的隐含对象，applicationScope、sessionScope、requestScope和pageScope</h5>
优先级： page》request》session》application
<h5 id="toc_3">2）与输入有关的隐含对象，param和paramValues</h5>
<pre><code>param获取一个请求参数： 
request.getParameter(String name)   ==》  ${param.name}      
paramValues获取一组请求参数：
request.getParameterValues(String name)  ==》 ${paramValues.name} 
</code></pre>
<h5 id="toc_4">3）其它隐含对象</h5>
<blockquote>pageContext 等(cookie, header, initParam 只需了解.) --&gt;
pageContext: pageContext 即为 PageContext 类型, 但只能读取属性就可以一直的 . 下去。</blockquote>
<pre><code>● cookie
所谓的 cooki e 是一个小小的文本文件，它是以 key、value 的方式将 Session Tracki ng 的内容记录在这个文本文件内，这个文本文件通常存在于浏览器的暂存区内。JSTL 并没有提供设定 cooki e 的动作， 因为这个动作通常都是后端开发者必须去做的事情，而不是交给前端的开发者。假若我们在 cooki e 中 设定一个名称为 user Count r y 的值，那么可以使用${ cooki e. user Count r y} 来取得它。

</code></pre>
<pre><code>● header 和 headerValues
header 储存用户浏览器和服务端用来沟通的数据，当用户要求服务端的网页时，会送出一个记载要求信息的标头文件，例如:用户浏览器的版本、用户计算机所设定的区域等其他相关数据。假 若要取得用户浏览器的版本，即${header["User-Agent"]}。另外在鲜少机会下，有可能同一标头名 称拥有不同的值，此时必须改为使用 headerValues 来取得这些值。


</code></pre>
<pre><code>● initParam
就像其他属性一样，我们可以自行设定 web 站台的环境参数(Context)，当我们想取得这些参数时，可以使用 initParam 隐含对象去取得它，例如:当我们在 web.xml 中设定如下:
 
`   &lt;context-param&gt; 
   &lt;param-name&gt;userid&lt;/param-name&gt; 
   &lt;param-value&gt;mike&lt;/param-value&gt;
    &lt;/context-param&gt;`
取得名为userid，值为 mike 的参数：
String userid=(String)application.getInitParameter("userid");
${initParam.userid}
</code></pre>
<pre><code>● pageContext
我们可以使用 ${pageContext}来取得其他有关用户要求或页面的详细信息。表 6-5 列出了几个 比较常用的部分。
</code></pre>
<img src="http://shaohjz.site/wp-content/uploads/2018/05/15262844149149.jpg" alt="" />￼
<img src="http://shaohjz.site/wp-content/uploads/2018/05/15262844295966.jpg" alt="" />￼

<img src="http://shaohjz.site/wp-content/uploads/2018/05/15262979834708.jpg" alt="" />￼
<img src="http://shaohjz.site/wp-content/uploads/2018/05/15262979988685.jpg" alt="" />￼

<img src="http://shaohjz.site/wp-content/uploads/2018/05/15262859823507.jpg" alt="" />￼
<img src="http://shaohjz.site/wp-content/uploads/2018/05/15262859961334.jpg" alt="" />￼
<pre><code>注意

因为 ${} 在JSP2.0中是特殊字符，JSP容器会自动将它当做EL来执行，因此，假若要显 示 ${}时，必须在 $ 前加上 \ ，如:\ ${ XXXXX }

</code></pre>
el就是不断地调用javabean的get方法。
学了jstl才可以遍历！

enumerate列举，枚举
<h5 id="toc_5">Empty运算符</h5>
<blockquote><!-- empty 可以作用于一个集合, 若该集合不存在或集合中没有元素, 其结果都为 true -->

Empt y 运算符主要用来判断值是否为 nul l 或空的，例如:
${empty param.name}</blockquote>
<pre><code>empty运算符的规则
(1) {empty} A
● 假若 A为 null时，回传 true
● 否则，假若A为空String时，回传 true
● 否则，假若A为空Array时，回传true
● 否则，假若A为空Map时，回传true
● 否则，假若A为空 Collection 时，回传true
● 否则，回传 fal se

</code></pre>
<h5 id="toc_6">条件运算符 ?</h5>
<pre><code class="language-${A">意思是说，当 A为 t r ue 时，执行 B;而 A为 f al se 时，则执行 C

</code></pre>
<h5 id="toc_7">EL函数 EL Functions</h5>
ns:function( arg1, arg2, arg3 .... argN)
其中 ns 为前置名称(prefix) ，它必须和 taglib 指令的前置名称一样。如下范例:
&lt;%@taglib prefix="my" uri="<a href="http://jakarta.apache.org/tomcat/jsp2-example-taglib">http://jakarta.apache.org/tomcat/jsp2-example-taglib</a>" %&gt;
.......
$ {my:function(param.name) }


-------

<p><a name="w12"></a></p>

<hr/>

<p><a href="#目录">回到目录</a></p>

<h1 id="toc_0">第十二周</h1>

<h2 id="toc_1">自定义标签</h2>

<blockquote>
<p>概念与原理：<br/>
用户定义的一种自定义的jsp标记 。jsp页面被编译为servlet时，tag标签被转化成了对一个称为 标签处理类 的对象的操作。<br/>
所以，当jsp页面被jsp引擎转化为servlet后，实际上tag标签被转化为了对tag处理类的操作。 </p>

<p><img src="http://shaohjz.site/wp-content/uploads/2018/05/15269943841426.jpg" alt=""/>￼</p>

<h3 id="toc_2">自定义标签(HelloSimpleTag)的过程</h3>

<blockquote>
<p>1). HelloWorld<br/>
①. 创建一个标签处理器类: 实现 SimpleTag 接口. <br/>
②. 在 WEB-INF 文件夹下新建一个 .tld(标签库描述文件) 为扩展名的 xml 文件. 并拷入固定的部分: 并对 <br/>
<del>description, display-name, tlib-version,</del> <br/>
<strong>short-name</strong>,  <strong>uri</strong> <br/>
做出修改<br/>
<strong>&lt;!-- 其中short-name, uri 最重要！ 标签 --&gt;</strong><br/>
③. 在 tld 文件中描述自定义的标签:</p>

<pre><code class="language-&lt;!--">   &lt;tag&gt;
    &lt;!-- 标签的名字: 在 JSP 页面上使用标签时的名字 --
    &lt;name&gt;hello&lt;/name&gt;
    &lt;!-- 标签所在的全类名 --&gt;
    &lt;tag-class&gt;com.atguigu.javaweb.tag.HelloSimpleTag&lt;/tag-class&gt;
    &lt;!-- 标签体的类型 --&gt;
    &lt;body-content&gt;empty&lt;/body-content&gt;
   &lt;/tag&gt;
</code></pre>

<p>④. 在 JSP 页面上使用自定义标签: </p>

<p>使用 taglib 指令导入标签库描述文件: &lt;%@taglib uri=&quot;<a href="http://www.atguigu.com/mytag/core">http://www.atguigu.com/mytag/core</a>&quot; prefix=&quot;atguigu&quot; %&gt;</p>

<p>使用自定义的标签: <a href="atguigu:hello/">atguigu:hello/</a> <br/>
```</p>

<h3 id="toc_3">带属性的自定义标签:</h3>

<p>①. 先在标签处理器类中定义 setter 方法. 建议把所有的属性类型都设置为 String 类型. </p>

<p>private String value;<br/>
private String count;</p>

<p>public void setValue(String value) {<br/>
    this.value = value;<br/>
}</p>

<p>public void setCount(String count) {<br/>
    this.count = count;<br/>
}</p>

<p>②. 在 tld 描述文件中来描述属性:</p>

<pre><code class="language-&lt;!--">&lt;attribute&gt;
  &lt;!-- 属性名, 需和标签处理器类的 setter 方法定义的属性相同 --&gt;
  &lt;name&gt;value&lt;/name&gt;
  &lt;!-- 该属性是否被必须 --&gt;
  &lt;required&gt;true&lt;/required&gt;
  &lt;!-- rtexprvalue: runtime expression value 
      当前属性是否可以接受运行时表达式的动态值 --&gt;
  &lt;rtexprvalue&gt;true&lt;/rtexprvalue&gt;
&lt;/attribute&gt;
</code></pre>

<p>③. 在页面中使用属性, 属性名同 tld 文件中定义的名字. </p>

<p><code>&lt;atguigu:hello value=&quot;${param.name }&quot; count=&quot;10&quot;/&gt;</code></p>
</blockquote>

<p>4). 通常情况下开发简单标签直接继承 SimpleTagSupport 就可以了. 可以直接调用其对应的 getter 方法得到对应的 API </p>

<h3 id="toc_4">带标签体的自定义标签</h3>

<blockquote>
<p>1). 若一个标签有标签体: <br/>
(在jsp中)<br/>
    ```
 <a href="atguigu:testJspFragment">atguigu:testJspFragment</a>abcdefg<a 
 href="/atguigu:testJspFragment">/atguigu:testJspFragment</a></p>
 ```

<p>在自定义标签的标签处理器中使用 JspFragment 对象封装标签体信息. </p>

<p>2). 若配置了标签含有标签体, 则 JSP 引擎会调用 setJspBody() 方法把 JspFragment 传递给标签处理器类<br/>
在 SimpleTagSupport 中还定义了一个 getJspBody() 方法, 用于返回 JspFragment 对象. </p>

<p>3). JspFragment 的 invoke(Writer) 方法: 把标签体内容从 Writer 中输出, 若为 null, <br/>
则等同于 invoke(getJspContext().getOut()), 即直接把标签体内容输出到页面上</p>

<p>有时, 可以 借助于 StringWriter, 可以在标签处理器类中先得到标签体的内容: </p>

<pre><code>   1. 利用 StringWriter 得到标签体的内容.
   StringWriter sw = new StringWriter();
   bodyContent.invoke(sw);
   2. 把标签体的内容都变为大写
    String content = sw.toString().toUpperCase();
</code></pre>

<p>4). 在 tld 文件中, 使用 body-content 节点来描述标签体的类型: </p>

<p><body-content>: 指定标签体的类型, 大部分情况下, 取值为 scriptless。可能取值有 3 种：<br/>
empty: 没有标签体<br/><br/>
scriptless: 标签体可以包含 el 表达式和 JSP 动作元素，但不能包含 JSP 的脚本元素<br/>
tagdependent: 表示标签体交由标签本身去解析处理。<br/>
若指定 tagdependent，在标签体中的所有代码都会原封不动的交给标签处理器，而不是将执行结果传递给标签处理器</p>

<p><body-content>tagdependent</body-content></p>

<p>5). 定义一个自定义标签: <atguigu:printUpper time="10">abcdefg</atguigu> 把标签体内容转换为大写, 并输出 time 次到<br/>
浏览器上. </p>
</blockquote>

<h3 id="toc_5">带父标签体的标签</h3>

<blockquote>
<p>使用：</p>

<pre><code>    &lt;c:choose&gt;
        &lt;c:when test=&quot;${param.age &gt; 24}&quot;&gt;大学毕业&lt;/c:when&gt;
        &lt;c:when test=&quot;${param.age &gt; 20}&quot;&gt;高中毕业&lt;/c:when&gt;
        &lt;c:otherwise&gt;高中以下...&lt;/c:otherwise&gt;
    &lt;/c:choose&gt;
</code></pre>

<p>1). 父标签无法获取子标签的引用, 父标签仅把子标签作为标签体来使用. <br/>
2). 子标签可以通过 getParent() 方法来获取父标签的引用。</p>

<pre><code>    需继承 
    SimpleTagSupport 
    或
    自实现 SimpleTag 接口的该方法
</code></pre>
</blockquote>

<h3 id="toc_6">EL的自定义函数（几乎不用）</h3>
</blockquote>

<p>练习1：<br/>
定制一个带有两个属性的标签<max>, 用于计算并输出两个数的最大值<br/>
[练习1](http://shaohjz.site:8080/JAVAWEB_LEARN/easytag/test.jsp)


练习2：</p>
[练习2](http://shaohjz.site:8080/JAVAWEB_LEARN/easytag/test2.jsp)


<a href="#目录">回到目录</a>
