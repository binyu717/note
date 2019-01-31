package util.excel;


import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author ming.jin
 * @Date 2017/8/8
 */
public class Excel<T> {

    /**
     * excel 标题
     */
    private List<ExcelHead> heads = new ArrayList<>();

    /**
     * excel sheet名称
     */
    private String sheetName;

    /**
     * 扩展名
     */
    private String extension;

    /**
     * 列数量
     */
    private int columnSize;

    /**
     * 行数量
     */
    private int rowSize;

    /**
     * 数据起始行
     */
    private int dataStartRow;

    /**
     * 数据
     */
    private List<T> data;

    /**
     * 原始数据
     */
    private List<Map<String,String>> originData;

    /**
     * 错误信息
     */
    private List<ExcelErrorInfo> errorInfos;

    public void addErrorInfo(ExcelErrorInfo info){
        if (CollectionUtils.isEmpty(errorInfos)){
            errorInfos = new ArrayList<>();
        }
        errorInfos.add(info);
    }

    @Override
    public String toString() {
        return "Excel{" +
                "heads=" + heads +
                "\n, sheetName='" + sheetName + '\'' +
                "\n, extension='" + extension + '\'' +
                "\n, columnSize=" + columnSize +
                "\n, rowSize=" + rowSize +
                "\n, dataStartRow=" + dataStartRow +
                "\n, data=" + data +
                "\n, originData=" + originData +
                "\n, errorInfos=" + errorInfos +
                '}';
    }

    public List<Map<String, String>> getOriginData() {
        return originData;
    }

    public void setOriginData(List<Map<String, String>> originData) {
        this.originData = originData;
    }

    public List<ExcelErrorInfo> getErrorInfos() {
        return errorInfos;
    }

    public void setErrorInfos(List<ExcelErrorInfo> errorInfos) {
        this.errorInfos = errorInfos;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getColumnSize() {
        return columnSize;
    }

    public void setColumnSize(int columnSize) {
        this.columnSize = columnSize;
    }

    public int getRowSize() {
        return rowSize;
    }

    public void setRowSize(int rowSize) {
        this.rowSize = rowSize;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public List<ExcelHead> getHeads() {
        return heads;
    }

    public void setHeads(List<ExcelHead> heads) {
        this.heads = heads;
    }

    public int getDataStartRow() {
        return dataStartRow;
    }

    public void setDataStartRow(int dataStartRow) {
        this.dataStartRow = dataStartRow;
    }
}
