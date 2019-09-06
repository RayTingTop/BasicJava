import dao.BaseDao;
import pojo.Dept;
import pojo.Employee;
import utils.UsingReflect;

/**
 * @author RayTing
 * @date 2019-01-06 14:44
 * JDBC访问数据库，使用Java反射机制实现 增加 和 查询
 *
 * 保存数据时，把需要保存的对象的属性值全部取出来再拼凑sql语句
 * 查询时，将查询到的数据全部包装成一个java对象
 *
 * 无需创建多个dao,自动判断类和表、执行语句字段参数、结果处理
 */
public class Test {
    BaseDao dao = new BaseDao();

    /**
     * 测试员工添加
     */
    @org.junit.Test
    public void testAddEmp(){
        Employee employee = new Employee("张三", "男", "123");
        System.out.println(UsingReflect.getSaveSql(employee));

        int result = dao.save(employee);
        System.out.println(result==1?"执行结果"+result+"添加成功":"执行结果"+result+"失败！");
    }

    /**
     * 测试员工查询
     */
    @org.junit.Test
    public void testGetEmp(){

        Employee employee = (Employee) dao.getById(new Employee(),1);
        System.out.println(employee);
    }


    /**
     * 测试员工添加
     */
    @org.junit.Test
    public void testAddDept(){
        Dept dept = new Dept( "测试部","dept1");
        System.out.println(UsingReflect.getSaveSql(dept));

        int result = dao.save(dept);
        System.out.println(result==1?"执行结果"+result+"添加成功":"执行结果"+result+"失败！");
    }

    /**
     * 测试员工查询
     */
    @org.junit.Test
    public void testGetDept(){
        Dept dept = (Dept) dao.getById(new Dept(),1);
        System.out.println(dept);
    }
}