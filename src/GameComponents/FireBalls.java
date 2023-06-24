import com.sun.tools.javac.Main;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.util.List;

import static com.sun.java.accessibility.util.AWTEventMonitor.addKeyListener;

public class FireBalls extends Thread implements Serializable {
    MainPanel mainPanel;
    KeyEvent ev;
    public FireBalls()
    {
        this.mainPanel = null;
    }
    public FireBalls(MainPanel p, KeyEvent key)
    {
        this.mainPanel = p;
        this.ev = key;
    }
    public void run() {
        while (!MainPanel.IsPaused)
        {
                ev.setKeyCode(' ');
                Fire f = new Fire(mainPanel.r.x + mainPanel.r.width / 2, mainPanel.r.y, Color.black, program.mp);
                mainPanel.fire_list.add(f);
                f.start();
                try {
                    Thread.sleep(75);
                } catch (InterruptedException t) {
                    t.printStackTrace();
                }
        }
    }
}
