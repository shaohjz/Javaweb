 package swu.edu.cn.mvcapp.dao.impl;
 
 import java.util.List;

import swu.edu.cn.mvcapp.dao.CustomerDAO;
 import swu.edu.cn.mvcapp.dao.DAO;
 import swu.edu.cn.mvcapp.dao.domain.Customer;;


public class CustomerDAOJdbcImpl extends DAO<Customer> implements CustomerDAO{
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
