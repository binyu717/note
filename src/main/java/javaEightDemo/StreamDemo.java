package javaEightDemo;

import commondata.People;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author bin.yu
 * @create 2017-12-21 9:06
 **/
public class StreamDemo {

    public static void main(String[] args) {
        StreamDemo streamDemo = new StreamDemo();
        List list = new ArrayList();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.add("e");
        list.add("f");

        List<People> peoples = new ArrayList();
        peoples.add(new People("张三",20));
        peoples.add(new People("李四",21));
        peoples.add(new People("王五",23));
        peoples.add(new People("赵六",24));

//        System.out.println("==============Collectors=============");
//        streamDemo.collectorsDemo(list);
        System.out.println("==============Reduce=============");
        reduceDemo(peoples);
//        List<People> collect = peoples.stream().collect(Collectors.reducing(0,People::getAge,(i,j)->i+j));
        List<People> collect = peoples.stream().filter(i->i.getAge()==2).collect(Collectors.toList());
    }

    private void collectorsDemo(List list){
        String str1 = (String)list.stream().collect(Collectors.joining(" "));
        System.out.println(str1);
        String str2 = (String) list.stream().collect(Collectors.joining("|","start "," end"));
        System.out.println(str2);
    }

    private static void reduceDemo(List list){
        List<People> peoples = new ArrayList();
        peoples.add(new People("张三",20));
        peoples.add(new People("李四",21));
        peoples.add(new People("王五",23));
        peoples.add(new People("赵六",24));
        peoples.stream().forEach(i-> System.out.println(i.getName()));

//        list.stream().forEach(i-> System.out.println(i.));
        List<People> collect = peoples.stream().filter(i->i.getAge()==2).collect(Collectors.toList());


        String string = String.format("id in (%s)", new String("'a','b'")).toString();
        System.out.println(string);

        BigDecimal bigDecimal = new BigDecimal(1);
        BigDecimal add = bigDecimal.add(new BigDecimal(1)).add(null);
        System.out.println(add);

    }
}
