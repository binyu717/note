package guavaDemo;

import com.google.common.base.Functions;
import com.google.common.collect.ComparisonChain;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 混合demo
 *
 * @author bin.yu
 * @create 2018-07-29 9:29
 **/
public class MixedDemo {

    public static void main(String[] args) {
        People hua =new People();
        hua.setName("小花");
        hua.setSex("female");
        hua.setAge("18");
        People ming = new People();
        ming.setName("小明");
        ming.setSex("male");
        ming.setAge("18");
//        System.out.println(hua.compareTo(ming));

        Map<String, Integer> map = new HashMap<String, Integer>() {
            //构造一个测试用Map集合
            {
                put("love", 1);
                put("miss", 2);
            }
        };
        /**
         * forMap
         */
         com.google.common.base.Function<String, Integer> function = Functions.forMap(map);
        //调用apply方法，可以通过key获取相应的value
        System.out.println(function.apply("love"));
    }

    /**
     * ComparisonChain 提供比较的方法 1>,0=,-1<
      */
   static class People{
        private String name;
        private String sex;
        private String age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public int compareTo(People girl) {
            return ComparisonChain.start()
                    .compare(name, girl.name)
                    .compare(sex, girl.sex)
                    .compare(age, girl.age)
                    .result();
        }
    }
    /**
     * 函数式接口 去重
     * @param keyExtractor
     * @param <T>
     * @return
     */
    private <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        ConcurrentHashMap<Object, Boolean> map = new ConcurrentHashMap<>(16);
        return t -> map.putIfAbsent(keyExtractor.apply(t),Boolean.TRUE) == null;
    }

}
