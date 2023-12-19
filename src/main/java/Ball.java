import java.awt.*;
import java.util.Random;

public class Ball extends Rectangle{

    private final int BALL_SIZE = 20;
    private int velocity;
    private final Color color;
    private Coordinate direction;
    private final Random r = new Random();

    public Ball(Rectangle zone, int velocity, Color color) {
        this.velocity = velocity;
        int padding = 2 * BALL_SIZE;
//        TODO: set ball moving towards player
        int randomX = r.nextInt(padding, zone.width - BALL_SIZE - padding);
        int randomY = r.nextInt(zone.y, zone.y + zone.height - BALL_SIZE);
        setBounds(randomX, randomY, BALL_SIZE, BALL_SIZE);
        this.color = color;
        setInitialDirection();
    }

    public void move(Rectangle panelBoundaries, Player player) {
        int nextXPos = this.x + direction.getX();
        int nextYPos = this.y + direction.getY();
        int maxY = player.y - BALL_SIZE;

        if(willCollideWithPlayer(player)){
            System.out.println(player.getBounds() + ", " + this);
            if(this.y >= maxY) direction.setY(-direction.getY());
            setLocation(nextXPos, maxY);
        }else{
            int margin = 5;
            Coordinate nextPos = new Coordinate(nextXPos, nextYPos);
            if(nextPos.getX() <= panelBoundaries.x || nextPos.getX() <= margin) {
                nextPos.setX(panelBoundaries.x);
                direction.setX(Math.abs(direction.getX()));
            }else if(nextPos.getX() + BALL_SIZE > panelBoundaries.width || (panelBoundaries.width - nextPos.getX()) - BALL_SIZE <= margin){
                nextPos.setX(panelBoundaries.width - BALL_SIZE);
                direction.setX(-direction.getX());
            }

            if(nextPos.getY() < panelBoundaries.y || nextPos.getY() <= margin){
                nextPos.setY(panelBoundaries.y);
                direction.setY(Math.abs(direction.getY()));
            }else if(nextPos.getY() + BALL_SIZE > panelBoundaries.height || (panelBoundaries.height - nextPos.getY()) - BALL_SIZE <= margin){
                nextPos.setY(panelBoundaries.height - BALL_SIZE);
            }
            setLocation(nextPos.getX(), nextPos.getY());
        }
    }

    private void setInitialDirection() {
//        this.direction = new Coordinate(
//                r.nextBoolean() ? ballSize : -ballSize,
//                r.nextBoolean() ? -ballSize : +ballSize);
        this.direction = new Coordinate(r.nextBoolean() ? BALL_SIZE : -BALL_SIZE, BALL_SIZE);
    }

    public void draw(Graphics2D g) {
        g.setColor(color);
        g.fillOval(this.x, this.y, BALL_SIZE, BALL_SIZE);
    }

    public boolean willCollideWithPlayer(Player player){
        Rectangle tempNextPos = this;
        tempNextPos.setLocation(tempNextPos.x+direction.getX(), tempNextPos.y+direction.getY());
        return tempNextPos.intersects(player.getBounds());
    }

    public int getBallSize() {
        return BALL_SIZE;
    }

}
