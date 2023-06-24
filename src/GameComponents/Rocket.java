
import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

public class Rocket extends Thread {
    boolean ON;
    Image i;
    public int x, y, width, depth;
    public MainPanel panel;
    public Rocket()
    {
        this.ON=false;
    }
    public Rocket(int x,int y,int width, int depth, MainPanel p) {
        this.ON=true;
        ImageIcon ii=new ImageIcon("Images/Bullets/rock.gif");
        i=ii.getImage();
        this.x=x;
        this.y=y;
        this.width=width;
        this.depth=depth;
        this.panel=p;
    }

    public void draw(Graphics g) {
        g.drawImage(i, x, y, this.width, this.depth, null);
    }

    public void run() {
        make_start_movement();
        while (true) {
            boolean flag = false;
            Ball to_expload = panel.ball_list.get(0);
            while (flag==false ) {
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(!MainPanel.IsPaused) {
                    if (to_expload.x > this.x)
                        this.x += 1;
                    if (to_expload.x < this.x)
                        this.x -= 1;
                    if (to_expload.y > this.y) {
                        this.y += 1;
                    }
                    if (to_expload.y < this.y)
                        this.y -= 1;
                    if ((distance(x - to_expload.x, y - to_expload.y) < width / 2 + to_expload.width / 2)) {
                        flag = true;
                        to_expload.HP -= 5;
                    }
                    HandleDir(to_expload);
                }
                panel.repaint();
            }
            if(flag)
                break;
        }
    }
    public void make_start_movement()
    {
        int total = 45;
        while (total>0) {
            this.y -= 1;
            try {
                Thread.sleep(4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            total-=1;
        }
    }
    public double distance(int a, int b) {
        return Math.sqrt(Math.pow(a, 2.0) + Math.pow(b, 2.0));
    }
    public void HandleDir(Ball to_expload)
    {
        if (to_expload.y - to_expload.width > this.y) {
            ImageIcon ii = new ImageIcon("Images/Bullets/rocket_back.gif");
            i = ii.getImage();
        }
        if (to_expload.y - to_expload.width < this.y) {
            ImageIcon ii = new ImageIcon("Images/Bullets/rock.gif");
            i = ii.getImage();
        }
        if(to_expload.x > this.x)
        {
            ImageIcon ii = new ImageIcon("Images/rocket_right.gif");
            i = ii.getImage();
        }
        if(to_expload.x > this.x)
        {
            ImageIcon ii = new ImageIcon("Images/rocket_right.gif");
            i = ii.getImage();
        }
        if(to_expload.x < this.x)
        {
            ImageIcon ii = new ImageIcon("Images/rocket_left.gif");
            i = ii.getImage();
        }
    }

}