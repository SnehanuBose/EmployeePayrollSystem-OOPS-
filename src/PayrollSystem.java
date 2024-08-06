import javax.swing.plaf.nimbus.State;
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
    public void updateDetails(float salary,String etype,int id){
        //update the salary based on employee id
        Statement statement;
        try{
            String query = null;
            Connection conn=connectDb();
            if(etype.equals("FULL_TIME")){
                query = "UPDATE full_time_employee SET salary = '"+salary+"' WHERE empid ='"+id+"'";
                statement = conn.createStatement();
                statement.executeUpdate(query);
            }else if(etype.equals("PART_TIME")){
                query = "UPDATE part_time_employee SET salary = '"+salary+"' WHERE empid ='"+id+"'";
                statement = conn.createStatement();
                statement.executeUpdate(query);

            }else{
                System.out.println("Invalid Employee Type");
            }


        }catch(Exception e){
            System.out.println(e);
        }
    }

    public void updateDetails(float salary,String etype,String name){
        //update salary based on name of employee
        Statement statement;
        try{
            String query = null;
            Connection conn=connectDb();
            if(etype.equals("FULL_TIME")){
                query = "UPDATE full_time_employee SET salary = '"+salary+"' WHERE empname ='"+name+"'";
                statement = conn.createStatement();
                statement.executeUpdate(query);
            }else if(etype.equals("PART_TIME")){
                query = "UPDATE part_time_employee SET salary = '"+salary+"' WHERE empname ='"+name+"'";
                statement = conn.createStatement();
                statement.executeUpdate(query);

            }else{
                System.out.println("Invalid Employee Type");
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public void updateLeaves(int id){
        //update leaves for employee based on id
        Statement statement;
        try{
            String query = null;
            Connection conn=connectDb();
            query = "UPDATE full_time_employee SET leaves = leaves + 1 WHERE empid ='"+id+"'";
            statement = conn.createStatement();
            statement.executeUpdate(query);

        }catch(Exception e){
            System.out.println(e);
        }
    }

    public void updateLeaves(int id,int leave){
        //update leaves for employee based on id
        Statement statement;
        try{
            String query = null;
            Connection conn=connectDb();
            query = "UPDATE full_time_employee SET leaves = leaves + "+leave+" WHERE empid ='"+id+"'";
            statement = conn.createStatement();
            statement.executeUpdate(query);
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public void resetLeaves(){//reset the leaves of all the employees
        Statement statement;
        try{
            Connection conn=connectDb();
            String query=null;
            int count=0;
            count=getlength("FULL_TIME");
            for(int i =1;i<=count;i++){
                query = "UPDATE full_time_employee SET leaves = 0 WHERE empid ='"+i+"'";
                statement=conn.createStatement();
                statement.executeUpdate(query);
            }
        }catch(Exception e){
            System.out.println(e);
        }
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

    //get details
    public void getEmployeeDetails(int ch){ //get the list of employees from the table and display it
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
                    display();
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
                    display();
                    break;
                case 3:
                    System.out.println("FULL TIME EMPLOYEE LIST");
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
                    display();
                    employeeList.clear();
                    System.out.println("PART TIME EMPLOYEE LIST");
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
                    display();
                    break;
                default:
                    System.out.println("Invalid choice");
                    return;
            }
            conn.close();
        }catch(Exception e){
            System.out.println(e);
        }
    }
    public void getEmployeeDetails(int ch,String name){ //get the  details of tnhe employee based on the name
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
                    query="SELECT * FROM full_time_employee WHERE empname='"+name+"';";
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
                    query="SELECT * FROM part_time_employee WHERE empname='"+name+"';";
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

    public void getEmployeeDetails(int ch,int id){ //get the details of a particular employee based on the id
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
                    query="SELECT * FROM full_time_employee WHERE empid='"+id+"';";
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
                    query="SELECT * FROM part_time_employee WHERE empid='"+id+"';";
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
    private void display(){//to display the employees in the list
        for(Employee employee : employeeList){
            System.out.println(employee.toString());
        }
    }

    private int getlength(String etype){//return the length of the table
        Statement statement;
        try{
            Connection conn=connectDb();
            if(etype.equals("FULL_TIME")){
                String query="SELECT COUNT(*) as rowscount FROM full_time_employee;";
                statement=conn.createStatement();
                ResultSet rs=statement.executeQuery(query);
                rs.next();
                return rs.getInt("rowscount");
            }else if(etype.equals("PART_TIME")){
                String query="SELECT COUNT(*) as rowscount FROM part_time_employee;";
                statement=conn.createStatement();
                ResultSet rs=statement.executeQuery(query);
                rs.next();
                return rs.getInt("rowscount");
            }

        }catch(Exception e){
            System.out.println(e);
        }
        return -1;
    }
}
