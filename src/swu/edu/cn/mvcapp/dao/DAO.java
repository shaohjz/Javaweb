package swu.edu.cn.mvcapp.dao;


import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import swu.edu.cn.mvcapp.db.jdbcUtils;



/**
 * 封装了基本的CRUD的方法，以供子类继承使用
 * 当前DAO直接在方法中获取数据库连接
 * @param <T> ：当前DAO处理实体的类型是什么
 *
 */
public class DAO<T> {
    //此步骤前需要/lib加入commons-dbutils-xx.jar
    private QueryRunner  queryRunner=new QueryRunner();
    private Class<T> clazz;
    public DAO(){
    	//讲反射的视频？？
        //Type通过Ctrl+Shift+O进行反射Type选择
        Type superClass=getClass().getGenericSuperclass();
        
        if(superClass instanceof ParameterizedType){
            ParameterizedType parameterizedType=(ParameterizedType)superClass;
            
            Type[] typeArgs=parameterizedType.getActualTypeArguments();
            if(typeArgs!=null && typeArgs.length>0){
                if(typeArgs[0] instanceof Class)    clazz=(Class<T>)typeArgs[0];
            }
        }
    }    
    /**
     * 返回某一个字段的值，或者返回数据表中有多少条记录等。
     * @param sql：SQL语句
     * @param args：填充SQL语句的占位符
     * @return
     */
    public <E> E getForValue(String sql,Object ... args) {
        Connection connection=null;
       try{
            connection=jdbcUtils.getConnection();
            return (E) queryRunner.query(connection,sql,new ScalarHandler<T>(),args);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            jdbcUtils.releaseConnection(connection);
        }
        return null;
    }
    /**
     * 返回T所对应的List
     * @param sql：SQL语句
     * @param args：填充SQL语句的占位符
     * @return
     */
    public List<T> getForList(String sql,Object ... args){
        Connection connection=null;
       try{
            connection=jdbcUtils.getConnection();
            
            return queryRunner.query(connection,sql,new BeanListHandler<>(clazz),args);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            jdbcUtils.releaseConnection(connection);
        }
        return null;
    }
    /**
     * 返回对应T的一个实体类对象
     * @param sql：SQL语句
     * @param args：填充SQL语句的占位符
     * @return
     */
    public T get(String sql,Object ... args){
        Connection connection=null;
        System.out.println(clazz);
        try{
            connection=jdbcUtils.getConnection();
            return queryRunner.query(connection,sql,new BeanHandler<>(clazz),args);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            jdbcUtils.releaseConnection(connection);
        }
        return null;
    }
    /**
     * 该方法封装了INSERT、DELETE、UPDATE操作
     * @param sql：SQL语句
     * @param args：填充SQL语句的占位符
     */
    public void update(String sql,Object ... args){
        Connection connection=null;
        try{
            connection=jdbcUtils.getConnection();
            queryRunner.update(connection,sql,args);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            jdbcUtils.releaseConnection(connection);
        }
    }
}
