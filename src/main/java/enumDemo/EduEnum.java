package enumDemo;

/**
 * @author bin.yu
 * @create 2018-01-05 15:39
 **/
public enum EduEnum {

    /**
     * 博士
     */
    doctor("doctor","博士"),
    /**
     * 硕士
     */
    master("master","硕士"),
    /**
     * 本科
     */
    UC("UC","本科"),
    /**
     * 大专
     */
    JC("JC","大专"),
    /**
     * 中专
     */
    PS("PS ","中专"),
    /**
     * 中技
     */
    PT("PT ","中技"),
    /**
     * 高中
     */
    HS("HS","高中"),
    /**
     * 初中及以下
     */
    MS("MS","初中及以下"),
    /**
     * 其他
     */
    other("other","其他");

    private String name;
    private String desc;

    private EduEnum(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
