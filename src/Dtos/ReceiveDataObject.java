import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ReceiveDataObject {
    public  boolean IsPaused;
    public Point cannonPos;
    public List<BallDto> currentBallsList;
    public List<Point> fire_listXY = new ArrayList<Point>();
    public List<Point> rocket_listXY = new ArrayList<Point>();
    public int score;
    public  ReceiveDataObject(boolean is, Point c, List<BallDto> balls, List<Point>Fires, List<Point> rockets, int score)
    {
        this.IsPaused = is;
        this.cannonPos = c;
        this.currentBallsList = balls;
        this.fire_listXY = Fires;
        this.rocket_listXY = rockets;
        this.score = score;
    }

}
