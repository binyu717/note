package compareDemo;

import commondata.People;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author bin.yu
 * @create 2017-12-04 9:52
 **/
public class CompareDemo {

    public static void main(String[] args) {
        List<People> list = new ArrayList();
        list.add(new People("name1",14));
        list.add(new People("name2",15));
        list.add(new People("name3",16));
//        String collect = list.stream().map(o -> o == null ? "null" : o.toString()).collect(Collectors.joining(","));
//        System.out.println(collect);
        list.stream().forEach(i->{
            System.out.println(i.getAge());
        });
        list.stream().sorted(Comparator.comparing(People::getName).reversed()).forEach(i->{
            System.out.println(i.getAge());
        });
        list.stream().forEach(i->{
            System.out.println(i.getAge());
        });


    }


}
