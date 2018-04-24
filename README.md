[MVC教程例子实现链接](http://shaohjz.site:8080/JAVAWEB_LEARN/)
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