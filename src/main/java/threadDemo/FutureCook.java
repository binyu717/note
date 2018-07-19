package threadDemo;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author bin.yu
 * @create 2018-05-16 15:32
 **/
public class FutureCook {


    public static void main(String[] args) throws InterruptedException, ExecutionException {
        long startTime = System.currentTimeMillis();
        // 第一步：网购厨具
        Callable<Chuju> onlineShopping = new Callable<Chuju>(){
            @Override
            public Chuju call() throws Exception {

                System.out.println("第一步：下单");
                System.out.println("等待送货");
                Thread.sleep(5000);//模拟送货时间
                System.out.println("快递送到");
                return new Chuju();
            }
        };

        // lambda 写法
//        Callable<Chuju> o =()->{
//            System.out.println("第一步：下单");
//            System.out.println("等待送货");
//            Thread.sleep(5000);//模拟送货时间
//            System.out.println("快递送到");
//            return new Chuju();
//         };

//        new Thread(() -> System.out.println("asd")).start();


        FutureTask<Chuju> task = new FutureTask<Chuju>(onlineShopping);
        new Thread(task).start();

        // 第二步：超市购买食材
        Thread.sleep(2000);
        Shicai shicai = new Shicai();
        System.out.println("食材到位");

        // 第三部：烹饪

        if (!task.isDone()) {
            System.out.println("厨具还未到，可以wait,也可cancel");
        }

        Chuju chuju = task.get();
        System.out.println("厨具到位，可烹饪");

        cook(chuju,shicai);

        System.out.println("总共用时："+(System.currentTimeMillis()-startTime)+"ms");


    }

    //  用厨具烹饪食材
    static void cook(Chuju chuju, Shicai shicai) {}

    // 厨具类
    static class Chuju {}

    // 食材类
    static class Shicai {}
}
