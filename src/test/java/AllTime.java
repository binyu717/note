import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author bin.yu
 * @create 2017-12-18 21:11
 **/
public class AllTime {

    public static void main(String[] args) {


        List list = new ArrayList();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.add("aa");
        list.add("dd");

        System.out.println(list);
//        List a = (List) list.stream().filter(t -> t.equals("a")).collect(Collectors.toList());
        String str= (String)list.stream().collect(Collectors.joining("','", "'", "'"));
        System.out.println(str);


        List<People> peoples = new ArrayList();
        peoples.add(new People("张三",20));
        peoples.add(new People("李四",21));
        peoples.add(new People("王五",23));
        peoples.add(new People("赵六",24));
        List<People> collect = peoples.stream().filter(i->i.getAge()==2).collect(Collectors.toList());
        System.out.println(collect.size());

    }
}
