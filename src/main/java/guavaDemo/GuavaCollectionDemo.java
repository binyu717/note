package guavaDemo;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.*;

/**
 * @author bin.yu
 * @create 2018-07-28 21:34
 **/
public class GuavaCollectionDemo {

    public static void main(String[] args) {
        // lists
//        listsDemo();
        // set集合操作：交集、差集、并集
//        setsDemo();
        mapDemo();
    }

    private static void listsDemo() {
        List<String> list = Lists.newArrayList("5", "2", "3");
        List<String> reverse = Lists.reverse(list);// 反转顺序
        System.out.println(reverse);
        /**
         * asList：返回一个不可变的List
         * 其中包含指定的第一个元素和附加的元素数组组成
         * 修改这个数组将反映到返回的List上,addAll方法不会同步修改
         */
        String str = "docker";
        String[] strs = {"java","mysql"};
        List<String> lists = Lists.asList(str, strs);
        System.out.println(lists);// [docker, java, mysql]
        strs[1] = "oracle";
        System.out.println(lists);// [docker, java, oracle]

        /**
         * transform：根据传进来的function对fromList进行相应的处理
         * 并将处理得到的结果存入到新的list对象中返回
         */
        List<String> newList = Lists.transform(lists, new Function<String, String>() {
            @Override
            public String apply(String input) {
                //这里简单的对集合中的元素转换为大写
                return input.toUpperCase();
            }
        });
        // lambda 表达式写法
        List<String> transform = Lists.transform(lists, t -> t.toUpperCase());
        System.out.println(newList);// [DOCKER, JAVA, ORACLE]
        System.out.println(transform);// [DOCKER, JAVA, ORACLE]
//       根据size传入的List进行切割，切割成符合要求的小的List,并将这些小的List存入一个新的List对象中返回
        List<List<String>> partition = Lists.partition(list, 2);
        System.out.println(partition);

        /**
         * charactersOf：将传进来的String或者CharSequence分割为单个的字符
         * 并存入到一个ImmutableList对象中返回
         * ImmutableList：一个高性能、不可变的、随机访问列表的实现
         */
        ImmutableList<Character> characters = Lists.charactersOf("you");
        System.out.println(characters);//[y, o, u]
        // 指定确定大小
        ArrayList<String> listWithCapacity = Lists.newArrayListWithCapacity(2);
        // 不确定大小，返回容量5L + arraySize + (arraySize / 10)
        ArrayList<String> listWithexpected  = Lists.newArrayListWithExpectedSize(10);


    }

    private static void setsDemo(){
        Set<Integer> set1= Sets.newHashSet(1,2,3,4,5);
        Set<Integer> set2=Sets.newHashSet(3,4,5,6);
        Sets.SetView<Integer> inter=Sets.intersection(set1,set2); //交集
        System.out.println(inter);
        Sets.SetView<Integer> diff=Sets.difference(set1,set2); //差集,在A中不在B中
        System.out.println(diff);
        Sets.SetView<Integer> union=Sets.union(set1,set2); //并集
        System.out.println(union);
    }

    private static void mapDemo() {
        // difference：返回两个给定map之间的差异。
        Map<String,String> map1 = new HashMap();
        Map<String,String> map2 = new HashMap();
        Map<String,String> map3 = new HashMap();
        Map<String,String> map4 = new HashMap();
        map1.put("a", "1");
        map2.put("b", "2");
        map3.put("c", "3");
        map4.put("a", "1");
        map4.put("b", "1");
        System.out.println(Maps.difference(map1, map2));
        System.out.println(Maps.difference(map1, map3));
        System.out.println(Maps.difference(map2, map4));

        /**
         * asMap：返回一个活动的map
         * 键值为给定的set中的值
         * value为通过给定Function计算后的值。
         */
        Set<String> set = Sets.newHashSet("f", "b", "c");
        //Function：简单的对元素做大写转换，下面示例多次使用
        Function<String, String> function = new Function<String, String>() {
            @Override
            public String apply(String input) {
                return input.toUpperCase();
            }
        };
        System.out.println(Maps.asMap(set, function)); // {b=B, c=C, f=F}
        // lambda写法
        System.out.println(Maps.asMap(set, t->t.toUpperCase()));

        /**
         * toMap：返回一个不可变的ImmutableMap实例
         * 其键值为给定keys中去除重复值后的值
         * 其值为键被计算了valueFunction后的值
         */
        List<String> keys = Lists.newArrayList("a", "b", "c", "a");
        //输出：{a=A, b=B, c=C}
        System.out.println(Maps.toMap(keys, function));

        /**
         * uniqueIndex：返回一个不可变的ImmutableMap实例，
         * 其value值为按照给定顺序的给定的values值
         * 键值为相应的值经过给定Function计算后的值
         */
        List<String> values = Lists.newArrayList("a", "b", "c", "d");
        /**
         * 注：这里的value值不可重复，重复的话在转换后会抛出异常：
         * IllegalArgumentException: Multiple entries with same key
         */
        System.out.println(Maps.uniqueIndex(values, function)); // {A=a, B=b, C=c, D=d}
    }
}
