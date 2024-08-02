import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class PayrollSystem {

    private ArrayList<Employee> employeeList;//store the employee objects

    private String uname=System.getenv("db-name");//username of the db
    private String pass=System.getenv("db-pass");//password of the db
    private String url=System.getenv("db-url");//Url for the databasw

    //constructor
    public PayrollSystem(){
        employeeList=new ArrayList<Employee>();
    }


    //connectiong to the db
    private Connection connectDb(){
        Connection conn=null;
        try {
            Class.forName("org.postgresql.Driver");
            conn= DriverManager.getConnection(url,uname,pass);
            if (conn!=null){
                System.out.println("Connected to the database");
            }else {
                System.out.println("Failed to connect to the database");
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return conn;
    }

    //Inserting Employees
    public void addEmployee(FullTimeEmployee employee){
        //adds full time employee
        Statement statement;
        try{
            Connection conn= connectDb();
            String query ="INSERT INTO full_time_employee(empname,empemail,salary,leaves) VALUES( '"+employee.getName()+"', '"+employee.getEmail()+"', "+employee.calculateSalary()+", "+employee.getLeavedays()+");";
//            System.out.println(query);//for debug purpose
            statement=conn.createStatement();
            statement.executeUpdate(query);
            conn.close();
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public void addEmployee(PartTimeEmployee employee){
        //add part time employee
        Statement statement;
        Connection conn= connectDb();
        try{
            String query ="INSERT INTO part_time_employee(empname,empemail,salary,workhours,ratephr) " +
                    "VALUES('"+employee.getName()+"','"+employee.getEmail()+
                    "',"+employee.calculateSalary()+","+employee.getHrsworked()+","+employee.getHourlyrate()+");";
            statement=conn.createStatement();
            statement.executeUpdate(query);
            conn.close();
        }catch(Exception e){
            System.out.println(e);
        }
        employeeList.add(employee);
    }

    //Update -- TODO
    public void updateDetails(){
        //will do later
    }

    //Remove
    public void removeEmployee(String name,String employeeType){ //overloading the removeEmployee method to enable to remove for both id and name
        Statement statement;
        Connection conn=connectDb();
        try{
            if(employeeType.equals("FULL_TIME")){
                String query = "DELETE FROM full_time_employee WHERE empname='"+name+"'";
                statement=conn.createStatement();
                statement.executeUpdate(query);
            }else if (employeeType.equals("PART_TIME")){
                String query = "DELETE FROM part_time_employee WHERE empname='"+name+"'";
                statement=conn.createStatement();
                statement.executeUpdate(query);
            }
            conn.close();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void removeEmployee(int id,String employeeType){//overloading the removeEmployee method to enable to remove for both id and name
        String eid=Integer.toString(id);
        Statement statement;
        Connection conn=connectDb();
        try{
            if(employeeType.equals("FULL_TIME")){
                String query = "DELETE FROM full_time_employee WHERE empid='"+eid+"'";
                statement=conn.createStatement();
                statement.executeUpdate(query);
            }else if (employeeType.equals("PART_TIME")){
                String query = "DELETE FROM part_time_employee WHERE empid='"+eid+"'";
                statement=conn.createStatement();
                statement.executeUpdate(query);
            }
            conn.close();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void getEmployees(int ch){ //get the list of employees from the table and display it
        //ch- 1 for full time
        //ch- 2 for part time
        //ch- 3 for both -- need to work on it
        Statement statement;
        try{
            Connection conn= connectDb();
            String query=null;
            ResultSet rs=null;
            switch(ch){
                case 1:
                    query="SELECT * FROM full_time_employee;";
                    statement=conn.createStatement();
                    rs=statement.executeQuery(query);
                    while(rs.next()){
                        FullTimeEmployee ftemp=new FullTimeEmployee();
                        ftemp.setId(rs.getString("empid"));
                        ftemp.setName(rs.getString("empname"));
                        ftemp.setEmail(rs.getString("empemail"));
                        ftemp.setSalary(Float.parseFloat(rs.getString("salary")));
                        ftemp.setLeavedays(Integer.parseInt(rs.getString("leaves")));
                        employeeList.add(ftemp);
                    }
                    break;
                case 2:
                    query="SELECT * FROM part_time_employee;";
                    statement=conn.createStatement();
                    rs=statement.executeQuery(query);
                    while(rs.next()){
                        PartTimeEmployee ptemp=new PartTimeEmployee();
                        ptemp.setId(rs.getString("empid"));
                        ptemp.setName(rs.getString("empname"));
                        ptemp.setEmail(rs.getString("empemail"));
                        ptemp.setHourlyrate(Float.parseFloat(rs.getString("ratephr")));
                        ptemp.setHrsworked(Float.parseFloat(rs.getString("workhours")));
                        employeeList.add(ptemp);
                    }
                    break;
                case 3:
                    //will do later
                    break;
                default:
                    System.out.println("Invalid choice");
                    return;
            }
            conn.close();
        }catch(Exception e){
            System.out.println(e);
        }
        display();
    }

    //Search
    public void searchEmployee(int id,String employeeType){ //overloading the searchEmployee method to enable to search for both id and name
        String eid=Integer.toString(id);
        Statement statement;
        try {
            Connection conn= connectDb();
            ResultSet rs=null;
            Statement statement1;
            if(employeeType.equals("FULL_TIME")) {
                String query = "SELECT * from full_time_employee where empid='" + eid + "'";
                statement = conn.createStatement();
                rs = statement.executeQuery(query);
                while (rs.next()) {
                    FullTimeEmployee ftemp = new FullTimeEmployee();
                    ftemp.setId(rs.getString("empid"));
                    ftemp.setName(rs.getString("empname"));
                    ftemp.setEmail(rs.getString("empemail"));
                    ftemp.setSalary(Float.parseFloat(rs.getString("salary")));
                    ftemp.setLeavedays(Integer.parseInt(rs.getString("leaves")));
                    employeeList.add(ftemp);
                }
                display();
                return;
            }else {
                String query = "SELECT * from part_time_employee where empid='" + eid + "'";
                statement = conn.createStatement();
                rs = statement.executeQuery(query);
                while (rs.next()) {
                    PartTimeEmployee ptemp=new PartTimeEmployee();
                    ptemp.setId(rs.getString("empid"));
                    ptemp.setName(rs.getString("empname"));
                    ptemp.setEmail(rs.getString("empemail"));
                    ptemp.setHourlyrate(Float.parseFloat(rs.getString("ratephr")));
                    ptemp.setHrsworked(Float.parseFloat(rs.getString("workhours")));
                    employeeList.add(ptemp);
                }
                display();
                return;
            }
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public void searchEmployee(String name,String employeeType){//overloading the searchEmployee method to enable to search for both id and name
        Statement statement;
        try {
            Connection conn= connectDb();
            ResultSet rs=null;
            Statement statement1;
            if(employeeType.equals("FULL_TIME")) {
                String query = "SELECT * from full_time_employee where empname='" + name + "'";
                statement = conn.createStatement();
                rs = statement.executeQuery(query);
                while (rs.next()) {
                    FullTimeEmployee ftemp = new FullTimeEmployee();
                    ftemp.setId(rs.getString("empid"));
                    ftemp.setName(rs.getString("empname"));
                    ftemp.setEmail(rs.getString("empemail"));
                    ftemp.setSalary(Float.parseFloat(rs.getString("salary")));
                    ftemp.setLeavedays(Integer.parseInt(rs.getString("leaves")));
                    employeeList.add(ftemp);
                }
                display();
                return;
            }else {
                String query = "SELECT * from part_time_employee where empname='" + name + "'";
                statement = conn.createStatement();
                rs = statement.executeQuery(query);
                while (rs.next()) {
                    PartTimeEmployee ptemp=new PartTimeEmployee();
                    ptemp.setId(rs.getString("empid"));
                    ptemp.setName(rs.getString("empname"));
                    ptemp.setEmail(rs.getString("empemail"));
                    ptemp.setHourlyrate(Float.parseFloat(rs.getString("ratephr")));
                    ptemp.setHrsworked(Float.parseFloat(rs.getString("workhours")));
                    employeeList.add(ptemp);
                }
                display();
                return;
            }
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //Display
    public void display(){//to display the employees in the list
        for(Employee employee : employeeList){
            System.out.println(employee.toString());
        }
    }
}
