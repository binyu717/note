package reflectDemo;

import commondata.People;

import java.lang.reflect.Constructor;

/**
 * @author bin.yu
 * @create 2018-08-24 15:23
 **/
public class ReflectDemo {

    public static void main(String[] args) {
        try {
            Class<?> clz = Class.forName("commondata.People");
            Constructor<?> constructor = clz.getConstructor(String.class, int.class);
            People people = (People) constructor.newInstance("张三", 34);
            System.out.println(people.getName()+": "+people.getAge());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
