package StreamDemo;

import com.alibaba.fastjson.JSONArray;
import commondata.People;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author bin.yu
 * @create 2017-12-21 9:06
 **/
public class StreamDemo {

    private static void initData(List<String> strList,List<Integer> integerList,List<People> peopleList) {

        integerList.add(1);
        integerList.add(2);
        integerList.add(3);
        integerList.add(4);
        integerList.add(5);

        strList.add("a");
        strList.add("b");
        strList.add("c");
        strList.add("d");
        strList.add("e");
        strList.add("f");

        peopleList.add(new People("张三",20));
        peopleList.add(new People("李四",21));
        peopleList.add(new People("王五",23));
        peopleList.add(new People("赵六",24));

    }

    public static void main(String[] args) {
        List<String> strList = new ArrayList();
        List<Integer> integerList = new ArrayList<>();
        List<People> peopleList = new ArrayList();
        initData(strList,integerList,peopleList);
//        System.out.println("==============Collectors=============");
//        collectorsDemo(strList);
//        System.out.println("==============Reduce=============");
//        reduceDemo(integerList,peopleList);

        List<People> collect = peopleList.stream().filter(i -> i.getName().equals("张三")).collect(Collectors.toList());
        System.out.println(JSONArray.toJSONString(collect));
        collect.stream().forEach(i->i.setAge(55));
        System.out.println(JSONArray.toJSONString(collect));
        List<People> collect1 = peopleList.stream().filter(i -> i.getName().equals("张三")).collect(Collectors.toList());
        List<People> copy = new ArrayList(collect1);
        Collections.copy(copy,collect1);

        copy.stream().forEach(i->i.setAge(66));
        System.out.println(JSONArray.toJSONString(collect1));// 期望55
//        System.out.println(JSONArray.toJSONString(copy));// 期望55


    }

    private static void collectorsDemo(List list){
        String str1 = (String)list.stream().collect(Collectors.joining(" "));
        System.out.println(str1);
        String str2 = (String) list.stream().collect(Collectors.joining("|","start "," end"));
        System.out.println(str2);
    }

    private static void reduceDemo(List<Integer> integerList,List<People> peopleList){
        //  取出对象里的属性操作，计算年龄之和
        Integer collect = peopleList.stream().collect(Collectors.reducing(0, People::getAge, (i, j) -> i + j));
        System.out.println(collect);
        // 一个参数
        Optional<Integer> optional = integerList.stream().reduce((a, b) -> a + b);
        System.out.println("optioal: " + optional.get());
        // 对值进行其他处理，求奇数和
        Optional<Integer> optional1 = integerList.stream().reduce((a, b) -> {
            if (b % 2 != 0) {
                return a + b;
            } else {
                return a;
            }
        });
         System.out.println("optional1: "+optional1.get());
        // 两个参数，第一个是初始值,
        Integer integer = integerList.stream().reduce(2, (a, b) -> a * b);
        System.out.println("integer: "+integer);

        //
        System.out.println(integerList.parallelStream().reduce((a, b) -> a + b).get());


    }
}
