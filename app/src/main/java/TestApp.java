import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by yuva on 28/4/17.
 */

public class TestApp {
    public static String sample = "2017-04-28 16:00:00" ;

    public static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss") ;

    public static String getReadableLocalCurrentTime() {
        Date now = Calendar.getInstance().getTime();
        return formatter.format(now) ;
    }

    public static Long getLocalCurrentTime() {
        return System.currentTimeMillis()/1000 ;
    }

    public static String getReadableCurrentTimeOnServer(Date serverTimeAtStart, Integer elapsedTime) {
        Calendar calendar = Calendar.getInstance() ;
        calendar.setTime(serverTimeAtStart);
        calendar.add(Calendar.SECOND, elapsedTime);
        return formatter.format(calendar.getTime()) ;
    }

    public static String toLowerCase(String src) {
        return src.toLowerCase(Locale.ENGLISH) ;
    }
    public static String generateUserName(String userNameFeed) {
        return toLowerCase(userNameFeed).replaceAll("[^A-Za-z0-9]", "") ;
    }


    public static void main(String[] args){
        try {
            Date date = formatter.parse(sample) ;
            Calendar calendar = Calendar.getInstance() ;
            calendar.setTime(date);
            calendar.add(Calendar.SECOND, 1000);


            System.out.println("New Time is " + formatter.format(calendar.getTime()) + " Epoch is " +  date.getTime());

            System.out.println("Generated Username is : " + generateUserName("Happy Lucky HiName"));
        } catch (ParseException e) {
            e.printStackTrace();
        }



    }
}

