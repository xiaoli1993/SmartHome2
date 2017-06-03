package com.heiman.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @Author : 肖力
 * @Time :  2017/5/22 11:27
 * @Description :
 * @Modify record :
 */

public class ClassUtils {
    /**
     * 获取该类的信息
     * 比如他的类的类名和函数
     *
     * @param obj
     */
    public static void getClassInfo(Object obj) {
        //获取obj的类类型
        Class c1 = obj.getClass();
        System.out.println("类的类型为" + c1.getName());
        System.out.println("类的类型为" + c1.getSimpleName());
        //获取类的类方法包括其父类的方法
        Method[] methods = c1.getMethods();
        System.out.println("该类的方法为");
        int c = 0;
        for (int i = 0; i < methods.length; i++) {
            c++;
            Class returntype = methods[i].getReturnType();//获取该类的返回值
            System.out.print(returntype.getSimpleName() + " ");//打印返回值
            System.out.print(methods[i].getName() + "(");//打印方法名
            Class[] prams = methods[i].getParameterTypes();//获取参数的方法
            for (int j = 0; j < prams.length; j++) {
                System.out.print(prams[j].getSimpleName() + "  ");//打印参数名
            }
            System.out.println(")");
        }
        System.out.println("利用getDeclaredMethods");
        //获取类的本类方法，不包含父类的方法可以获取他的类里面的所有方法
        Method[] methods1 = c1.getDeclaredMethods();
        System.out.println("该类的方法为");
        int count = 0;
        for (int i = 0; i < methods1.length; i++) {
            count++;
            Class returntype = methods1[i].getReturnType();//获取该类的返回值
            System.out.print(returntype.getSimpleName() + " ");//打印返回值
            System.out.print(methods1[i].getName() + "(");//打印方法名
            Class[] prams = methods1[i].getParameterTypes();//获取参数的方法
            for (int j = 0; j < prams.length; j++) {
                System.out.print(prams[j].getSimpleName() + "  ");//打印参数名
            }
            System.out.println(")");
        }
        System.out.println(c + "  " + count);
    }

    /**
     * @param obj
     */
    public static void getName(Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            System.out.println("变量名：" + fields[i].getName());
        }
    }

    /**
     * 获取构造方法
     *
     * @param obj
     */
    public static void getConMethod(Object obj) {
        Class c = obj.getClass();
        //获取类的构造方法
        Constructor[] declaredConstructors = c.getDeclaredConstructors();
        for (Constructor constructor : declaredConstructors) {
            System.out.print(constructor.getName() + "(");
            Class[] parameterTypes = constructor.getParameterTypes();
            for (Class class1 : parameterTypes) {
                System.out.print(class1.getName());
            }
        }
        System.out.println(")");
    }

    public static void getClassFields(Object obj) {
        Class c = obj.getClass();
        //获取该类的成员变量
        Field[] declaredFields = c.getDeclaredFields();
        for (Field field : declaredFields) {
            //获取成员变量的类型
            System.out.print(field.getType().getName() + " ");
            //获取成员变量的名称
            System.out.println(field.getName());
        }
    }

    public static void runClassMethod(Object obj) throws Exception {
        Class c1 = obj.getClass();//获取类类型
        /*
         * c1.getDeclaredMethod("syso", parameterTypes)
         * 第一个是方法名，第二个为参数
         */
        Method m1 = c1.getDeclaredMethod("de");
        m1.setAccessible(true);//设置这个方法的权限为public但是不会改变该方法的原有的权限
        /*
         * invoke(obj)对于指定的方法执行第一参数为这个对象，第二个为函数参数
         */
        System.out.println(m1.invoke(obj));
    }
}
