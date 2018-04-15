package swu.edu.cn.mvc;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class StudentDao {
	public void deleteByFlowID(Integer FlowID){

		List<Student> students =new ArrayList<>();
		Connection connection =null;
		PreparedStatement preparedStatement =null;
		try{
			//注册 JDBC 驱动
			Class.forName("com.mysql.jdbc.Driver");
			// 打开链接
			//connection = DriverManager.getConnection(url,user,passwords);
            connection= DriverManager.getConnection("jdbc:mysql://39.106.47.64:3306/student", "root", "123");
			// 执行查询
			String sql ="DELETE FROM student WHERE FlowID = 4";
			preparedStatement =connection.prepareStatement(sql);
			//preparedStatement.setInt(1, FlowID);//传入整数值
			System.out.println(sql);
			System.out.println(FlowID);
			
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			try {
				if(preparedStatement!=null){
					preparedStatement.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}try {
				if(connection !=null){
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			} 
		
	}
	public List<Student> getAll(){
		List<Student> students =new ArrayList<>();
		Connection connection =null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		try{
			//注册 JDBC 驱动
			Class.forName("com.mysql.jdbc.Driver");
			//String url ="jdbc:mysql://localhost:3306/student";
			//String url ="jdbc:mysql://39.106.47.64:3306/student";
			
		//	String user ="shaohjz";
		//	String passwords ="123";
			// 打开链接
            System.out.println("连接数据库...");//////////////
			//connection = DriverManager.getConnection(url,user,passwords);
            connection= DriverManager.getConnection("jdbc:mysql://39.106.47.64:3306/student", "root", "123");
			// 执行查询
			String sql ="SELECT  FlowID , Type , IDCard, ExamCard , StudentName , Location , Grade " + "FROM student";
			preparedStatement =connection.prepareStatement(sql);
			resultSet =preparedStatement.executeQuery();
			while(resultSet.next()){
				int flowID = resultSet.getInt(1);
				int type = resultSet.getInt(2);
				String idCard =resultSet.getString(3);
				String examCard =resultSet.getString(4);
				String studentName =resultSet.getString(5);
				String location =resultSet.getString(6);
				int grade = resultSet.getInt(7);
				Student student =new Student(flowID, type,idCard, examCard, studentName, location, grade);
				students.add(student);
			}
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			try {
				if(resultSet !=null){
					resultSet.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}try {
				if(preparedStatement!=null){
					preparedStatement.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}try {
				if(connection !=null){
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			} 
		
		return  students;
		
	}

}
