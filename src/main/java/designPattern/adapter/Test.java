package designPattern.adapter;

/**
 * 适配器模式:将一个类的接口转换成客户希望的另外一个接口。A d a p t e r 模式使得原本由于接口不兼容而不能一起工作的那些类可以一起工作。
 * 又分 类适配器模式、对象的适配器模式、接口的适配器模式
 * @author bin.yu
 * @create 2019-03-06 20:08
 **/
public class Test {

    public static void main(String[] args) {
        Transformer transformer = new Transformer();
        Phone phone = new Phone(transformer);
        phone.charge();
    }
}
