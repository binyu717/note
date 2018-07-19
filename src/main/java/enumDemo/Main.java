package enumDemo;

/**
 * @author bin.yu
 * @create 2018-01-05 15:44
 **/
public class Main {

    public static void main(String[] args) {
        String doctor = EduEnum.valueOf("doctor").getDesc();
        System.out.println(doctor);
    }
}
