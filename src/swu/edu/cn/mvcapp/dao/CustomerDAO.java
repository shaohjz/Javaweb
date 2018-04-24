package swu.edu.cn.mvcapp.dao;

import java.util.List;

import swu.edu.cn.mvcapp.dao.domain.Customer;

public interface CustomerDAO {
	public List<Customer> getForListWithCriteriaCustomer(CriteriaCustomer cc);
    public List<Customer> getAll();//获取Customer列表信息
    public void save(Customer customer);//对Customer的添加,通过CTRL+T转到定义
    public void update(Customer customer);//对Customer的更新,通过CTRL+T转到定义
    public Customer get(Integer id);//获取Customer实体
    public void delete(Integer id);//根据id进行删除
    public long getCountWithName(String name);//返回name相等的记录数
    //cc封装了查询条件，返回查询条件的list
    
}