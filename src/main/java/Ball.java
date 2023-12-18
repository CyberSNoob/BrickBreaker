import java.awt.*;
import java.util.Random;

public class Ball {

    private Coordinate c;
    private int ballSize;
    private int velocity;
    private Color color;
    private Coordinate direction;

    public Ball(Rectangle zone, int ballSize, int velocity, Color color) {
        this.ballSize = ballSize;
        this.velocity = velocity;
        int padding = 2 * this.ballSize;
        Random r = new Random();
        this.c = new Coordinate(
                r.nextInt(padding, zone.width - ballSize - padding),
                r.nextInt(zone.y, zone.y + zone.height - ballSize));
        this.color = color;
        this.direction = new Coordinate(
                r.nextBoolean() ? ballSize : -ballSize,
                r.nextBoolean() ? -ballSize : +ballSize);
    }

    public void move(Rectangle boundaries) {
        c.setX(c.getX() + direction.getX());
        if(c.getX() <= boundaries.x) {
            c.setX(boundaries.x);
            direction.setX(Math.abs(direction.getX()));
        }else if(c.getX() + ballSize >= boundaries.width){
            c.setX(boundaries.width - direction.getX() - ballSize);
            direction.setX(-direction.getX());
        }

        c.setY(c.getY() + direction.getY());
        if(c.getY() <= boundaries.y){
            c.setY(boundaries.y);
            direction.setY(Math.abs(direction.getY()));
        }else if(c.getY() + ballSize >= boundaries.height){
            c.setY(boundaries.height - direction.getY() - ballSize);
            direction.setY(-direction.getY());
        }
    }

    public void draw(Graphics2D g) {
        g.setColor(color);
        g.fillOval(c.getX(), c.getY(), ballSize, ballSize);
    }

    public void increaseVelocity(int velocity) {
        this.velocity = velocity;
    }

    public int getVelocity() {
        return velocity;
    }
}
