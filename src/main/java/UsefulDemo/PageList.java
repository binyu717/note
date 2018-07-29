package UsefulDemo;
import java.util.ArrayList;
import java.util.Map;

/**
 * 写这个例子就是要学会这样思考
 * @author bin.yu
 * @create 2018-07-26 20:15
 **/
public class PageList<T> extends ArrayList<T>{

    private int pageSize;
    private int pageNum;
    private int totalNum;
    private int totalPage;
    private Map map;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public int getTotalPage() {
        if (pageSize != 0) {
            totalPage = (int) Math.ceil(totalNum/pageSize);
        }
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }


    public static void main(String[] args) {
        PageList<String> pageList = new PageList();
         pageList.add("1");
         pageList.add("2");
         pageList.add("3");
         pageList.setTotalNum(10);
         pageList.setPageNum(1);
         pageList.setPageSize(2);
        System.out.println(pageList.get(2));

    }
}
