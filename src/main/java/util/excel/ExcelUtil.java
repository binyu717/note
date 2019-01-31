package util.excel;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections4.MapUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static org.apache.poi.ss.usermodel.Cell.*;

/**
 * @Author ming.jin
 * @Date 2017/8/8
 */
public class ExcelUtil {

    private static final Logger logger = LoggerFactory.getLogger(ExcelUtil.class);

    /**
     * Create excel 对象.
     *
     * @param <T>       the type parameter
     * @param dataList  the data list
     * @param dataClass the data class
     * @return the excel
     */
    public static <T> Excel<T> createExcelObj(List<T> dataList,Class<T> dataClass){
        Excel<T> excel = new Excel<>();
        excel.setHeads(createExcelHead(dataClass));
        excel.setExtension("xls");
        excel.setSheetName("Sheet1");
        excel.setRowSize(dataList.size()+1);
        excel.setColumnSize(excel.getHeads().size());
        excel.setData(dataList);

        return excel;
    }

    /**
     * 创建excel Workbook对象.
     *
     * @param excel the excel
     */
    public static <T> Workbook createExcel(Excel<T> excel){
        Workbook wb = null;
        if (excel.getExtension().equals("xls")){
            wb = new HSSFWorkbook();
        }else{
            wb = new XSSFWorkbook();
        }
        Sheet sheet = wb.createSheet(excel.getSheetName());
        sheet.setDefaultColumnWidth((short) 20);
        //写标题
        Map<String,Boolean> groupWriteFlag = new HashMap<>();
        int rowIndex = 0;
        Row titleRow = sheet.createRow(rowIndex);
        List<ExcelHead> heads = excel.getHeads();
        heads =heads.stream().sorted(Comparator.comparing(ExcelHead::getWriteSort)).collect(Collectors.toList());
        int columnNum = 0;
        int dataStartRow = excel.getDataStartRow();
        for (ExcelHead head : heads){
            if (!head.getHeadName().contains("|")){
                CellRangeAddress cellRangeAddress =new CellRangeAddress(titleRow.getRowNum(), titleRow.getRowNum(), columnNum, columnNum);
                sheet.addMergedRegion(cellRangeAddress);
                Cell cell = titleRow.createCell(columnNum);
                cell.setCellValue(head.getHeadName());

            }else{
                String[] groupArray = head.getHeadName().split("\\|");
                String groupName = groupArray[0];
                if (!MapUtils.getBoolean(groupWriteFlag,groupName,false)){
                    CellRangeAddress cellRangeAddress =new CellRangeAddress(titleRow.getRowNum(), titleRow.getRowNum(), columnNum, columnNum+groupArray.length-1);
                    sheet.addMergedRegion(cellRangeAddress);
                    Cell cell = titleRow.createCell(columnNum);
                    cell.setCellValue(groupName);
                    groupWriteFlag.put(groupName,true);
                }

                Row subTitleRow = sheet.getRow(titleRow.getRowNum()+1);
                if (subTitleRow==null){
                    subTitleRow = sheet.createRow(titleRow.getRowNum()+1);
                }
                Cell subTitleCell = subTitleRow.getCell(columnNum);
                if(subTitleCell == null){
                    subTitleCell = subTitleRow.createCell(columnNum);
                }
                subTitleCell.setCellValue(groupArray[1]);
            }
            columnNum++;
        }

        rowIndex=excel.getDataStartRow() + 1;

        //写值

        List<T> dataList = excel.getData();
        int objIndex = 0;
        for(T item:dataList){
            Row dataRow = sheet.createRow(rowIndex);
            int i = 0;
            for (ExcelHead head : heads){
                try {
                    Field field =item.getClass().getDeclaredField(head.getPropertyName());
                    field.setAccessible(true);
                    Object value =field.get(item);
                    Cell cell = dataRow.createCell(i);
                    if (head.getType().equals(HeadProperty.HeadType.STRING)){
                        if (value == null){
                            cell.setCellValue("");
                        }else{
                            cell.setCellValue(value.toString());
                        }
                    }
                    if (head.getType().equals(HeadProperty.HeadType.INT)){
                        if (value == null){
                            cell.setCellValue(0);
                        }else{
                            cell.setCellValue((int)value);
                        }
                    }
                    if (head.getType().equals(HeadProperty.HeadType.DOUBLE)){
                        if (value == null){
                            cell.setCellValue(0.0);
                        }else {
                            cell.setCellValue((double) value);
                        }
                    }
                    if (head.getType().equals(HeadProperty.HeadType.DATE)){
                        if (value == null){
                            cell.setCellValue(new Date());
                        }else {
                            SimpleDateFormat sdf = new SimpleDateFormat(head.getFormat());
                            cell.setCellValue(sdf.format((Date) value));
                        }
                    }

                } catch (NoSuchFieldException e) {
                    logger.error(e.getMessage(),e);
                    throw new RuntimeException("创建excel 出错");
                } catch (IllegalAccessException e) {
                    logger.error(e.getMessage(),e);
                    throw new RuntimeException("创建excel 出错");
                }
                i++;
            }
            rowIndex++;
            objIndex++;

        }

        return wb;
    }

