package util;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by yuva on 17/4/17.
 */
public class TimeModule {

    private static TimeModule timeModule = new TimeModule() ;
    private Date dateServerTimeAtStart ;
    private Long localTimeAtStart ;
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss") ;

    public static TimeModule get() {
        return timeModule ;
    }

    public void setServerTimeAtStart(String serverTimeAtStart) {
        //TODO NOTE :- Client will be a few seconds behind server
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss") ;
        try {
            this.dateServerTimeAtStart = formatter.parse(serverTimeAtStart) ;
        } catch (ParseException e) {
            throw new RuntimeException(e) ;
        }
        this.localTimeAtStart = getLocalCurrentTime() ;
    }

    public String getCurrentTimeOnServer() {
        long elapsedTime = getLocalCurrentTime() - localTimeAtStart ;
        Calendar calendar = Calendar.getInstance() ;
        calendar.setTime(dateServerTimeAtStart);
        calendar.add(Calendar.SECOND, (int) elapsedTime);
        return formatter.format(calendar.getTime()) ;
    }

    private Long getLocalCurrentTime() {
        return System.currentTimeMillis()/1000 ;
    }
}
