import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class EndGameMenu  extends JFrame implements ActionListener {
    JFrame frame1 = new JFrame();
    MainPanel GamePanel;
    public EndGameMenu(int finalScore, MainPanel panel){
        this.GamePanel = panel;
        ImageIcon ii = new ImageIcon("Images/Background/menu-background.jpg");
        Image img = ii.getImage();
        frame1.setLocation(550,300);
        frame1.setSize(500, 250);
        Container mainP = frame1.getContentPane();
        mainP.setBackground(Color.white);
        mainP.setLayout(null);
        TextField t1= new TextField();
        t1.setFont(new Font("Arial", Font.BOLD, 20));
        JLabel titleL = new JLabel("Game Over!");
        titleL.setForeground(Color.BLACK);
        JLabel scoreTitle = new JLabel("Score: " + finalScore);
        scoreTitle.setForeground(Color.BLACK);
        JLabel label = new JLabel();
        label.setIcon(new ImageIcon(img));

        JButton startB = new JButton("New Game!");
        startB.setFont(new Font("Arial", Font.BOLD, 15));




        mainP.add(titleL);
        titleL.setFont(new Font("TimesRoman", Font.BOLD, 30));
        titleL.setBounds(180, 0, 300, 100);

        mainP.add(scoreTitle);
        scoreTitle.setFont(new Font("TimesRoman", Font.BOLD, 30));
        scoreTitle.setBounds(180, 80, 300, 50);

        mainP.add(startB);
        startB.setMnemonic(KeyEvent.VK_S);
        startB.setBounds(170, 150, 170, 20);
        startB.setBackground(Color.black);
        startB.setForeground(Color.white);


        mainP.add(label);
        label.setBounds(0,0,500,250);

        startB.addActionListener(this);

        frame1.setVisible(true);
        frame1.setResizable(false);
    }
    public void actionPerformed(ActionEvent e) {
        
        if (MainPanel.IsSinglePlayer){
            MainPanel.IsPaused = false;
            this.GamePanel.fire_list.clear();
            this.GamePanel.ball_list.clear();
            this.GamePanel.score = 0;
            this.GamePanel.IsFirst = true;
        }
        else{
            this.GamePanel.fire_list.clear();
            this.GamePanel.ball_list.clear();
            this.GamePanel.score = 0;
            this.GamePanel.IsFirst = true;
            MainPanel.IsNewGameRequest = true;
            this.GamePanel.otherClientData = new RecceiveDataDto();
            MainPanel.IsGameOver = false;
            MainPanel.IsPaused = false;
        }
        frame1.dispose();
    }
}
