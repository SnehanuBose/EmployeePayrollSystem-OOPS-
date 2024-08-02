public class FullTimeEmployee extends Employee{

    private float salary ;
    private int leavedays;

    public FullTimeEmployee(String name, String id, String email, float salary,int leaves) {
        super(name, id, email,"FULL_TIME");
        this.salary = salary;
        this.leavedays=leaves;
    }

    public FullTimeEmployee(){
        super(null, null, null,"FULL_TIME");
        this.salary = 0;
        this.leavedays=0;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public double getSalary() {
        return salary;
    }

    @Override
    public float calculateSalary() {
        return salary-((salary/30)*leavedays);
    }

    public int getLeavedays(){
        return leavedays;
    }
    public void setLeavedays(int days){
        this.leavedays=days;
    }

}