    /**
     * 解析excel文件
     *
     * @param is        the is
     * @param extension the excel扩展名
     * @param dataClass the data class
     * @return the excel
     */
    public static <T> Excel<T> resolverExcel(InputStream is,String extension,Class<T> dataClass){
        Workbook wb = null;
        Excel<T> excel = new Excel<>();
        try {
            if (extension.equals("xls")){
                wb = new HSSFWorkbook(is);
            }else{
                wb = new XSSFWorkbook(is);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
            throw new RuntimeException("读取Excel出错");
        }
        excel.setExtension(extension);

        //写标题
        List<ExcelHead> heads = createExcelHead(dataClass);
        excel.setHeads(heads);

        Sheet sheet =  wb.getSheetAt(0);
        String sheetName =sheet.getSheetName();

        excel.setSheetName(sheetName);

        excel.setRowSize(sheet.getLastRowNum()+1);

        //构造一个map标题名称对应哪一列
        Map<Integer,ExcelHead> headIndexMap = resolveExcelHeader(excel,heads,sheet);

        //读数据
        List<T> dataList = new ArrayList<>();
        List<Map<String,String>> originDataList = new ArrayList<>();
        for (int i = excel.getDataStartRow();i<excel.getRowSize();i++){
            Row dataRow = sheet.getRow(i);
            Map<String,String> dataMap = new HashMap<>();
            T obj = null;
            try {
                obj = dataClass.newInstance();
            } catch (Exception e) {
                logger.error(e.getMessage(),e);
                throw new RuntimeException("在Excel对应的数据类无法初始化");
            }

            for(int j = 0;j<excel.getColumnSize();j++){
                Cell cell = dataRow.getCell(j);
                ExcelHead head =headIndexMap.get(j);

                Field field = null;
                try {
                    field = dataClass.getDeclaredField(head.getPropertyName());
                } catch (NoSuchFieldException e) {
                    logger.error(e.getMessage(),e);
                    throw new RuntimeException("在Excel对应的属性无法初始化");
                }
                field.setAccessible(true);

                dataMap.put(field.getName(),getCellDataToString(cell,head));

                if (head.getType().equals(HeadProperty.HeadType.STRING)){
                    try{
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        field.set(obj,cell.getStringCellValue());
                    }catch (Exception e){
                        excel.addErrorInfo(createError(i,j,i-excel.getDataStartRow(),head.getHeadName(),"格式错误"));
                    }
                }
                if (head.getType().equals(HeadProperty.HeadType.INT)){
                    try {
                        if (field.getType().equals(Integer.class)) {
                            field.set(obj, ((Double) cell.getNumericCellValue()).intValue());
                        }
                        if (field.getType().equals(Long.class)) {
                            field.set(obj, ((Double) cell.getNumericCellValue()).longValue());
                        }
                    }catch (Exception e){
                        excel.addErrorInfo(createError(i,j,i-excel.getDataStartRow(),head.getHeadName(),"格式错误"));
                    }
                }
                if (head.getType().equals(HeadProperty.HeadType.DOUBLE)){
                    try{
                        field.set(obj,getDoubleValueInCell(cell));
                    }catch (Exception e){
                        excel.addErrorInfo(createError(i,j,i-excel.getDataStartRow(),head.getHeadName(),"格式错误"));
                    }
                }
                if (head.getType().equals(HeadProperty.HeadType.DATE)){
                    try {
                        if(HSSFDateUtil.isCellDateFormatted(cell)){
                            field.set(obj,cell.getDateCellValue());
                        }
                    }catch (Exception e){
                        try {
                            String dateStr = cell.getStringCellValue();
                            SimpleDateFormat sdf = new SimpleDateFormat(head.getFormat());
                            field.set(obj, sdf.parse(dateStr));
                        }catch (Exception ex){
                            excel.addErrorInfo(createError(i,j,i-excel.getDataStartRow(),head.getHeadName(),"格式错误"));
                        }
                    }
                }
            }
            originDataList.add(dataMap);
            dataList.add(obj);
        }
        excel.setOriginData(originDataList);
        excel.setData(dataList);
        return excel;
    }

    private static ExcelErrorInfo createError(int rowNo, int ColumnNo,int objIndex, String headName, String info) {
        ExcelErrorInfo error = new ExcelErrorInfo();
        error.setRowNo(rowNo+1);
        error.setColumnNo(ColumnNo+1);
        error.setObjIndex(objIndex);
        error.setTitleName(headName);
        error.setErrorInfo(info);
        return error;
    }

    /**
     * 解析excel标题头.
     *
     * @param excel the excel
     * @param heads the heads
     * @param sheet the sheet
     * @return the map
     */
    public static Map<Integer,ExcelHead> resolveExcelHeader(Excel excel,List<ExcelHead> heads,Sheet sheet){

        excel.setDataStartRow(1);

        Map<Integer,ExcelHead> headIndexMap = new HashMap<>();

        Row titleRow = sheet.getRow(0);
        excel.setColumnSize(titleRow.getLastCellNum());
        for(int i = 0;i<titleRow.getLastCellNum();i++){
            Cell cell = titleRow.getCell(i);
            String headerName =cell.getStringCellValue();
            //Optional<ExcelHead> head= heads.parallelStream().filter(t->t.getHeadName().equals(headerName)).findFirst();
            List<ExcelHead> findedHeaders = heads.parallelStream().filter(t->t.getHeadName().startsWith(headerName)).collect(Collectors.toList());
            if(findedHeaders.size()==1){
                headIndexMap.put(i,findedHeaders.get(0));
            }else if(findedHeaders.size()>1){
                Row subTitleRow = sheet.getRow(1);
                int max = i+findedHeaders.size();
                for(int j = i;j<max;j++){
                    Cell subCell = subTitleRow.getCell(j);
                    String subHeaderName =subCell.getStringCellValue();
                    Optional<ExcelHead> head= heads.parallelStream().filter(t->t.getHeadName().equals(headerName+"|"+subHeaderName)).findFirst();
                    if (head.isPresent()){
                        headIndexMap.put(j,head.get());
                        excel.setDataStartRow(2);
                    }else{
                        throw new RuntimeException("在Excel中找不到对应的列名"+headerName+"|"+subHeaderName);
                    }
                }
                i=i+findedHeaders.size()-1;
            }else {
                ExcelHead optionHead = heads.stream().filter(t->t.getHeadName().equals(headerName)).findFirst().get();
                if (!optionHead.isOption()){
                    throw new RuntimeException("在Excel中找不到对应的列名"+headerName);
                }
            }
        }

        return headIndexMap;
    }

    /**
     * Create excel 标题头.
     *
     * @param <T>       the type parameter
     * @param dataClass the data class
     * @return the list
     */
    public static <T> List<ExcelHead> createExcelHead(Class<T> dataClass){
        Field[] fields = dataClass.getDeclaredFields();
        List<ExcelHead> heads = new ArrayList<>();
        for (Field field : fields){
            Annotation[] annotations = field.getDeclaredAnnotations();
            for(Annotation annotation : annotations) {
                if (annotation instanceof HeadProperty) {
                    HeadProperty property = (HeadProperty) annotation;
                    ExcelHead head = new ExcelHead();
                    head.setHeadName(property.name());
                    head.setPropertyName(field.getName());
                    head.setType(property.type());
                    head.setFormat(property.format());
                    head.setOption(property.option());
                    head.setWriteSort(property.writeSort());
                    heads.add(head);
                }
            }
        }
        return heads;
    }

    public static Workbook createProductExcel(List<String> list, Map<String, Object> map) {
        Excel excel = new Excel<>();
        excel.setHeads(createProductExcelHead(list));
        excel.setExtension("xls");
        excel.setSheetName("Sheet1");
        excel.setRowSize(1);
        excel.setColumnSize(excel.getHeads().size());
        Workbook wb = null;
        if (excel.getExtension().equals("xls")){
            wb = new HSSFWorkbook();
        }else{
            wb = new XSSFWorkbook();
        }
        Sheet sheet = wb.createSheet(excel.getSheetName());
        //写标题
        int rowIndex = 0;
        Row titleRow = sheet.createRow(rowIndex);
        List<ExcelHead> heads = excel.getHeads();
        heads =heads.stream().sorted(Comparator.comparing(ExcelHead::getWriteSort)).collect(Collectors.toList());
        int columnNum = 0;
        for (ExcelHead head : heads){
            CellRangeAddress cellRangeAddress =new CellRangeAddress(titleRow.getRowNum(), titleRow.getRowNum(), columnNum, columnNum);
            sheet.addMergedRegion(cellRangeAddress);
            Cell cell = titleRow.createCell(columnNum);
            cell.setCellValue(head.getHeadName());
            columnNum++;
        }
        if (sheet.getRow(rowIndex) != null) {
            int lastRowNo = sheet.getLastRowNum();
            sheet.shiftRows(rowIndex, lastRowNo, 1);
        }
        //添加首行  赋值 SKU 规格选项
        Row firstRow = sheet.createRow(rowIndex);
        CellRangeAddress cellRangeAddress =new CellRangeAddress(firstRow.getRowNum(), firstRow.getRowNum(), 0, columnNum - 1);
        sheet.addMergedRegion(cellRangeAddress);
        Cell cell = firstRow.createCell(0);
        cell.getCellStyle().setAlignment(CellStyle.ALIGN_LEFT);
        cell.getCellStyle().setVerticalAlignment(CellStyle.VERTICAL_TOP);
        cell.getCellStyle().setWrapText(true);
        firstRow.setHeightInPoints(80);
        String name = "";
        for(Map.Entry<String,Object> entry : map.entrySet()) {
            name += entry.getKey();
            List<String> values = (List<String>) entry.getValue();
            name = name +"："+ JSONObject.toJSONString(values) + "\n";
        }
        cell.setCellValue(name);
        return wb;
    }

    /**
     *
     * @param list
     * @return
     */
    public static List<ExcelHead> createProductExcelHead(List<String> list){
        List<ExcelHead> heads = new ArrayList<>();
        list.forEach(p ->{
            ExcelHead head = new ExcelHead();
            head.setHeadName(p);
            heads.add(head);
        });
        return heads;
    }

    /**
     * 解析产品导入 Excel
     * @param is
     * @param extension
     * @return
     */
    public static Excel<Map> resolverProductExcel(InputStream is, String extension) {
        Workbook wb = null;
        Excel<Map> excel = new Excel<>();
        try {
            if (extension.equals("xls")){
                wb = new HSSFWorkbook(is);
            }else{
                wb = new XSSFWorkbook(is);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
            throw new RuntimeException("读取Excel出错");
        }
        excel.setExtension(extension);
        Sheet sheet =  wb.getSheetAt(0);
        String sheetName =sheet.getSheetName();
        excel.setSheetName(sheetName);
        excel.setRowSize(sheet.getLastRowNum()+1);
        excel.setDataStartRow(2);
        List<ExcelHead> heads = new ArrayList<>();
        if(sheet.getLastRowNum() > 0) {
            Row headRow = sheet.getRow(1);
            excel.setColumnSize(headRow.getLastCellNum());
            for(int i = 0;i<headRow.getLastCellNum();i++){
                ExcelHead head = new ExcelHead();
                headRow.getCell(i).setCellType(Cell.CELL_TYPE_STRING);
                head.setHeadName(headRow.getCell(i).getStringCellValue());
                head.setPropertyName(headRow.getCell(i).getStringCellValue());
                head.setType(HeadProperty.HeadType.STRING);
                heads.add(head);
            }
        }
        excel.setHeads(heads);
        //构造一个map标题名称对应哪一列
        Map<Integer,ExcelHead> headIndexMap = resolveProductExcelHeader(excel,heads,sheet);
        //读数据
        List<Map> dataList = new ArrayList<>();
        List<Map<String,String>> originDataList = new ArrayList<>();
        for (int i = excel.getDataStartRow();i<excel.getRowSize();i++){
            Row dataRow = sheet.getRow(i);
            Map<String,String> dataMap = new HashMap<>();
            Map obj = new HashMap();
            for(int j = 0;j<excel.getColumnSize();j++){
                Cell cell = dataRow.getCell(j);
                ExcelHead head =headIndexMap.get(j);
                String  field = head.getPropertyName();
                if(cell == null) {
                    throw new RuntimeException("【"+field+"】列存在空值，请重新编辑EXCEL文件并再次导入");
                }
                cell.setCellType(Cell.CELL_TYPE_STRING);
                dataMap.put(field,getCellDataToString(cell,head));
                obj.put(field,cell.getStringCellValue());
            }
            originDataList.add(dataMap);
            dataList.add(obj);
        }
        excel.setOriginData(originDataList);
        excel.setData(dataList);
        return excel;
    }

    /**
     * 解析excel标题头.
     *
     * @param excel the excel
     * @param heads the heads
     * @param sheet the sheet
     * @return the map
     */
    public static Map<Integer,ExcelHead> resolveProductExcelHeader(Excel excel,List<ExcelHead> heads,Sheet sheet){
        Map<Integer,ExcelHead> headIndexMap = new HashMap<>();
        Row titleRow = sheet.getRow(1);
        excel.setColumnSize(titleRow.getLastCellNum());
        for(int i = 0;i<titleRow.getLastCellNum();i++){
            Cell cell = titleRow.getCell(i);
            String headerName =cell.getStringCellValue();
            //Optional<ExcelHead> head= heads.parallelStream().filter(t->t.getHeadName().equals(headerName)).findFirst();
            List<ExcelHead> findedHeaders = heads.parallelStream().filter(t->t.getHeadName().startsWith(headerName)).collect(Collectors.toList());
            if(findedHeaders.size()==1){
                headIndexMap.put(i,findedHeaders.get(0));
            }else if(findedHeaders.size()>1){
                Row subTitleRow = sheet.getRow(1);
                int max = i+findedHeaders.size();
                for(int j = i;j<max;j++){
                    Cell subCell = subTitleRow.getCell(j);
                    String subHeaderName =subCell.getStringCellValue();
                    Optional<ExcelHead> head= heads.parallelStream().filter(t->t.getHeadName().equals(headerName+"|"+subHeaderName)).findFirst();
                    if (head.isPresent()){
                        headIndexMap.put(j,head.get());
                        excel.setDataStartRow(2);
                    }else{
                        throw new RuntimeException("在Excel中找不到对应的列名"+headerName+"|"+subHeaderName);
                    }
                }
                i=i+findedHeaders.size()-1;
            }else {
                ExcelHead optionHead = heads.stream().filter(t->t.getHeadName().equals(headerName)).findFirst().get();
                if (!optionHead.isOption()){
                    throw new RuntimeException("在Excel中找不到对应的列名"+headerName);
                }
            }
        }

        return headIndexMap;
    }

    private static String getCellDataToString(Cell cell,ExcelHead head){
        if(cell == null) {
            return "";
        }
        switch (cell.getCellType()){

//            case _NONE:
//                return "";
            case CELL_TYPE_BLANK:
                return "";
            case CELL_TYPE_ERROR:
                return "";
            case CELL_TYPE_STRING:
                return cell.getStringCellValue();
            case CELL_TYPE_BOOLEAN:
                return Boolean.toString(cell.getBooleanCellValue());
            case CELL_TYPE_FORMULA:
                return Double.toString(cell.getNumericCellValue());
            case CELL_TYPE_NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)){
                    SimpleDateFormat sdf = new SimpleDateFormat(head.getFormat());
                    return sdf.format(cell.getDateCellValue());
                }else{
                    return Double.toString(cell.getNumericCellValue());
                }

            default:
                    return "";
        }
    }


