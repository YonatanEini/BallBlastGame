import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

public class SpecialBall extends Ball implements Runnable, Serializable {
    public Image i;

    public SpecialBall(int x, int y, int width,int level ,int hp, MainPanel p) {
        super(x, y, width,level ,hp, p);
        ImageIcon ii=new ImageIcon("Images/SpecialBall/S_Ball_new.png");
        i=ii.getImage();
    }
    public void draw(Graphics g){
        if(this.HP<25)
        {
            ImageIcon ii=new ImageIcon("Images/SpecialBall/S_Ball_Broken.png");
            i=ii.getImage();
        }
        g.drawImage(i, x, y, this.width, this.width, null);
    }
    public void run(){
        int dirx=1,diry=1;
        Make_start_movement();
        while (true)
        {
            if(this.HP<=0) {
                break;
            }
            int h=panel.getHeight();
            int w=panel.getWidth();

            if (x + width/2 -5 > w)
                dirx = -1;

            if (x-width/2  < 0)
                dirx = 1;

            if (y + width/2 > h) {
                int x= HandleGroundHit(dirx);
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                diry=1;
                dirx= x;
            }
            if (y -width/2 - 5 < 0)
                diry = 1;
            if(!MainPanel.IsPaused) {
                x += dirx;
                y += diry;
            }
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            panel.repaint();
        }
    }
}
