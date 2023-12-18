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
//        this.direction = new Coordinate(
//                r.nextBoolean() ? ballSize : -ballSize,
//                r.nextBoolean() ? -ballSize : +ballSize);
        this.direction = new Coordinate(
                r.nextBoolean() ? ballSize : -ballSize, ballSize);
        System.out.println(c.toString());
    }

    public void move(Rectangle boundaries) {
        int nextXPos = c.getX() + direction.getX();
        int nextYPos = c.getY() + direction.getY();
        int margin = 5;

        c.setX(nextXPos);
        if(nextXPos <= boundaries.x || nextXPos <= margin) {
            c.setX(boundaries.x);
            direction.setX(Math.abs(direction.getX()));
        }else if(nextXPos + ballSize > boundaries.width || (boundaries.width - nextXPos) - ballSize <= margin){
            c.setX(boundaries.width - ballSize);
            direction.setX(-direction.getX());
        }

        c.setY(nextYPos);
        if(nextYPos < boundaries.y || nextYPos <= margin){
            c.setY(boundaries.y);
            direction.setY(Math.abs(direction.getY()));
        }else if(nextYPos + ballSize > boundaries.height || (boundaries.height - nextYPos) - ballSize <= margin){
            System.out.println("Called Y once");
            c.setY(boundaries.height - ballSize);
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

    public Coordinate getCoordinate() {
        return c;
    }

    public int getBallSize() {
        return ballSize;
    }
}
