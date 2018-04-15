package swu.edu.cn.mvc;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DeleteStudent
 */
@WebServlet("/DeleteStudent")
public class DeleteStudent extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String FlowID=request.getParameter("flowID");
		System.out.println(FlowID);
		StudentDao studentDao =new StudentDao();
		studentDao.deleteByFlowID(Integer.parseInt(FlowID));//把 flowid 字符型强制转换为一个整数型
		request.getRequestDispatcher("/success.jsp").forward(request, response);
		
	}

}
