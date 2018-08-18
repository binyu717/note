package guavaDemo;

import com.google.common.base.Function;
import com.google.common.collect.*;

import java.util.*;
import java.util.stream.Collectors;

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
//        mapDemo();
        biMapDemo();
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
        List<String> keys = Lists.newArrayList("a","d", "d", "c");
        //输出：{a=A, b=B, c=C}
        System.out.println("guava方式list转map" + Maps.toMap(keys, function));

        // remark:stream方式list不能有重复值，否则异常，并且生成的map不会按list的顺序返回，可能自带默认排序
        Map<String, String> uppercaseMap = keys.stream().collect(Collectors.toMap(i -> i, t -> t.toUpperCase()));
        System.out.println("stream方式list转map" + uppercaseMap);

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

    /**
     * BiMap是Guava提供的一种key和value双向关联的数据结构,它允许我们可以通过特定的value获取key值
     */
    private static void biMapDemo() {
        HashBiMap<String, String> biMap = HashBiMap.create();
        biMap.put("one", "bin");
        try {
            /**
             * 通过put方法存入相同的value值，会抛出异常
             * java.lang.IllegalArgumentException: value already present
             */
            biMap.put("two", "bin");
        } catch (IllegalArgumentException e) {
            System.out.println("------------------catch:"+e);
            /**
             * forcePut()：相同value值允许传入,相当于替换key
             */
            biMap.forcePut("three", "bin");
            biMap.forcePut("four", "opt");
            biMap.forcePut("five", "urs");
        }
        System.out.println(biMap.get("one"));//null
        System.out.println(biMap.get("two"));//null
        System.out.println(biMap.get("three"));//bin
        System.out.println(biMap.get("four"));//bin
        System.out.println(biMap);//{three=bin, four=opt, five=urs}
        /**
         * inverse()：进行键值对的反转，返回BiMap的一种双向映射关系,两者相互关联
         */
        System.out.println(biMap.inverse());//{bin=three, opt=four, urs=five}


    }

}