    private static Double getDoubleValueInCell(Cell cell){
        double result = 0;
        try {
            result =cell.getNumericCellValue();
        }catch (Exception e){
            String str = cell.getStringCellValue();
            result = Double.parseDouble(str);
        }
        return result;
    }

    public static String getFileName(String userAgent, String fileName){
        String encodeName = "";
        try {
            if (userAgent != null && (userAgent.contains("Firefox") || userAgent.contains("Chrome")
                    || userAgent.contains("Safari"))) {
                encodeName= new String((fileName).getBytes(), "ISO8859-1");
            } else {
                encodeName= URLEncoder.encode(fileName,"UTF-8"); //其他浏览器
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodeName;
    }

    public static void insertTitleRow(Workbook wb, Excel excel,String title) {
        Sheet sheet = wb.getSheet(excel.getSheetName());
        int insertRowIndex = 0;
        sheet.shiftRows(insertRowIndex, sheet.getLastRowNum(),1,true,false);
        Row row = sheet.createRow(insertRowIndex);
        CellRangeAddress cellRangeAddress =new CellRangeAddress(insertRowIndex, insertRowIndex, 0, excel.getHeads().size()-1);
        sheet.addMergedRegion(cellRangeAddress);
        Cell cell = row.createCell(0);
        cell.setCellValue(title);
    }
}
