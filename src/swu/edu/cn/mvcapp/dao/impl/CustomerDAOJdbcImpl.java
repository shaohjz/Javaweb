 package swu.edu.cn.mvcapp.dao.impl;
 
 import java.util.List;

import swu.edu.cn.mvcapp.dao.CustomerDAO;
 import swu.edu.cn.mvcapp.dao.DAO;
 import swu.edu.cn.mvcapp.dao.domain.Customer;
 import swu.edu.cn.mvcapp.dao.CriteriaCustomer;

public class CustomerDAOJdbcImpl extends DAO<Customer> implements CustomerDAO{
	public List<Customer> getForListWithCriteriaCustomer(CriteriaCustomer cc) {
		String sql = "SELECT id, name, address, phone FROM customers WHERE " +
				"name LIKE ? AND address LIKE ? AND phone LIKE ?";
		//修改了 CriteriaCustomer 的 getter 方法: 使其返回的字符串中有 "%%".
		//若返回值为 null 则返回 "%%", 若不为 null, 则返回 "%" + 字段本身的值 + "%"
		return getForList(sql, cc.getName(), cc.getAddress(), cc.getPhone());
	}
	
    @Override
    public List<Customer> getAll() {
        String sql="select * from customers";
        return getForList(sql);
    }
    @Override
    public void save(Customer customer) {
        String sql="insert customers(name,address,phone) values(?,?,?)";
        update(sql, customer.getName(),customer.getAddress(),customer.getPhone());
    }
    @Override
    public Customer get(int id) {
        String sql="select * from customers where id=?";
        return get(sql,id);
    }

    @Override
    public void delete(int id) {
        String sql="delete  from customers where id=?";
        update(sql, id);
    }

    @Override
    public long getCountWithName(String name) {
        String sql="select count(id) from customers where name=?";
        return getForValue(sql, name);
    }
    
    @Override
    public void update(Customer customer) {
        String sql="update customers set name=?,address=?,phone=? where id=?";
        update(sql,customer.getName(),customer.getAddress(),customer.getPhone(),customer.getId());
    }
 	
}
