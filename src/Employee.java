abstract class Employee {
    private String name;
    private String id;
    private String email;
    private String employeetype;

    public Employee(){
        this.name=null;
        this.id=null;
        this.email=null;
        this.employeetype=null;
    }

    public Employee(String name, String id, String email,String employeetype) {
        this.name = name;
        this.id = id;
        this.email = email;
        this.employeetype = employeetype;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public abstract float calculateSalary();

    @Override
    public String toString() {
        return "Employee [name=" + name + ", id=" + id + ", email=" + email + ", Employee Type ="+employeetype+", Salary = "+calculateSalary()+"]";
    }
}
