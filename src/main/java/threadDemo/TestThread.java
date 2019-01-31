package threadDemo;

/**
 * @author bin.yu
 * @create 2018-10-15 9:02
 **/
public class TestThread {

        public static void main(String[] args) {
            System.out.println("主线程ID是：" + Thread.currentThread().getId());
            MyThread my = new MyThread("线程1");
            my.start();

            MyThread my2 = new MyThread("线程2") ;
            /**
             * 这里直接调用my2的run()方法。
             */
            my2.run() ;
        }
    }

    class MyThread extends Thread {
        private String name;
        public MyThread(String name) {
            this.name = name;
        }
        @Override
        public void run() {
            System.out.println("名字：" + name + "的线程ID是="
                    + Thread.currentThread().getId());
        }

}
