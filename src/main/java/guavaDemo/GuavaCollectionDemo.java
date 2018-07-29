package guavaDemo;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author bin.yu
 * @create 2018-07-28 21:34
 **/
public class GuavaCollectionDemo {

    public static void main(String[] args) {

        listsDemo();
        // 集合操作：交集、差集、并集
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
        System.out.println(newList);// [DOCKER, JAVA, ORACLE]
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



    }
}
