package designPattern.observer;

/**
 *  观察者模式；对象间一对多的依赖关系，当一个对象的状态发生改变时，所有依赖于它的对象都得到通知并被自动更新。
 *  例子：学生是观察者，老师是被观察者；老师讲课，学生同步讲课内容
 * @author bin.yu
 * @create 2019-03-05 19:40
 **/
public class Test {

    public static void main(String[] args) {
        Student meizu = new Student("魅族");
        Student xiaomi = new Student("小米");

        Teacher teacher = new Teacher("若基亚");
        teacher.addStudent(meizu);
        teacher.addStudent(xiaomi);
        teacher.teach("下课了");

    }
}
