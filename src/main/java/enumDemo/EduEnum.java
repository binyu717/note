package enumDemo;

import java.util.*;

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


    public static List<EduEnum> getAllItems() {
        return Arrays.asList(EduEnum.values());
    }

    public static List<Map<String, String>> getAllForMap() {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        for (EduEnum edu : EduEnum.values()) {
            Map<String, String> map = new HashMap<>();
            map.put("name", edu.getName());
            map.put("desc", edu.getDesc());
            list.add(map);
        }
        return list;
    }

    public static String getNameByDesc(String desc) {
        List<EduEnum> allItems = getAllItems();
        for (EduEnum item : allItems) {
            if (item.getDesc().equals(desc)) {
                return item.getName();
            }
        }
        return "";
    }

    public static String getDescByName(String name) {
        List<EduEnum> allItems = getAllItems();
        for (EduEnum item : allItems) {
            if (item.getName().equals(name)) {
                return item.getDesc();
            }
        }
        return "";
    }
    public static Map<String, String> getMap() {
        Map<String, String> map = new LinkedHashMap<>();
        for (EduEnum edu : EduEnum.values()) {
            map.put(edu.getName(), edu.getDesc());
        }
        return map;
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
