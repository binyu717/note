package designPattern.decorator;

/**
 * 装饰者模式
 * 对已有的业务逻辑进一步的封装，使其增加额外的功能，如Java中的IO流就使用了装饰者模式，用户在使用的时候，可以任意组装，达到自己想要的效果。
 * 例子：
 * @author bin.yu
 * @create 2019-03-05 19:46
 **/
public class Test {

    public static void main(String[] args) {
        Food bread = new Bread(new Cream(new Food("培根")));
        System.out.println(bread.make());
    }

}
