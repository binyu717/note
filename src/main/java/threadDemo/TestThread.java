package threadDemo;

/**
 * @author bin.yu
 * @create 2018-10-15 9:02
 **/
public class TestThread {

    static Demo d = new Demo();
    public static void main(String[] args) {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                d.foo();
//                System.out.println(d.x);
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                d.bar();
//                System.out.println(d.x);
            }
        });
//        try {
            t1.start();
            t2.start();
//            t1.join();
//            t2.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }



    }
}

    class Demo   {
        public Demo() {
        }

        public long x = 0;
        synchronized public void foo()  {
            for (int i = 0; i < 9000000; i++) {
                x++;
            }
            System.out.println("inner foo"+x);

        }
        synchronized public void bar()  {
            for (int i = 0; i < 9000000; i++) {
                x--;
            }
            System.out.println("inner bar"+x);
        }

}
