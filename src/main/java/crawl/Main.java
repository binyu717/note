package crawl;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/**
 * @author bin.yu
 * @create 2018-08-18 10:40
 **/
public class Main {

    public static DB db = new DB();

    public static void main(String[] args) throws SQLException, IOException {
        db.executeSql("TRUNCATE crawl_record;");
        processPage("https://news.sina.com.cn/");
    }

    public static void processPage(String url) throws SQLException, IOException{
        //检查一下是否给定的URL已经在数据库中
        String sql = "select * from crawl_record where URL = '"+url+"'";
        ResultSet rs = db.querySql(sql);
        if(rs.next()){

        }else{
            //将uRL存储到数据库中避免下次重复
            //得到有用的信息
            Document doc = Jsoup.connect(url).get();
            Elements news = doc.select("div").select("li").select("a[href]");
            for (Element element : news) {
                String title = element.text();
                String childUrl = element.attr("abs:href");
                if (StringUtils.isNotBlank(title) && StringUtils.isNotBlank(childUrl)) {
                    java.sql.Date   now = new  java.sql.Date(new java.util.Date().getTime());
                    sql = "INSERT INTO `crawl_record` (url,title,created_time) VALUES " + "(?,?,?);";
                    PreparedStatement stmt = db.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    stmt.setString(1, childUrl);
                    stmt.setString(2,title);
                    stmt.setDate(3,now);
                    stmt.execute();
                    System.out.println(title+"————"+childUrl);
                    processPage(element.attr("abs:href"));
                }
            }
        }
    }
}
