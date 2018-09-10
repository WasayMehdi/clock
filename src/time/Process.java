package time;

import gui.ClockImage;

public class Process implements  Runnable{
    
    private final Clock clock;
    private final ClockImage image;
    
    public Process(final Clock clock, final ClockImage image) {
        this.clock = clock;
        this.image = image;
    }
    
    @Override
    public void run() {
        while(true) {
            clock.tick();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            image.revalidate();
            image.repaint();
        }
    }
    
    public static final void run(final Clock clock, final ClockImage image) {
        new Thread(new Process(clock, image)).start();
    }

}
