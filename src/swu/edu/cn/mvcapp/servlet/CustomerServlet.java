/**
 * 
 */
package swu.edu.cn.mvcapp.servlet;
/**
* @author 作者
* @version 创建时间：2018年4月16日 下午8:26:28
* 类说明
*/
/**
 * @author hujizhou
 *
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import swu.edu.cn.mvcapp.dao.CustomerDAO;
import swu.edu.cn.mvcapp.dao.impl.CustomerDAOJdbcImpl;
import swu.edu.cn.mvcapp.dao.domain.Customer;

public class CustomerServlet extends HttpServlet {


    //private CustomerDAO customerDAO=new CustomerDAOJdbcImpl();
    //private CustomerDAO customerDAO=new CustomerDAOXMLImpl();
	//  private CustomerDAO customerDAO=CustomerDAOFactory.getInstance().getCustomerDAO();
	private CustomerDAO customerDAO=new CustomerDAOJdbcImpl();
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
        System.out.println("test doget");
    }
   /* @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String method=request.getParameter("method");
        System.out.println("test");
        System.out.println(method);
        switch (method) {
        case "add":  add(request,response); break;
        case "query": query(request,response); break;
        case "delete": delete(request,response);break;
        default: break;
        }
    }*/
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        //1 获取servlet路径 诸如：/add.do
        String servletPath=req.getServletPath().substring(1);
        System.out.println("test");
        
        //去除/和.do得到类似于add这样字符串
        String methodName=servletPath.substring(0,servletPath.length()-3);
        System.out.println(methodName);
        try {
            //利用反射获取获取methodName对应的方法
            Method method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class,HttpServletResponse.class);
            //利用反射获取方法
            method.invoke(this, req,resp);
        } catch (Exception e) {
            //出错时候响应出来
            resp.sendRedirect("error.jsp");
        }
    }
    private void edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String forwordPath="/error.jsp";
        //1 获取请求参数id
        String idstr=request.getParameter("id");
        //2 调用CustomeDAO的customerDAO.get(id)获取和id对应的Customer对象customer
        try{
            Customer customer=customerDAO.get(Integer.parseInt(idstr));
            if(customer!=null){
                forwordPath="/updatecustomer.jsp";
                //3 将customer放在request中
                request.setAttribute("customer", customer);
            }
        }catch(Exception e){}
        //4 响应updatecustomer.jsp页面：转发
        request.getRequestDispatcher(forwordPath).forward(request, response);
    }
    private void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //1 获取请求参数:id,name,address,phone,oldname
        String id=request.getParameter("id");
        String name=request.getParameter("name");
        String oldname=request.getParameter("oldname");
        String address=request.getParameter("address");
        String phone=request.getParameter("phone");
        //2  检验name是否被占用
        //2.1  比较name和oldname是否相同，若相同name可用，oldname.equals(name)不如equalsIgnoreCase，数据库默认大小写一致的，而equals忽略大小写
        if(!oldname.equalsIgnoreCase(name)){
            //不相同，调用CustomerDAO的getCountWithName(String name)获取name在数据库中是否存在
            long count=customerDAO.getCountWithName(name);
            //大于0， 响应updatecustomer.jsp页面：通过转发响应newcustomer.jsp
            if(count>0){
                // 通过request.getAttribute("message")显示信息，在页面上request.getAttribute("message")的方式显示
                // 表单据回显。address,phone显示提交的新值， name显示oldname，而不是新值
                request.setAttribute("message", "用户名["+name+"]已经被占用，请重新填写！");
                // 方法结束
                request.getRequestDispatcher("/updatecustomer.jsp").forward(request, response);
                return;
            }
        }
        //3 若验证通过，把表单参数封装为一个Customer对象customer
        Customer customer=new Customer(name,address,phone);
        customer.setId(Integer.parseInt(id));
        //4 调用CustomerDAO的update(Customer customer)执行更新操作
        customerDAO.update(customer);
        //5 重定向到query.do
        response.sendRedirect("query.do");
        
    }
    //模糊查询
    private void query(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String name=request.getParameter("name");
        String address=request.getParameter("address");
        String phone=request.getParameter("phone");
        //1 调用CustomerDAO的getALl方法得到Customer集合
        //获取所有信息列表
        List<Customer> customers=customerDAO.getAll();
        //List<Customer> customers=customerDAO.getForListWithCriteriaCustomer(cc);
        //2 把customer的集合放入request
        request.setAttribute("customers", customers);
        //3 转发页面index.jsp（不能使用重定向）
        request.getRequestDispatcher("/index.jsp").forward(request,response);
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String idstr=request.getParameter("id").trim();
        int id=0;
        try{
            id=Integer.parseInt(idstr);
            customerDAO.delete(id);
        }catch(Exception e){}
        response.sendRedirect("query.do");
    }

    //此方法名称跟页面add添加的action中add.do匹配
    private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //1 获取表单参数：name，address，phone
        String name=request.getParameter("name");
        String address=request.getParameter("address");
        String phone=request.getParameter("phone");
        //2  检验name是否被占用
        //2.1  调用CustomerDAO的getCountWithName(String name)获取name在数据库中是否存在
        long count=customerDAO.getCountWithName(name);
        if(count>0){
        //2.2 若返回值大于0，则相应newcustomer.jsp页面：①在此页面显示一个错误信息②此表单值可以回显
        //     通过request.getAttribute("message")显示信息
        //     通过value="<%=request.getParameter("name")==null?"":request.getParameter("name")%>"回显
            request.setAttribute("message", "用户名["+name+"]已经被占用，请重新填写！");
            request.getRequestDispatcher("/newcustomer.jsp").forward(request, response);
            return;
        }
        //3 若验证通过，把表单参数封装为一个Customer对象customer
        Customer customer=new Customer(name,address,phone);
        //4 调用CustomerDAO的save(Customer customer)执行保存操作
        customerDAO.save(customer);
        //5 重定向到success.jsp页面
        response.sendRedirect("success.jsp");
    }

}