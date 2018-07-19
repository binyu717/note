package basetypecast;

/**
 * @author bin.yu
 * @create 2018-01-17 11:57
 **/
public class Main {

    public static void main(String[] args) {
        String str = "4";
        long l = Long.parseLong(str);
        System.out.println(l);
        test();
    }

    private static void test(){
        String str = "ASD";
        String str1 = str;
        System.out.println("str1="+str1);
         str = str.toLowerCase();
//        System.out.println("s="+s);
        System.out.println("str1="+str1);
        System.out.println("str="+str);

    }
}
