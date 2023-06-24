import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import javazoom.jl.player.Player;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class program {
    // panel and frame
    public static JFrame f=new JFrame("Ball Blast");
    public static MainPanel mp;
    // wait to see the GameMode
    public static boolean decision = false;
    // multiplayer connection objects
    public static Socket connectionToServer;
    public static OutputStream outputStream;
    public static InputStream inputStream;

    public static void main(String[] args) throws IOException {

        StartMenu menu = new StartMenu();
        while (!decision) {
            System.out.println();
        }
        InitializeGameFrame();
        if (!MainPanel.IsSinglePlayer){
            InitializeMultiplayerMode();
        }
    }
    // sends data to server if GameMode = Multiplayer
    static class SendingDataToSever{
        public SendingDataToSever() {}
        ActionListener changeScore = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                    Gson gson = new Gson();
                    ArrayList<Point> points = new ArrayList<Point>();
                    points.add(new Point(mp.r.x, mp.r.y));
                    List<Point> fireDto = CreteFireSendList(mp.fire_list);
                    DataDto data = new DataDto(MainPanel.IsPaused, new Point(mp.r.x, mp.r.y), fireDto, MainPanel.IsGameOver, mp.score, MainPanel.IsNewGameRequest);
                    String jsonStr = gson.toJson(data);
                    try {
                        ObjectOutputStream objectTransfer = new ObjectOutputStream(outputStream);
                        objectTransfer.writeObject(jsonStr);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        };
    }
    // receive other client data from server if GameMode = Multiplayer
    static class ReceiveDataToSever{
        public ReceiveDataToSever() {}
        ActionListener secondClientData = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (!MainPanel.IsGameOver) {
                    try {
                        ObjectInputStream objectTransfer = new ObjectInputStream(inputStream);
                        Type receiveDataDtoType = new TypeToken<RecceiveDataDto>() {
                        }.getType();
                        RecceiveDataDto outputContent = new Gson().fromJson((String) objectTransfer.readObject(), receiveDataDtoType);
                        if (MainPanel.IsNewGameRequest){
                            if (outputContent.NewGameRequest){
                                MainPanel.IsNewGameRequest = false;
                                MainPanel.IsPaused = false;
                                System.out.println(outputContent.IsPaused + " " + outputContent.current_ballList + " " + outputContent.cannonPos);
                                mp.otherClientData = outputContent;
                                mp.repaint();
                            }
                        }
                        else {
                            if (outputContent.IsPaused) {
                                MainPanel.IsPaused = true;
                            }
                            if (outputContent.IsGameOver && !MainPanel.IsGameOver) {
                                MainPanel.IsGameOver = true;
                                EndGameMenu endGame = new EndGameMenu(mp.score, mp);
                            }
                            mp.otherClientData = outputContent;
                            mp.repaint();
                        }


                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                }
            }
        };
    }
    // converting Fire List to Dto (XY Points)
    public static List<Point> CreteFireSendList(List<Fire> fires)
    {
        List<Point> fireList = new ArrayList<>();
        for (int i=0; i<fires.size();i++)
        {
            Point currentFirePosition = new Point(fires.get(i).x, fires.get(i).y);
            fireList.add(currentFirePosition);
        }
        return fireList;
    }
    public static void InitializeGameFrame(){
        mp = new MainPanel();
        f.setResizable(false);
        f.add(mp);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(1295,700);
        MainPanel.r = new Cannon(mp, Color.black);
        MainPanel.r.start();
        f.setVisible(true);
        f.setFocusable(false);
        mp.hideMouseCursor();
    }
    public static void InitializeMultiplayerMode(){
        try {
            connectionToServer = new Socket("localhost", 8080);
            outputStream = connectionToServer.getOutputStream();
            inputStream = connectionToServer.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        SendingDataToSever sendingData = new SendingDataToSever();
        javax.swing.Timer t = new javax.swing.Timer(35, sendingData.changeScore);
        t.start();
        ReceiveDataToSever receivingData = new ReceiveDataToSever();
        javax.swing.Timer receiveDataTimer = new javax.swing.Timer(35, receivingData.secondClientData);
        receiveDataTimer.start();
    }
    public static class Mp3Player {
        private final String filename;
        public Mp3Player(String filename) {
            this.filename = filename;
        }

        public void play() {
            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            BufferedInputStream buffer = new BufferedInputStream(
                                    new FileInputStream(filename));
                            Player player = new Player(buffer);
                            player.play();
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    }
                }
            });
            t1.start();
        }

    }

}