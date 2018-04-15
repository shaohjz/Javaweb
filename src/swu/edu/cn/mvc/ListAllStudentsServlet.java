package swu.edu.cn.mvc;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class ListAllStudentsServlet
 */
@WebServlet("/listAllStudents")
public class ListAllStudentsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListAllStudentsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		/*response.getWriter().append("Served at: ").append(request.getContextPath());
		request.setAttribute("students", Arrays.asList("AA","BB","CC"));
		request.getRequestDispatcher("/students.jsp").forward(request, response);
	*/
		StudentDao studentDao =new StudentDao();
		List<Student> students= studentDao.getAll();
		request.setAttribute("students", students);
		request.getRequestDispatcher("/students.jsp").forward(request, response);
	
	}

}
