package designPattern.observer;

/**
 * 小强-观察者
 *
 * @author bin.yu
 * @create 2019-03-05 19:29
 **/
public class Student implements StudentImpl {

    private String name;

    public Student(String name) {
        this.name = name;
    }

    @Override
    public void getMessage(String notice) {
        System.out.println(name+"接收到老师的讲课内容：" + notice);
    }
}
