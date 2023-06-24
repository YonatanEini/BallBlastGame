import com.sun.tools.javac.Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.io.IOException;

import java.util.Objects;

public class StartMenu extends JFrame implements ActionListener{
    JFrame frame1 = new JFrame();
    public StartMenu( ){
        ImageIcon ii = new ImageIcon("Images/Background/menu-background.jpg");
        Image img = ii.getImage();
        frame1.setLocation(550,300);
        frame1.setSize(500, 250);
        Container mainP = frame1.getContentPane();
        mainP.setBackground(Color.white);
        mainP.setLayout(null);
        TextField t1= new TextField();
        t1.setFont(new Font("Arial", Font.BOLD, 20));
        JLabel titleL = new JLabel("WELCOME TO BallBlast!");
        titleL.setForeground(Color.BLACK);
        JLabel label = new JLabel();
        label.setIcon(new ImageIcon(img));


        JButton startB = new JButton("Single Player");
        startB.setFont(new Font("Arial", Font.BOLD, 15));

        JButton exitB = new JButton("Multi Player");
        exitB.setFont(new Font("Arial", Font.BOLD, 15));



        mainP.add(titleL);
        titleL.setFont(new Font("Chiller", Font.BOLD, 35));
        titleL.setBounds(100, 30, 300, 100);

        mainP.add(startB);
        startB.setMnemonic(KeyEvent.VK_S);
        startB.setBounds(70, 120, 170, 20);
        startB.setBackground(Color.black);
        startB.setForeground(Color.white);


        mainP.add(exitB);
        exitB.setMnemonic(KeyEvent.VK_E);
        exitB.setBounds(250, 120, 170, 20);
        exitB.setBackground(Color.black);
        exitB.setForeground(Color.white);
        mainP.add(label);
        label.setBounds(0,0,500,250);

        startB.addActionListener(this);
        exitB.addActionListener(this);
        frame1.setVisible(true);
        frame1.setResizable(false);
    }
    public void actionPerformed(ActionEvent e) {
        String key = e.getActionCommand();
        if (key == "Single Player") {
            MainPanel.IsSinglePlayer = true;
        }
        else {
            MainPanel.IsSinglePlayer = false;
        }
        frame1.dispose();
        program.decision = true;
    }
}
