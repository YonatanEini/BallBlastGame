
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.io.Serializable;
import java.text.AttributedString;
import java.util.Random;
import java.util.Timer;

class Ball extends Thread {
    public static  int BallID = 0;
    public int id;
    public int level;
    public  int x,y,width;
    public Color color;
    public MainPanel panel;
    public int HP;
    public int dir_x;
    public int dir_y;
    public Ball(int x,int y,int width,int level, int hp, MainPanel p){
        this.x=x;
        this.y=y;
        this.width=width;
        this.panel=p;
        this.HP=hp;
        this.level = level;
        this.dir_x = 1;
        this.dir_y = 1;
        this.id = BallID++;
        this.color = Create_Random_Color();
    }
    public Ball(int x,int y,int width,int level, int hp,int dir_x, int dir_y, MainPanel p){
        this.x=x;
        this.y=y;
        this.width=width;
        this.panel=p;
        this.HP=hp;
        this.level = level;
        this.dir_x = dir_x;
        this.dir_y = dir_y;
        this.color = Create_Random_Color();

      }
    public void draw(Graphics g){
        g.setColor(color);
        g.fillOval(x-width/2,y-width/2,width,width);
        Font f1= new Font("Arial",Font.BOLD,20);
        g.setFont(f1);
        g.setColor(Color.BLACK);
        g.drawString(String.valueOf(HP), this.x-5,this.y+5); //where to put the ball string
    }

    public void run(){
       this.setPriority(1);
        int dirx=this.dir_x,diry=this.dir_y;
        Make_start_movement();
        while (true)
        {
            if(this.HP<=0 ) {
                if(level == 1)
                {
                    ChangeGameScore changeScore = new ChangeGameScore(5, this.panel);
                    changeScore.start();
                }
                else {
                    SplitBall();
                }
                break;
            }
            int h=panel.getHeight();
            int w=panel.getWidth();


            if (x + width/2  > w)
                dirx = -1;

            if (x-width/2  < 0)
                dirx = 1;

            if (y + width/2  > h) {
               int x= HandleGroundHit(dirx);
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                diry=1;
                dirx= x;
            }
            if (y -width/2 < 0)
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
    public void Make_start_movement()
    {
        int dir_x;
        if (this.x==panel.getWidth())
            dir_x=-1;
        else
            dir_x =1;
        if (dir_x==1) {
            while (this.x - this.width - 10 < 0) {
                this.x += dir_x;
                try {
                    Thread.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        else
        {
            while (this.x + this.width + 10 < panel.getWidth()) {
                this.x += dir_x;
                try {
                    Thread.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    public int HandleGroundHit(int dir)
    {
        int dir_x=0;
        if(level > 0)
        {
            int IncX=2;
            dir_x=dir;
            while(this.y >= (program.f.getHeight()/3))
            {
                if (x + width/2  > program.f.getWidth())
                    dir_x = -1;
                if (x-width/2  < 0)
                    dir_x = 1;
                if(!MainPanel.IsPaused) {
                    this.y -= 1;
                    if (IncX == 0) {
                        this.x += dir_x;
                        IncX = 2;
                    } else
                        IncX--;

                    try {
                        Thread.sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            panel.repaint();
            }
        }
        return dir_x;
    }
    public void SplitBall()
    {
        Ball ball_right = new Ball(this.x - this.width, this.y+10, 35,this.level -1 ,Random_easy_HP() ,-1, 1, program.mp);
        Ball ball_left = new Ball(this.x + this.width, this.y+10, 35,this.level -1,Random_easy_HP() ,1, 1,program.mp);
        ball_left.start();
        ball_right.start();
        panel.ball_list.add(ball_right);
        panel.ball_list.add((ball_left));
    }

    public boolean IsCollision(Cannon check) {
        if ((x + width / 2 >= check.x) && (x - width / 2 <= check.x + check.width) && (y + width / 2 >= check.y) && (y - width / 2 <= check.y + check.high))
            return true;
        return false;
    }
    public Color Create_Random_Color()
    {
        Random rnd = new Random();
        Color ball_color = new Color(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        return ball_color;
    }
    public int Random_easy_HP()
    {
        Random random = new Random();
        int x = random.nextInt(6);
        return x + 5;
    }
    public Image draw_bomb()
    {
        ImageIcon fire_work_gif = new ImageIcon("nemzetiünnep-fireworks.gif");
        Image fire_work_image = fire_work_gif.getImage();
        return fire_work_image;
    }

}