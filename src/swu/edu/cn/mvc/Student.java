package swu.edu.cn.mvc;

public class Student {
public Integer getFlowID() {
		return flowID;
	}
	public void setFlowID(Integer flowID) {
		this.flowID = flowID;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getExamCard() {
		return examCard;
	}
	public void setExamCard(String examCard) {
		this.examCard = examCard;
	}
	//构造器
	public Student(Integer flowID, int type, String idCard, String examCard, String studentName, String location,
			int grade) {
		super();
		this.flowID = flowID;
		this.type = type;
		this.idCard = idCard;
		this.examCard = examCard;
		this.studentName = studentName;
		this.location = location;
		this.grade = grade;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getGrade() { 
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
private Integer flowID;
private int type;
private String idCard;
private String examCard;
private String studentName;
private String location;
public Student() {
	super();
	// TODO Auto-generated constructor stub
}
private int grade;



}
