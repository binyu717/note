package designPattern.proxy;

/**
 * 静态代理
 * 1.可以做到在不修改目标对象的功能前提下,对目标功能扩展.
 * 2.缺点:
 * 因为代理对象需要与目标对象实现一样的接口,所以会有很多代理类,类太多.同时,一旦接口增加方法,目标对象与代理对象都要维护.
 *
 * @author bin.yu
 * @create 2018-08-15 17:18
 **/
public class UserDaoProxy implements UserDao {

    private UserDao target;

    public UserDaoProxy(UserDao target){
        this.target=target;
    }

    @Override
    public void save() {
        System.out.println("before");
        target.save();
        System.out.println("after");
    }

    public static void main(String[] args) {
        //目标对象
        UserDao target = new UserDaoImpl();

        //代理对象,把目标对象传给代理对象,建立代理关系
        UserDaoProxy proxy = new UserDaoProxy(target);

        proxy.save();//执行的是代理的方法
    }
}
