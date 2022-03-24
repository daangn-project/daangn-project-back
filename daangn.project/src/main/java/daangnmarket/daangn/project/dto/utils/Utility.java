package daangnmarket.daangn.project.dto.utils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Utility {

    public static String  timeFormatting(LocalDateTime time){
        LocalDateTime now = LocalDateTime.now();
        String result = "";
        if(time.isAfter(now.minusHours(1L))){
            LocalDateTime nowByMinute = now.truncatedTo(ChronoUnit.MINUTES);
            LocalDateTime createdByMinute = time.truncatedTo(ChronoUnit.MINUTES);
            if(createdByMinute.isAfter(nowByMinute.minusMinutes(1L))){
                result = "방금 전";
            }else{
                Duration duration = Duration.between(time, now);
                result = (duration.getSeconds() / 60) + "분 전";
            }
        }else if(time.truncatedTo(ChronoUnit.DAYS).isEqual(now.truncatedTo(ChronoUnit.DAYS))){
            result = time.format(DateTimeFormatter.ofPattern("hh시 mm분"));
        }else{
            result = time.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"));
        }
        return result;
    }
}
