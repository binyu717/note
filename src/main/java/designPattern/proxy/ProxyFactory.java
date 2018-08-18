package designPattern.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 创建动态代理对象
 * 动态代理不需要实现接口,但是需要指定接口类型
 * @author bin.yu
 * @create 2018-08-15 18:08
 **/
public class ProxyFactory {

    //维护一个目标对象
    private Object target;

    public ProxyFactory(Object target){
        this.target=target;
    }

    //给目标对象生成代理对象
    public Object getProxyInstance(){
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        System.out.println("before");
                        //执行目标对象方法
                        Object returnValue = method.invoke(target, args);
                        System.out.println("after");
                        return returnValue;
                    }
                }
        );
    }

    public static void main(String[] args) {
        // 目标对象
        UserDao target = new UserDaoImpl();
        // 【原始的类型 class designPattern.proxy.UserDaoImpl】
        System.out.println(target.getClass());

        // 给目标对象，创建代理对象
        UserDao proxy = (UserDao) new ProxyFactory(target).getProxyInstance();
        // class com.sun.proxy.$Proxy0   内存中动态生成的代理对象
        System.out.println(proxy.getClass());

        // 执行方法   【代理对象】
        proxy.save();
    }
}
