package kr.momjobgo.eyou.web.common;


import org.springframework.context.annotation.Configuration;

import java.util.Calendar;
import java.util.Date;
@Configuration
public class GetDateTime {
    public Date yesterday() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        Date dateObj = cal.getTime();
        return dateObj;
    }
}
