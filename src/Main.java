import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.List;

import javax.swing.JFrame;

import gui.ClockImage;
import gui.HandGui;
import time.Clock;

public class Main {
    
    public static void main(String[] args) throws Exception{
        final Clock clock = new Clock();
        final JFrame frame = new JFrame("Clock");
        final List<HandGui> guis = HandGui.generate(clock.hands());
        
        try {
            final ClockImage image = new ClockImage(guis);
            frame.setPreferredSize(new Dimension(500, 500));
            frame.setContentPane(image);
            frame.setUndecorated(true);
            frame.getContentPane().setBackground(new Color(1.0f,1.0f,1.0f,0.0f));
            frame.setBackground(new Color(1.0f,1.0f,1.0f,0.0f));
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            time.Process.run(clock, image);
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        restartApplication(null);
        
        
        


    }

    /**
     * Sun property pointing the main class and its arguments.
     * Might not be defined on non Hotspot VM implementations.
     */
    public static final String SUN_JAVA_COMMAND = "sun.java.command";

    /**
     * Restart the current Java application
     * @param runBeforeRestart some custom code to be run before restarting
     * @throws IOException
     */
    public static void restartApplication(Runnable runBeforeRestart) throws IOException {
        try {
// java binary
            String java = System.getProperty("java.home") + "/bin/java";
// vm arguments
            List<String> vmArguments = ManagementFactory.getRuntimeMXBean().getInputArguments();
            StringBuffer vmArgsOneLine = new StringBuffer();
            for (String arg : vmArguments) {
// if it's the agent argument : we ignore it otherwise the
// address of the old application and the new one will be in conflict
                if (!arg.contains("-agentlib")) {
                    vmArgsOneLine.append(arg);
                    vmArgsOneLine.append(" ");
                }
            }
// init the command to execute, add the vm args
            final StringBuffer cmd = new StringBuffer("\"" + java + "\" " + vmArgsOneLine);

// program main and program arguments
            String[] mainCommand = System.getProperty(SUN_JAVA_COMMAND).split(" ");
// program main is a jar
            if (mainCommand[0].endsWith(".jar")) {
// if it's a jar, add -jar mainJar
                cmd.append("-jar " + new File(mainCommand[0]).getPath());
            } else {
// else it's a .class, add the classpath and mainClass
                cmd.append("-cp \"" + System.getProperty("java.class.path") + "\" " + mainCommand[0]);
            }
// finally add program arguments
            for (int i = 1; i < mainCommand.length; i++) {
                cmd.append(" ");
                cmd.append(mainCommand[i]);
            }
// execute the command in a shutdown hook, to be sure that all the
// resources have been disposed before restarting the application
            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    try {
                        Runtime.getRuntime().exec(cmd.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
// execute some custom code before restarting
            if (runBeforeRestart!= null) {
                runBeforeRestart.run();
            }
// exit
            System.exit(0);
        } catch (Exception e) {
// something went wrong
            throw new IOException("Error while trying to restart the application", e);
        }

    }
}
