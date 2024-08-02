public class PartTimeEmployee extends Employee {
    private float hrsworked;
    private float hourlyrate;

    public PartTimeEmployee(String name, String id, String email, float hrsworked, float hourlyrate) {
        super(name, id, email,"PART_TIME");
        this.hrsworked = hrsworked;
        this.hourlyrate = hourlyrate;
    }
    public PartTimeEmployee(){
        super(null,null,null,"PART_TIME");
        this.hrsworked = 0.0f;
        this.hourlyrate = 0.0f;
    }

    public void setHrsworked(float hrsworked) {
        this.hrsworked = hrsworked;
    }

    public float getHrsworked() {
        return hrsworked;
    }

    public void setHourlyrate(float hourlyrate) {
        this.hourlyrate = hourlyrate;
    }
    public float getHourlyrate() {
        return hourlyrate;
    }

    public float calculateSalary() {
        return hrsworked * hourlyrate;
    }

}
