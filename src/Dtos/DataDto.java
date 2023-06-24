import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DataDto implements Serializable {
    private static final long serialVersionUID =1L;
    public  boolean IsPaused;
    public  Point cannonPos;
    public java.util.List<Point> fire_listXY = new ArrayList<Point>();
    public int score;
    public boolean IsGameOver;
    public boolean NewGameRequest;
    public DataDto(boolean is, Point c, List<Point>Fires, boolean gameOver, int score, boolean request) {
        this.IsPaused = is;
        this.cannonPos = c;
        this.fire_listXY = Fires;
        this.IsGameOver = gameOver;
        this.score = score;
        this.NewGameRequest = request;
    }
}
