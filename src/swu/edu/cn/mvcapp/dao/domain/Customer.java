package swu.edu.cn.mvcapp.dao.domain;


public class Customer {
    
    private int  id;
    private String name;
    private String address;
    private String phone;
    public int getId() {
        return id;
    }
    public Customer() {
        
    }
    public Customer(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    @Override
    public String toString(){
        return "Customer [id="+id+",name="+name+",address"+address+
                ",phone="+phone+"]";
    }
}