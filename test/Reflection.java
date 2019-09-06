import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author RayTing
 * @date 2019-09-06 9:27
 * 关于Java反射机制测试
 * 反射机制指的是程序在运行时能够获取自身的信息，
 * java中，只要给定类的名字，就可以通过反射机制来获得类的所有信息。
 */
public class Reflection {
    public static void main(String[] args) throws ClassNotFoundException {

        Class class1=Class.forName("Person");//加载类

        Person person = new Person();//新实例
        Class class2 = person.getClass();

        System.out.println("class1==class2:"+(class1==class2));

        System.out.println();
        System.out.println("class1.getName() = " + class1.getName());
        System.out.println("1.所有public属性：----------------------------");
        Field[] fields = class1.getFields(); //获取所有public属性
        for (int i = 0; i < fields.length; i++) {
            System.out.println(fields[i]);
        }


        System.out.println("\n2.所有属性：----------------------------");
        Field[] declaredFields = class1.getDeclaredFields();//获取所有属性
        for (int i = 0; i < declaredFields.length; i++) {
            System.out.println(declaredFields[i]);
        }


        System.out.println("\n3.按名称获取属性：----------------------------");
        try {
            Field favorite = class1.getField("favorite"); //根据名称获取public属性
            System.out.println("class1.getField()"+favorite);

            Field name = class1.getDeclaredField("name"); //根据名称获取所有属性
            System.out.println("class1.getDeclaredField()"+name);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }


        System.out.println("\n4.获取所有public方法(包括继承的，实现接口的)：----------------------------");
        Method[] methods = class1.getMethods();
        System.out.println(methods.length);
        for (int i = 0; i < methods.length; i++) {
            System.out.println(methods[i]);
        }
        System.out.println("\n5.获取获取所有方法(包括实现接口，但不包括继承的)：----------------------------");
        Method[] declaredMethods = class1.getDeclaredMethods();
        for (int i = 0; i < declaredMethods.length; i++) {
            System.out.println(declaredMethods[i]);

        }

        try {
            System.out.println("\n6.根据名称获取所有public方法(包括继承的，实现接口的)：----------------------------");
            Method getAge= class1.getMethod("getAge"); // 返回getName这个方法，如果没有参数，就默认为null
            System.out.println(getAge);

            System.out.println("\n7.根据名称获取获取所有方法(包括实现接口，但不包括继承的)：----------------------------");
            Method getInfo = class1.getDeclaredMethod("getInfo", null); // 返回getName这个方法，如果没有参数，就默认为null
            System.out.println(getInfo);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        System.out.println();
        try {
            System.out.println("\n8.获取实例newInstance：----------------------------");
            Person person1 = (Person) class1.newInstance();//获取实例
            Person person2 = (Person) class2.newInstance();
            System.out.println(person.hashCode());
            System.out.println(person1.hashCode());
            System.out.println(person2.hashCode());

            System.out.println("\n8.使用构造器方式newInstance：----------------------------");
            Constructor constructor = class1.getConstructor(String.class, int.class);
            Person jack = (Person)constructor.newInstance("jack", 18);
            jack.speak();


            System.out.println("\n8.通过反射调用普通方法：----------------------------");
            Method setAge = class1.getDeclaredMethod("setAge", int.class);
            setAge.invoke(person1,12); //调用person1的setAge方法，参数12
            person1.speak();

            System.out.println("\n8.通过反射设置属性：----------------------------");
            Field name = class1.getDeclaredField("name");
            name.setAccessible(true);// 设置属性可以直接的进行访问
            name.set(person2,"tom");//把person2的 name 设置成 “tom”
            person2.speak();


        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }


    }
}

/**
 * 使用的测试类
 */
class Person{
    private String name;//姓名
    private int age;//年龄
    public String favorite;

    private String getInfo(){
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public Person() {
        System.out.println("无参构造");
    }

    public Person(String name, int age) {
        System.out.println("有参构造");
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void speak(){
        System.out.println("My name is "+this.name+". I'm "+this.age+" years old");
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
/**
 * 运行结果：

 无参构造
 class1==class2:true

 class1.getName() = Person
 1.所有public属性：----------------------------
 public java.lang.String Person.favorite

 2.所有属性：----------------------------
 private java.lang.String Person.name
 private int Person.age
 public java.lang.String Person.favorite

 3.按名称获取属性：----------------------------
 class1.getField()public java.lang.String Person.favorite
 class1.getDeclaredField()private java.lang.String Person.name

 4.获取所有public方法(包括继承的，实现接口的)：----------------------------
 14
 public java.lang.String Person.toString()
 public java.lang.String Person.getName()
 public void Person.setName(java.lang.String)
 public int Person.getAge()
 public void Person.setAge(int)
 public void Person.speak()
 public final void java.lang.Object.wait() throws java.lang.InterruptedException
 public final void java.lang.Object.wait(long,int) throws java.lang.InterruptedException
 public final native void java.lang.Object.wait(long) throws java.lang.InterruptedException
 public boolean java.lang.Object.equals(java.lang.Object)
 public native int java.lang.Object.hashCode()
 public final native java.lang.Class java.lang.Object.getClass()
 public final native void java.lang.Object.notify()
 public final native void java.lang.Object.notifyAll()

 5.获取获取所有方法(包括实现接口，但不包括继承的)：----------------------------
 public java.lang.String Person.toString()
 public java.lang.String Person.getName()
 public void Person.setName(java.lang.String)
 public int Person.getAge()
 private java.lang.String Person.getInfo()
 public void Person.setAge(int)
 public void Person.speak()

 6.根据名称获取所有public方法(包括继承的，实现接口的)：----------------------------
 public int Person.getAge()

 7.根据名称获取获取所有方法(包括实现接口，但不包括继承的)：----------------------------
 private java.lang.String Person.getInfo()


 8.获取实例newInstance：----------------------------
 无参构造
 无参构造
 1173230247
 856419764
 621009875

 8.使用构造器方式newInstance：----------------------------
 有参构造
 My name is jack. I'm 18 years old

 8.通过反射调用普通方法：----------------------------
 My name is null. I'm 12 years old

 8.通过反射设置属性：----------------------------
 My name is tom. I'm 0 years old

 * */