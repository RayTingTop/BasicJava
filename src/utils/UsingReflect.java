package utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @author RayTing
 * @date 2019-01-06 14:33
 * 使用反射处理sql
 */
public class UsingReflect {
    /**
     * 根据对象获取保存对象的sql语句
     * @param object
     * @return
     */
    public static String  getSaveSql(Object object){
        /**
         * insert into Object(field1,field2,field3) values(value1,value2,value3);
         */
        StringBuffer sql = new StringBuffer("insert into ");//sql语句
        Class objClass = object.getClass();

        Method[] methods = objClass.getMethods();//获取方法
//        Field[] fields = objClass.getFields();//获取字段，为了保证sql中字段和值一一相对，使用方法名获取属性更好

        String className=objClass.getName();//全类名
        String tableName = className.substring(className.lastIndexOf(".") + 1, className.length());//剪切类名作为数据库表名字
        sql.append(tableName+"("); //拼接

        List<String> fNameList = new ArrayList<String >();//字段名列表
        List valueList = new ArrayList();//值列表

        for (Method method : methods) { //循环方法
            String mName = method.getName();//方法名
            if (mName.startsWith("get")&&!mName.startsWith("getClass")){ //当方法是get方法，并且非getClass方法时
                String fieldName = mName.substring(3, mName.length());//剪切出字段名

                if (!fieldName.equalsIgnoreCase("Id")) { //这里跳过Id字段

                    fNameList.add(fieldName);//添加到字段列表
                    try {
                        Object value = method.invoke(object, null);//执行这个get方法

                        if (value instanceof String) { //如果是字符串
                            valueList.add("\"" + value + "\""); //转义并拼接到双引号中
                        } else {
                            valueList.add(value);//非字符串直接拼接
                        }

                        System.out.println("-----解析对象属性[" + fieldName + "：" + value + "]");
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
            
        }
        //处理字段名
        for (String fName : fNameList) {
            sql.append(fName+",");
        }
        sql.deleteCharAt(sql.length()-1);//删除末位的逗号
        sql.append(") values(");//拼上values的开头


        //处理值
        for (Object value : valueList) {
            sql.append(value+",");
        }
        sql.deleteCharAt(sql.length()-1);//删除末位的逗号
        sql.append(");");//拼上末位

        return sql.toString();
    }


    /**
     * 将结果封装成Object
     * @param className
     * @param rs
     * @return
     */
    public static Object resultToObject(String className, ResultSet rs){
        Class objClass = null;
        Object obj= null;//要封装的对象
        try {
            objClass = Class.forName(className);//加载
            Method[] methods = objClass.getMethods();//获取方法

            while (rs.next()){ //取rs
                obj = objClass.newInstance();//新实例
                for (Method method : methods) {
                    String mName = method.getName();//方法名

                    if (mName.startsWith("set")){//set方法

                        String fieldName = mName.substring(3,mName.length());
                        Class[] parTypes = method.getParameterTypes();//获取参数的类型

                        if (parTypes[0]==String.class){
                            // 如果参数为String类型，则从结果集中按照列名取得对应的值，并且执行改set方法
                            method.invoke(obj, rs.getString(fieldName)); //
                        }

                        if (parTypes[0]==int.class){
                            method.invoke(obj,rs.getInt(fieldName));
                        }
                        System.out.println("----封装对象["+fieldName+"："+rs.getObject(fieldName)+"]");
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        DataBaseConnection.close(null,null,rs);//关闭
        return  obj;
    }
}
