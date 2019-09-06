package pojo;

/**
 * @author RayTing
 * @date 2019-01-06 14:07
 */
public class Employee {
    private int id;
    private String name;
    private String gender;
    private String password;

    public Employee() {
    }

    public Employee(String name, String gender, String password) {
        this.name = name;
        this.gender = gender;
        this.password = password;
    }

    public int getId() {
        return id;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
