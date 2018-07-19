package equalsDemo;

import commondata.People;

/**
 * @author bin.yu
 * @create 2017-11-30 11:19
 **/
public class MainTest {

    public static void main(String[] args) {
        People p1 = new People("zhang", 1);
        People p2 = new People("zhang",1);

//        System.out.println(p1.equals(p2));

        String str = "     ";
        System.out.println(str.trim().isEmpty());
    }
}
