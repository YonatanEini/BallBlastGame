

import com.sun.tools.javac.Main;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.Serializable;

import javax.swing.ImageIcon;
import javax.swing.JEditorPane;

public class Cannon extends Thread  {
    public  int x,y,width,high;
    public Color color;
    public MainPanel panel;
    public Image i;
    public String playerName;

    public Cannon(MainPanel p,Color color)
    {
        ImageIcon ii=new ImageIcon("Images/Cannon/cannon.png");
        i=ii.getImage();
        x=250;
        width=100;
        high=125;
        y=program.f.getHeight()-this.high-25;
        this.color=color;
        panel=p;
        this.playerName = "You";
    }
    public Cannon(Point p1, MainPanel panel){
        ImageIcon ii=new ImageIcon("Images/Cannon/cannon.png");
        this.i = ii.getImage();
        this.x = p1.x;
        this.y = p1.y;
        this.width = 100;
        this.high = 125;
        this.y = program.f.getHeight()-this.high-25;
        this.panel = panel;
        this.playerName = "Friend";
    }
    public void draw(Graphics g)
    {
        y=program.f.getHeight()-this.high-25;
        g.drawImage(i, x, y, this.width, this.high, null);
        Font f1= new Font("Arial",Font.BOLD,20);
        g.setFont(f1);
        g.setColor(Color.BLACK);
        g.drawString( this.playerName, this.x-15,this.y+15); //where to put the ball string

    }
    public void run(){
        while(true)
        {
            for (int i=0;i<panel.ball_list.size();i++)
            {
                if(!MainPanel.IsPaused && IsCollision(panel.ball_list.get(i))) {
                    MainPanel.IsPaused = true;
                    EndGameMenu menu = new EndGameMenu(this.panel.score, this.panel);
                }
            }
            for (int i=0;i<panel.otherClientData.current_ballList.size();i++)
            {
                if(!MainPanel.IsPaused && IsCollisionBallDTO(panel.otherClientData.current_ballList.get(i))) {
                    MainPanel.IsPaused = true;
                    MainPanel.IsGameOver = true;
                    EndGameMenu menu = new EndGameMenu(this.panel.score, this.panel);
                }
            }
            try {
                Thread.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            panel.repaint();
        }
    }
    public boolean IsCollision( Ball b)
    {
        if(b!= null)
        {
            if((b.x+b.width/2 - 40 >= this.x) && (b.x-b.width/2 + 35  <= this.x+this.width) && (b.y+b.width/2 -22 >=this.y) && (b.y-b.width/2 -22<= this.y+ this.high) )
                return true;
            return false;
        }
        return false;
    }
    public boolean IsCollisionBallDTO( BallDto b)
    {
        if(b!= null)
        {
            if((b.CurrentPosition.x+b.Width/2 - 40 >= this.x) && (b.CurrentPosition.x-b.Width/2 + 35  <= this.x+this.width) &&
                                (b.CurrentPosition.y+b.Width/2 -22 >=this.y) && (b.CurrentPosition.y-b.Width/2 -22<= this.y+ this.high) )
                return true;
            return false;
        }
        return false;
    }
}