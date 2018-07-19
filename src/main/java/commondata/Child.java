package commondata;

/**
 * @author bin.yu
 * @create 2018-01-23 20:42
 **/
class Base{
      void test(){
        System.out.println("Base.test()");
    }
}
public class Child extends Base{
    @Override
      void test(){
        System.out.println("child.test()");
    }

    public static void main(String[] args) {
        Base base = new Child();
        Child child = new Child();
        base.test();
        child.test();

    }
}



