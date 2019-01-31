package util.excel;

/**
 * @Author ming.jin
 * @Date 2017/8/8
 */
public class ExcelHead {

    /**
     * 属性名称
     */
    private String propertyName;

    /**
     * 标题名称
     */
    private String headName;

    /**
     * 值类型
     */
    private HeadProperty.HeadType type;

    /**
     * 格式
     */

    private String format;

    private boolean option;

    private int writeSort;

    @Override
    public String toString() {
        return "ExcelHead{" +
                "propertyName='" + propertyName + '\'' +
                ", headName='" + headName + '\'' +
                ", type=" + type +
                ", format='" + format + '\'' +
                ", option=" + option +
                ", writeSort=" + writeSort +
                '}';
    }

    public boolean isOption() {
        return option;
    }

    public void setOption(boolean option) {
        this.option = option;
    }

    public int getWriteSort() {
        return writeSort;
    }

    public void setWriteSort(int writeSort) {
        this.writeSort = writeSort;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getHeadName() {
        return headName;
    }

    public void setHeadName(String headName) {
        this.headName = headName;
    }

    public HeadProperty.HeadType getType() {
        return type;
    }

    public void setType(HeadProperty.HeadType type) {
        this.type = type;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
