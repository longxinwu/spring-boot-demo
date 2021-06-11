package longxinwu.singleton;

import java.io.Serializable;

/**
 * 单例模式
 * 1)单例模式保证了 系统内存中该类只存在一个对象，节省了系统资源，对于一些需要频繁创建销毁的对象，使
 * 用单例模式可以提高系统性能
 * 2) 当想实例化一个单例类的时候，必须要记住使用相应的获取对象的方法，而不是使用 new
 * 3) 单例模式 使用的场景：需要 频繁的进行创建和销毁的对象、创建对象时耗时过多或耗费资源过多(即：重量级
 * 对象)，但又经常用到的对象、 工具类对象、频繁访问数据库或文件的对象(比如 数据源、session  工厂等)
 *
 * 单例模式容易糟蛋三种方式的破坏
 * 序列化，克隆，反射，为了控制这三种情况的出现可
 * 解决方案如下：
 * 1、防止反射
 *    定义一个全局变量，当第二次创建的时候抛出异常
 * 2、防止克隆破坏
 *       重写clone(),直接返回单例对象
 * 3、防止序列化破坏
 *    添加readResolve(),返回Object对象
 */


public class Singleton implements Serializable, Cloneable {

    private static volatile boolean isCreate = false; //默认是第一次创建
    /**
     * 双重检查加锁确保懒汉式单例线程安全
     */
    private volatile static Singleton instance = null;
    // 私有化构造方法， 外部不能new
    private Singleton() {
        if(isCreate){
            throw new RuntimeException("the Singleton had been created!");
        }
        isCreate = true;
    }
    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }

        }
        return instance;
    }

    /**
     * 当getInstance方法第一次被调用的时候,它第一次读取SingletonHolder.instance，
     * 导致SingletonHolder类得到初始化；而这个类在装载并被初始化的时候，会初始化它的静态域，
     * 从而创建Singleton的实例，由于是静态的域，因此只会在虚拟机装载类的时候初始化一次，并由虚拟机来保证它的线程安全性。
     */
    /*private static class SingletonHolder{
        private static Singleton instance = new Singleton();
    }
    private Singleton(){
    }
    public static Singleton getInstance(){
        return SingletonHolder.instance;
    }*/

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * 防止序列化破坏
     * @return
     */
    private Object readResolve(){
        return instance;
    }
}


