package dateDemo;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;


/**
 * @author bin.yu
 * @create 2018-04-03 8:59
 **/
public class JodaTimeDemo {

    public static void main(String[] args) {
        LocalDate localDate = new LocalDate();
//        System.out.println(localDate.getDayOfMonth());
//        System.out.println(localDate.getDayOfWeek());
//        System.out.println(localDate.getDayOfYear());
//        System.out.println(localDate.getMonthOfYear());
        DateTime today = new DateTime();
        System.out.println(today.toString("yyyy-MM-dd"));
        System.out.println(today.minusDays(1).toString("YYYY-MM-dd"));
        System.out.println(today.plusDays(1).toString("YYYY-MM-dd"));
        System.out.println(today);
        System.out.println(localDate.plusMonths(1).dayOfMonth().withMaximumValue());


    }
}
