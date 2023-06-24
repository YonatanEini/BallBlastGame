import java.io.Serializable;

public class ChangeGameScore extends Thread implements Serializable {
    public int score;
    MainPanel mainPanel;
    public  ChangeGameScore()
    {
        this.score = 0;
        this.mainPanel = null;
    }
    public ChangeGameScore(int add, MainPanel panel)
    {
        this.score = add;
        this.mainPanel = panel;
    }
    public void run() {
        for (int i=0; i<score; i++)
        {
            synchronized (this) {
                if (!MainPanel.IsPaused && (MainPanel.IsNewGameRequest == this.mainPanel.otherClientData.NewGameRequest))
                    mainPanel.score += 1;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
