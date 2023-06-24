
import com.google.gson.Gson;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.*;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class MainPanel extends JPanel implements Serializable {
    public static boolean IsPaused=false;
    public static boolean IsGameOver = false;
    public static boolean IsSinglePlayer = true;
    public static boolean IsNewGameRequest = false;
    public Image bg1;
    public static Cannon r;
    public List<Ball> ball_list = new ArrayList<Ball>();
    public List<Fire> fire_list = new ArrayList<Fire>();
    public List<Rocket> rocket_list = new ArrayList<Rocket>();
    public RecceiveDataDto otherClientData = new RecceiveDataDto();
    public int score;
    public boolean IsFirst;
    public MainPanel()  {
        ImageIcon ii = new ImageIcon("Images/Background/background (1).jpg");
        bg1 = ii.getImage();
        addMouseMotionListener(new MML());
        addKeyListener(new KeyHandler(this));
        this.score = 0;
        setFocusable(true);
        ScoreTimer scoreTimer = new ScoreTimer(this);
        javax.swing.Timer t = new javax.swing.Timer(1000, scoreTimer.changeScore);
        t.start();
        this.IsFirst = true;
        if (IsSinglePlayer) {
            new AnnoyingBeep();
            program.Mp3Player mp3 = new program.Mp3Player("Music/game-music.mp3");
            mp3.play();
        }
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bg1, 0, 0, getWidth(), getHeight(), null);
        Font f = new Font(Font.DIALOG, Font.BOLD, 50);
        g.setFont(f);
        g.setColor(Color.WHITE);
        String score = "score: "+this.score;
        g.drawString(score, 1000, 50);
        r.draw(g);
        if (otherClientData.cannonPos.y != 0) {
            Cannon otherClientCannon = new Cannon(otherClientData.cannonPos, this);
            otherClientCannon.draw(g);
        }
        for (int i = 0; i < fire_list.size(); i++) {
            if (fire_list.get(i).isAlive())
                fire_list.get(i).draw(g);
            else
                fire_list.remove(fire_list.get(i));

        }
        for (int i = 0; i < rocket_list.size(); i++) {
            if (rocket_list.get(i).isAlive())
                rocket_list.get(i).draw(g);
            else {
                rocket_list.remove(rocket_list.get(i));
            }

        }
        for (int i = 0; i < ball_list.size(); i++) {
            if (ball_list.get(i).isAlive()){
                ball_list.get(i).draw(g);
        }
            else
                ball_list.remove(ball_list.get(i));

        }
        for (int i=0; i<otherClientData.current_ballList.size();i++){
            otherClientData.current_ballList.get(i).draw(g);
        }

        for (int i=0; i<otherClientData.fire_listXY.size(); i++){
            DrawFireBall(g, otherClientData.fire_listXY.get(i));
        }

    }

    class MML extends MouseMotionAdapter {
        public void mouseMoved(MouseEvent e) {
            if (MainPanel.IsSinglePlayer){
                if (e.getX() < getWidth() - r.width  && !MainPanel.IsPaused){
                    r.x = e.getX();
                }
            }
            else{
                if (e.getX() + r.width <= 635   && e.getX() < getWidth() - r.width  && !MainPanel.IsPaused)
                    r.x = e.getX();
            }
        }
    }
    private class KeyHandler extends KeyAdapter {
        MainPanel panel;
        public KeyHandler(MainPanel p)
        {
            this.panel = p;
        }
        public void keyPressed(KeyEvent ev) {
            if(panel.IsFirst)
            {
                FireBalls fireBall = new FireBalls(panel,ev);
                fireBall.start();
                panel.IsFirst = false;
            }
            if (ev.getKeyCode() == 65  && !MainPanel.IsPaused ) {
                Rocket rocket = new Rocket(r.x - r.width / 2 + 30, r.y - 50, 100, 100, program.mp);
                rocket_list.add(rocket);
                rocket.start();
                try {
                    Thread.sleep(45);
                } catch (InterruptedException t) {
                    t.printStackTrace();
                }
            }
            if(ev.getKeyCode() == 80)
            {
                IsPaused = !IsPaused;
                if(!IsPaused)
                {
                    FireBalls fireBall = new FireBalls(panel,ev);
                    fireBall.start();
                }
            }
        }
    }

    public class AnnoyingBeep {
        Toolkit toolkit;
        Timer timer;

        public AnnoyingBeep() {
            toolkit = Toolkit.getDefaultToolkit();
            timer = new Timer();
            timer.schedule(new RemindTask(),
                    0,        //initial delay
                    5 * 1000);  //subsequent rate
        }

        class RemindTask extends TimerTask {
            int counter=1;
            public void run() {
                if(IsPaused) {
                    counter = 1;
                    return;
                }
                Ball b1;
                int [] ballXY = make_random_number();
                if (counter%5 == 0){
                     b1 = new Ball(ballXY[0], ballXY[1], 80,2,Random_easy_HP() ,program.mp);
                }
                else {
                    if (counter%3 == 0) {
                         b1 = new Ball(ballXY[0], ballXY[1], 50, 1, Random_easy_HP() + 30, program.mp);
                    }
                    else {
                       b1 = new Ball(ballXY[0], ballXY[1], 50,1,Random_easy_HP() ,program.mp);
                    }
                }
                ball_list.add(b1);
                b1.start();
                counter++;
                try {
                    Thread.sleep(5);
                } catch (InterruptedException t) {
                    t.printStackTrace();
                }
            }
        }
    }

    public int[] make_random_number() // starting (x,y) to the balls
    {
        int values[] = new int[2];
        int x;
        Random random = new Random();
        x = random.nextInt(2);
        if(program.f.getHeight() > 0)
        {
            if (x == 1) {
                values[0] = 0;
                x = random.nextInt(program.f.getHeight() / 4);
                values[1] = x;
            } else {
                x = random.nextInt(program.f.getHeight() / 4);
                values[1] = x;
                values[0] = program.f.getWidth();
            }
        }
        else
        {
            if (x == 1) {
                values[0] = 0;
                x = random.nextInt(500 / 4);
                values[1] = x;
            } else {
                x = random.nextInt(500 / 4);
                values[1] = x;
                values[0] = program.f.getWidth();
            }
        }
        return values;
    }
    public void DrawFireBall(Graphics g, Point fireXY){
        ImageIcon ii=new ImageIcon("Images/Bullets/bullets.gif");
        Image i = ii.getImage();
        g.drawImage(i, fireXY.x-15, fireXY.y + 10, 25, 25, null);
    }

    public int Random_easy_HP()
    {
        Random random = new Random();
        int x = random.nextInt(21);
        return x + 10;
    }
    public void hideMouseCursor()
    {
        BufferedImage cursorImg= new BufferedImage(16,16,BufferedImage.TYPE_INT_ARGB);
        Cursor blankCursor =  Toolkit.getDefaultToolkit().createCustomCursor(cursorImg,new Point(0,0),"blank cursor");
        setCursor(blankCursor);
    }
    class ScoreTimer{
        MainPanel mainPanel;
        public ScoreTimer(MainPanel panel) {
            this.mainPanel = panel;
        }
        ActionListener changeScore = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                ChangeGameScore changeScore = new ChangeGameScore(1, mainPanel);
                changeScore.start();
            }
        };
    }

}