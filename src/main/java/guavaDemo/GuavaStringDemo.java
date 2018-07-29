package guavaDemo;

import com.google.common.base.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Guava将常用的字符串处理设计了4种角色:连接器、拆分器、匹配器、格式器,
 * @author bin.yu
 * @create 2018-07-28 20:43
 **/
public class GuavaStringDemo {

    public static void main(String[] args) {

        // Joiner 连接器
//        testJoiner();
        // 拆分器
//        testSplitter();
        // 字符匹配
//        testCharMatcher();
        // 变量格式器

//        testCaseFormat();
        //Strings
        testStrings();



    }

    /**
     * on:制定拼接符号
     * skipNulls()：忽略NULL,返回一个新的Joiner实例
     * useForNull(“Hello”)：NULL的地方都用字符串”Hello”来代替
     */
    private static void testJoiner(){
        // 字符拼接
        Joiner joiner = Joiner.on(";").skipNulls();
        String result1 = joiner.join("1", null, "2", "3");
        System.out.println(result1);
        String result2 = Joiner.on(",").join(Arrays.asList(4, 5, 6));
        System.out.println(result2);

        // 合并 Map<String,String>
        Map<String,String> map = new HashMap<>();
        map.put("a", "b");
        map.put("c", "d");
        String mapJoinResult = Joiner.on(",").withKeyValueSeparator("=").join(map);
        System.out.println(mapJoinResult);

    }

    /**
     * on():指定分隔符来分割字符串
     * onPattern():指定符合正则表达式的分隔符
     * limit():当分割的子字符串达到了limit个时则停止分割?????
     * fixedLength():根据长度来拆分字符串
     * trimResults():去掉子串中的空格
     * omitEmptyStrings():去掉空的子串
     * withKeyValueSeparator():要分割的字符串中key和value间的分隔符,分割后的子串中key和value间的分隔符默认是=
     */
    private static void testSplitter(){
        List<String> list1 = Splitter.on(';')
                .trimResults()//移除结果字符串的前导空白和尾部空白
                .omitEmptyStrings()//从结果中自动忽略空字符串
                .splitToList("foo;baraa;; ; qux;aaaa;");
        System.out.println(list1);

         List<String> list2 = Splitter.fixedLength(3)//限制拆分出的字符串数量
                .splitToList("这使得splitter实例都是线程安全的");
        System.out.println(list2);

        Iterable<String> regexp = Splitter.onPattern("[A-Z]").trimResults().split("heLloWord!");
        System.out.println(regexp);
        Map<String, String> map = Splitter.on(";")
                .withKeyValueSeparator(":")
                .split("a:1;b:2;c:3");
        System.out.println(map);
    }

    /**
     * CharMatcher 还有其他内部类，不一一举例
     */
    private static void testCharMatcher(){
        // 只保留数字字符
        String theDigits = CharMatcher.DIGIT.retainFrom("今天是2016年9月16日");
        System.out.println(theDigits);
        //去除两端的空格,并把中间的连续空格替换成单个空格
        String spaced = CharMatcher.WHITESPACE.trimAndCollapseFrom(" 一个 CharMatcher 代表 一类 字符 ", ' ');
        System.out.println(spaced);
        //用*号替换所有数字
        String noDigits = CharMatcher.JAVA_DIGIT.replaceFrom("我的手机号是13400001234", "*");
        System.out.println(noDigits);
        // 只保留数字和小写字母
        String lowerAndDigit = CharMatcher.JAVA_DIGIT.or(CharMatcher.JAVA_LOWER_CASE).retainFrom(" Aaliyunzixun@xxx.com");
        System.out.println(lowerAndDigit);
        String letterOrDigit = CharMatcher.JAVA_LETTER_OR_DIGIT.retainFrom("Aaliyunzixun@xxx.com");
        System.out.println(letterOrDigit);

    }

    /**
     * 变量格式器
     */
    private static void testCaseFormat(){
        //userName -> user-name
        String lowerHyphen = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, "userName");
        System.out.println(lowerHyphen);
        //userName -> user_name
        String lowerUnderscore = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, "userName");
        System.out.println(lowerUnderscore);
        //userName -> UserName
        String upperCamel = CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, "userName");
        System.out.println(upperCamel);
        //userName -> USER_NAME
        String upperUnderscore = CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, "userName");
        System.out.println(upperUnderscore);
     }

    private static void testStrings(){
        // 判断字符串是否为空
        String str = "";
        boolean flag = Strings.isNullOrEmpty(str);
        System.out.println(flag);
        // 获得两个字符串相同的前缀commonPrefix或者后缀commonSuffix
        //Strings.commonPrefix(a,b) demo
        String a = "com.jd.coo.Hello";
        String b = "com.jd.coo.Hi";
        String ourCommonPrefix = Strings.commonPrefix(a,b);
        System.out.println("a,b common prefix is " + ourCommonPrefix);
        // padStart和padEnd方法来补全字符串
        int minLength = 4;
        String padEndResult = Strings.padEnd("123", minLength, '0');
        System.out.println("padEndResult is " + padEndResult);


    }

    private static void optionalDemo(){

    }


}
