//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        PayrollSystem pay =new PayrollSystem();

        FullTimeEmployee e1=new FullTimeEmployee("Snehanu","23042005","gokuui3232@gmail.com",60000.00f,0);
        FullTimeEmployee e2=new FullTimeEmployee("Rohit","23092005","goku@gmail.com",60000.00f,5);
        PartTimeEmployee e3=new PartTimeEmployee("Rahul","23042006","rahul@gmail.com",40,50.00f);
        System.out.println("Setup Done");

        pay.addEmployee(e1);
        pay.addEmployee(e3);
        pay.addEmployee(e2);
        System.out.println("Employee Added");
        pay.displayEmployees();

        pay.removeEmployee("23042006");
        System.out.println("Employee Deleted");
        System.out.println("List after Employee Deleted");
        pay.displayEmployees();
    }
}