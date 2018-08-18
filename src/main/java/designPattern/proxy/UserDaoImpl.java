package designPattern.proxy;

/**
 * @author bin.yu
 * @create 2018-08-15 14:03
 **/
public class UserDaoImpl implements UserDao {


    @Override
    public void save() {
        System.out.println("userDao save");
    }
}
