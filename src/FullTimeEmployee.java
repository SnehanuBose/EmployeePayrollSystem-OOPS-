public class FullTimeEmployee extends Employee{

    private float salary ;
    private int leavedays;

    public FullTimeEmployee(String name, String id, String email, float salary,int leaves) {
        super(name, id, email,"FULL_TIME");
        this.salary = salary;
        this.leavedays=leaves;
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



}
