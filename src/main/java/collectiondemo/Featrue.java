package collectiondemo;

import java.util.List;

/**
 * 功能
 *
 * @author bin.yu
 * @create 2017-12-21 16:04
 **/
public class Featrue {

    private String name;
    private List privileges;

    public Featrue(String name, List privileges) {
        this.name = name;
        this.privileges = privileges;
    }

    public Featrue() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List getPrivileges() {
        return privileges;
    }

    public void setPrivileges(List privileges) {
        this.privileges = privileges;
    }

}
