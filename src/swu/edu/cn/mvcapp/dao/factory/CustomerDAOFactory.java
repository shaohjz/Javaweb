package swu.edu.cn.mvcapp.dao.factory;

import java.util.HashMap;
import java.util.Map;

import swu.edu.cn.mvcapp.dao.CustomerDAO;
import swu.edu.cn.mvcapp.dao.impl.CustomerDAOJdbcImpl;
import swu.edu.cn.mvcapp.dao.impl.CustomerDAOXMLImpl;

public class CustomerDAOFactory {
	
	private Map<String, CustomerDAO> daos = new HashMap<String, CustomerDAO>();
	
	
	private static CustomerDAOFactory instance = new CustomerDAOFactory();
	
	public static CustomerDAOFactory getInstance() {
		return instance;
	}
	
	private String type = null;
	
	public void setType(String type) {
		this.type = type;
	}
	
	private CustomerDAOFactory(){
		daos.put("jdbc", new CustomerDAOJdbcImpl());
		daos.put("xml", new CustomerDAOXMLImpl());
	}
	
	public CustomerDAO getCustomerDAO(){
		return daos.get(type);
	}
	
}
