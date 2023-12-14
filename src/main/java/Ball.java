import java.awt.*;

public class Ball {

    private Coordinate c;
    private int ballSize;
    private Color color;

    public Ball(Coordinate c, int ballSize, Color color) {
        this.c = c;
        this.ballSize = ballSize;
        this.color = color;
    }

    public void move(int x, int y) {

    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(c.getX(), c.getY(), ballSize, ballSize);
    }
}
