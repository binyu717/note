package designPattern;

/**
 * 单例模式：属于创建型模式，保证一个类仅有一个实例，并提供一个访问它的全局访问点。
 * 优点：
 *  1、在内存里只有一个实例，减少了内存的开销，尤其是频繁的创建和销毁实例（比如网站首页页面缓存）。
 *  2、避免对资源的多重占用（比如写文件操作）。
 * @author bin.yu
 * @create 2018-06-14 10:54
 **/
public class SingletonPattern {



}
/**
 * 饿汉式
 *  通过static的静态初始化方式，在该类第一次被加载的时候，就有一个SimpleSingleton的实例被创建出来了。
 *  这样就保证在第一次想要使用该对象时，他已经被初始化好了。
 *  同时，由于该实例在类被加载的时候就创建出来了，所以也避免了线程安全问题。
 *
 *  饿汉式单例，在类被加载的时候对象就会实例化。这也许会造成不必要的消耗，因为有可能这个实例根本就不会被用到。
 *  而且，如果这个类被多次加载的话也会造成多次实例化。
 */
class singleton1{
    //类内部实例化一个实例
    private static singleton1 instance = new singleton1();
    //私有的构造函数,外部无法访问
    private singleton1(){}
    //对外提供获取实例的静态方法
    public static singleton1 getInstance(){
        return instance;
    }
}

/**
 * 静态内部类式
 *  这种方式同样利用了classloder的机制来保证初始化instance时只有一个线程，它跟饿汉式不同的是（很细微的差别）：
 *  饿汉式是只要Singleton类被装载了，那么instance就会被实例化（没有达到lazy loading效果），而这种方式是Singleton类被装载了，
 *  instance不一定被初始化。因为SingletonHolder类没有被主动使用，只有显示通过调用getInstance方法时，才会显示装载SingletonHolder类，
 *  从而实例化instance。想象一下，如果实例化instance很消耗资源，我想让他延迟加载，另外一方面，我不希望在Singleton类加载时就实例化，
 *  因为我不能确保Singleton类还可能在其他的地方被主动使用从而被加载，那么这个时候实例化instance显然是不合适的。
 *  这个时候，这种方式相比饿汉式更加合理。
 */
class StaticInnerClassSingleton {
    //在静态内部类中初始化实例对象
    private static class SingletonHolder{
        private static final StaticInnerClassSingleton INSTANCE = new StaticInnerClassSingleton();

    }
    //私有的构造方法
    private StaticInnerClassSingleton(){
    }
    //对外提供获取实例的静态方法
    public static final StaticInnerClassSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

}

/**
 * 懒汉式
 *  不会提前把实例创建出来，将类对自己的实例化延迟到第一次被引用的时候。getInstance方法的作用是希望该对象在第一次
 *  被使用的时候被new出来。(线程不安全)
 */
class Singleton2 {
    //定义实例
    private static Singleton2 instance;
    //私有构造方法
    private Singleton2(){}
    //对外提供获取实例的静态方法
    public static Singleton2 getInstance() {
        //在对象被使用的时候才实例化
        if (instance == null) {
            instance = new Singleton2();
        }
        return instance;
    }
}

/**
 * 线程安全的懒汉式
 *  这种写法能够在多线程中很好的工作，而且看起来它也具备很好的延迟加载，但是，遗憾的是，他效率很低，因为99%情况下不需要同步。
 *  （因为上面的synchronized的加锁范围是整个方法，该方法的所有操作都是同步进行的，但是对于非第一次创建对象的情况，
 *   也就是没有进入if语句中的情况，根本不需要同步操作，可以直接返回instance
 */
class SynchronizedSingleton {
    //定义实例
    private static SynchronizedSingleton instance;
    //私有构造方法
    private SynchronizedSingleton(){}
    //对外提供获取实例的静态方法,对该方法加锁
    public static synchronized SynchronizedSingleton getInstance() {
        //在对象被使用的时候才实例化
        if (instance == null) {
            instance = new SynchronizedSingleton();
        }
        return instance;
    }
}

/**
 * 双重校验锁
 *  上面的代码存在的问题主要是锁的范围太大了。只要缩小锁的范围就可以了(使用同步代码块的方式减小了锁的范围。这样可以大大提高效率)
 *  该代码还存在隐患。隐患的原因主要和Java内存模型（JMM）有关
 *
 *  线程A发现变量没有被初始化, 然后它获取锁并开始变量的初始化。由于某些编程语言的语义，编译器生成的代码允许在线程A执行完变量的
 *  初始化之前，更新变量并将其指向部分初始化的对象。线程B发现共享变量已经被初始化，并返回变量。由于线程B确信变量已被初始化，
 *  它没有获取锁。如果在A完成初始化之前共享变量对B可见（这是由于A没有完成初始化或者因为一些初始化的值还没有穿过B使用的
 *  内存(缓存一致性)），程序很可能会崩溃。
 */
class Singleton {

    private static Singleton singleton;

    private Singleton() {
    }

    public static Singleton getSingleton() {
        if (singleton == null) {
            synchronized (Singleton.class) {
                if (singleton == null) {
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }
}

/**
 * code 7替代方案：使用volatile
 */
class VolatileSingleton {
    private static volatile VolatileSingleton singleton;

    private VolatileSingleton() {
    }

    public static VolatileSingleton getSingleton() {
        if (singleton == null) {
            synchronized (VolatileSingleton.class) {
                if (singleton == null) {
                    singleton = new VolatileSingleton();
                }
            }
        }
        return singleton;
    }
}

/**
 * 枚举式
 */
 enum EnumSingleton {

    INSTANCE;
    public void Singleton() {
    }
}
