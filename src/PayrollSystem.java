import java.util.ArrayList;

public class PayrollSystem {
    private ArrayList<Employee> employeeList;

    public PayrollSystem(){
        employeeList=new ArrayList<Employee>();
    }

    public void addEmployee(Employee employee){
        employeeList.add(employee);
    }

    public void removeEmployee(String id){
        for(Employee employee : employeeList){
            if(employee.getId().equals(id)){
                employeeList.remove(employee);
                break;
            }
        }
    }

    public void displayEmployees(){
        for(Employee employee : employeeList){
            System.out.println(employee.toString());
        }
    }



}
