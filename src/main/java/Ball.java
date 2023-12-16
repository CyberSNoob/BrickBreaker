import java.awt.*;
import java.util.Random;

public class Ball {

    private Coordinate c;
    private int ballSize;
    private int velocity;
    private Color color;

    public Ball(Coordinate c, int ballSize, Color color) {
        this.c = c;
        this.ballSize = ballSize;
        this.color = color;
    }

    public Ball(Rectangle zone, int ballSize, Color color) {
        this.ballSize = ballSize;
        int padding = 2 * this.ballSize;
        Random r = new Random();
        this.c = new Coordinate(r.nextInt(padding, zone.width - ballSize - padding),
                r.nextInt(zone.y, zone.y + zone.height - ballSize));
        this.color = color;
    }

    public void move(Coordinate c) {

    }

    public void draw(Graphics2D g) {
        g.setColor(color);
        g.fillOval(c.getX(), c.getY(), ballSize, ballSize);
    }
}
