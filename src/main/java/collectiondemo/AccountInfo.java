package collectiondemo;

import java.util.List;

/**
 * 账号信息
 * @author bin.yu
 * @create 2017-12-21 15:42
 **/
public class AccountInfo {

    private String id;
    private String name;
    private String password;
    /**
     * r/w/x
     */
    private List<Featrue> featrues;

    public AccountInfo(String id, String name, String password, List<Featrue> featrues) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.featrues = featrues;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Featrue> getFeatrues() {
        return featrues;
    }

    public void setFeatrues(List<Featrue> featrues) {
        this.featrues = featrues;
    }
}
