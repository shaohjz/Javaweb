/**
 * 
 */
package swu.edu.cn.mvcapp.test;

import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;

import swu.edu.cn.mvcapp.dao.CustomerDAO;
import swu.edu.cn.mvcapp.dao.domain.Customer;
import swu.edu.cn.mvcapp.dao.impl.CustomerDAOJdbcImpl;

/**
* @author 作者
* @version 创建时间：2018年4月16日 下午7:20:50
* 类说明
*/
/**
 * @author hujizhou
 *
 */
public class CustomerDAOJdbcImpltest {
	 private  CustomerDAO customerDAO=new CustomerDAOJdbcImpl();
	@Test
	public void testGetAll() {
        List<Customer> customers=customerDAO.getAll();
        System.out.println(customers);
    }

    @Test
    public void testSaveCustomer() {
        Customer customer=new Customer("junit","Shanghai","124-2345-9086");
        customerDAO.save(customer);
    }

    @Test
    public void testGetInt() {
        Customer cust=customerDAO.get(0);
        System.out.println(cust);
    }

    @Test
    public void testDelete() {
        customerDAO.delete(2);
    }

    @Test
    public void testGetCountWithName() {
        long count=customerDAO.getCountWithName("Tom");
        System.out.println(count);
    }


}
