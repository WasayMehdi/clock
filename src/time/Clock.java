package time;

import java.util.Calendar;

public class Clock{
    
    private static final double SECOND_INCREMENT = 1;
    private static final double MINUTE_INCREMENT = SECOND_INCREMENT/60;
    private static final double HOUR_INCREMENT = MINUTE_INCREMENT/60;
        
    private Hand seconds;
    private Hand minutes;
    private Hand hours;
    
    public Clock() {
        final Calendar calendar = Calendar.getInstance();
        final int seconds = calendar.get(Calendar.SECOND);
        final double minutes = calendar.get(Calendar.MINUTE) + (MINUTE_INCREMENT * seconds);
        final double hours = calendar.get(Calendar.HOUR) + (HOUR_INCREMENT * minutes * 60);
        this.seconds = new Hand(seconds, 60);
        this.minutes = new Hand(minutes , 60);
        this.hours = new Hand(hours , 12);
        System.out.printf("%f:%f:%d", hours, minutes, seconds);
    }
    
    /**
     * process a second on clock
     */
    public void tick() {
        seconds.increment(SECOND_INCREMENT);
        minutes.increment(MINUTE_INCREMENT);
        hours.increment(HOUR_INCREMENT);
    }
    
    public Hand[] hands() {
        return new Hand[]{seconds, minutes, hours};
    }
    
    

}
