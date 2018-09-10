package gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import time.Hand;

public class ClockImage extends JPanel {
    
    private static final int SIZE = 400;
    
    private static final long serialVersionUID = 1L;
    
    private final List<HandGui> hands;
    private final BufferedImage clock;

    public ClockImage(final List<HandGui> list) throws IOException {
        super();
        this.clock = ImageIO.read(new URL("http://res.freestockphotos.biz/pictures/16/16760-illustration-of-a-clock-pv.png"));
        this.hands = list;
        
        
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        final Graphics2D graphics = (Graphics2D)g;
        g.drawImage(clock, 0, 0, 400, 400, null);
        for(HandGui gui : hands) {
            gui.draw(graphics, 200, 200);
        }
        
    }
    
    public static HandGui of(Hand hand, int thickness) {
        int length = SIZE/4 + (int)((2.5 - thickness/2) * (SIZE/8));
        return new HandGui(hand, thickness, length);
    }

}
