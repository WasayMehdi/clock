package gui;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.List;

import time.Hand;

public class HandGui {
    
    private final Hand hand;
    private final int thickness;
    private final int length;
    
    public HandGui(final Hand hand, final int thickness, final int length) {
        this.hand = hand;
        this.thickness = thickness;
        this.length = length;
    }
    
    public void draw(Graphics2D graphics, int baseX, int baseY) {

        double angle = hand.angle() - (Math.PI)/2;
        int endX = (int)(baseX + length * Math.cos(angle));
        int endY = (int)(baseY + length * Math.sin(angle));
        graphics.setStroke(new BasicStroke(thickness));
        graphics.drawLine(baseX, baseY, endX, endY);
        //System.out.printf("%d (%d, %d) - (%d, %d)\n", length, baseX, baseY, endX, endY);
        
    }
    
    public static List<HandGui> generate(Hand...hands) {
        final List<HandGui> guis = new LinkedList<>();
        for(int i = 0 ; i < 3; i++)
            guis.add(ClockImage.of(hands[i], i*2));
        return guis;
    }
}
