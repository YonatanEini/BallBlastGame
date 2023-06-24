import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.List;

public class Fire extends Thread {
    public Image i;
    int x,y,size=20;
    public Color color;
    public MainPanel panel;
    public Fire(int x,int y,Color color, MainPanel p) {
        ImageIcon ii=new ImageIcon("Images/Bullets/bullets.gif");
        i=ii.getImage();
        this.x=x;
        this.y=y;
        this.color=color;
        this.panel=p;
    }
    public void draw(Graphics g){
        g.drawImage(i, x-15, y, 25, 25, null);

    }
    public void run(){
        boolean is_alive=true;
        int dir_y=1;
        while (is_alive) {
            if (y - size / 2 < 0 )
                break;
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(!MainPanel.IsPaused)
                this.y -= dir_y;
            for (int i = 0; i < panel.ball_list.size(); i++)
            {
                if((distance(x- panel.ball_list.get(i).x,y-panel.ball_list.get(i).y)<size/2+panel.ball_list.get(i).width/2)) {
                    panel.ball_list.get(i).HP -= 1;
                    if(panel.ball_list.get(i) instanceof SpecialBall)
                    {
                        panel.ball_list.get(i).width+=1;
                    }
                    is_alive=false;
                }
            }
            List<BallDto> currentBallList = panel.otherClientData.current_ballList;
            for (int i =0; i<currentBallList.size(); i++){
                if((distance(x- currentBallList.get(i).CurrentPosition.x,y-currentBallList.get(i).CurrentPosition.y)<size/2+currentBallList.get(i).Width/2)) {
                    is_alive=false;
                    break;
                }
            }
            panel.repaint();

        }
    }
    public  double distance(int a,int b){
        return Math.sqrt(Math.pow(a,2.0)+Math.pow(b,2.0));
    }

}