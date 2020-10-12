package Utils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Hora {
    public static Integer discretizar(LocalDateTime H1, 
            LocalDateTime H2, Duration bloque){
        Duration dif = Duration.between(H1, H2);
        return (int)(dif.getSeconds()/bloque.getSeconds());
    }
    
    public static Integer discretizar(LocalTime H1, 
            LocalTime H2, Duration bloque){
        Duration dif = Duration.between(H1, H2);
        return (int)(dif.getSeconds()/bloque.getSeconds());
    }
    
    public static Integer hashVisual(LocalDateTime H){
        /*
        Integer hash = 0;
        hash += 100000000*H.getYear();
        hash += 1000000*H.getMonthValue();
        hash += 10000*H.getDayOfMonth();
        hash += 100*H.getHour();
        hash += H.getMinute();*/
        Integer hash = H.hashCode();
        return hash;
    }
}
