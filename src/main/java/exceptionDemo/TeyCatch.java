package exceptionDemo;

/**
 * @author bin.yu
 * @create 2017-12-15 12:31
 **/
public class TeyCatch {

    public static void main(String[] args) {
        try {
            System.out.println("try");
            int i = 1/0;
        }catch (Exception e){
            e.printStackTrace();

            System.out.println("catch");
        }
        System.out.println("last");
    }
}
