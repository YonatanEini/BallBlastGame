import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RecceiveDataDto implements Serializable {
    private static final long serialVersionUID =1L;
    public  boolean IsPaused;
    public Point cannonPos;
    public List<Point> fire_listXY = new ArrayList<Point>();
    public List<BallDto> current_ballList;
    public boolean IsGameOver;
    public int score;
    public boolean NewGameRequest;
    public RecceiveDataDto(){
        this.IsPaused = false;
        this.cannonPos = new Point();
        this.fire_listXY = new ArrayList<>();
        this.current_ballList = new ArrayList<>();
        this.IsGameOver = false;
        this.score = 0;
        this.NewGameRequest = false;
    }
}
