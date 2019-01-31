package util.excel;

/**
 * @Author ming.jin
 * @Date 2017/8/24
 */
public class ExcelErrorInfo {

    private Integer rowNo;

    private Integer columnNo;

    private Integer objIndex;

    private String titleName;

    private String errorInfo;

    @Override
    public String toString() {
        return "ExcelErrorInfo{" +
                "rowNo=" + rowNo +
                ", columnNo=" + columnNo +
                ", objIndex=" + objIndex +
                ", titleName='" + titleName + '\'' +
                ", errorInfo='" + errorInfo + '\'' +
                '}';
    }

    public Integer getObjIndex() {
        return objIndex;
    }

    public void setObjIndex(Integer objIndex) {
        this.objIndex = objIndex;
    }

    public Integer getRowNo() {
        return rowNo;
    }

    public void setRowNo(Integer rowNo) {
        this.rowNo = rowNo;
    }

    public Integer getColumnNo() {
        return columnNo;
    }

    public void setColumnNo(Integer columnNo) {
        this.columnNo = columnNo;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }
}
