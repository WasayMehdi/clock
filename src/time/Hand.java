package time;

public class Hand {
    
    private double number;
    private final int max;
    
    public Hand(double number, int max) {
        this.number = number;
        this.max = max;
    }
    
    public double angle() {
        final double angle = (double)number/(double)max * 2.0 * Math.PI;
        return angle;
    }
    
    public double increment(double n) {
        number += n;
        if(number >= max)
            number = 0;
        return number;
    }
    
    
    

}
