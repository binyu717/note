package socket;

/**
 * @author bin.yu
 * @create 2018-07-08 22:14
 **/
public class Main{

    public static void main(String[] args) {
//        new Receive().run();
//        new Send().run();

        new Thread(new Send()).start();
        new Thread(new Receive()).start();


    }
}


