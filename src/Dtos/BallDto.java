import org.w3c.dom.css.RGBColor;

import java.awt.*;
import java.io.Serializable;

public class BallDto implements Serializable {
    public Point CurrentPosition;
    public int Hp;
    public int Width;
    public int [] RgbColors;

    public BallDto(Point p1, int hp, int width, int[] rgb)
    {
        this.CurrentPosition = p1;
        this.Hp = hp;
        this.Width = width;
        this.RgbColors = rgb;

    }
    public void draw(Graphics g) {
        Color c = new Color(this.RgbColors[0],this.RgbColors[1],this.RgbColors[2]);
        g.setColor(c);
        g.fillOval(this.CurrentPosition.x-Width/2,this.CurrentPosition.y-Width/2,Width,Width);
        Font f1= new Font("Arial",Font.BOLD,20);
        g.setFont(f1);
        g.setColor(Color.BLACK);
        g.drawString(String.valueOf(this.Hp), this.CurrentPosition.x-5,this.CurrentPosition.y+5); //where to put the ball string
    }
}

