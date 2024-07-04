package gameObjects;

import dataClasses.Coordinate;

import java.awt.*;
import java.util.Random;

public class Ball extends Rectangle{
    private Color color;
    private final int SIZE = 20;
    private Coordinate direction;
    private final Random r = new Random();
    private Rectangle nextPosition;

    public Ball(Rectangle zone, Color color) {
        this.setSize(SIZE, SIZE);
        this.color = color;
        startPositionForTesting(zone);
//        setRandomPosition(zone);
//        setInitialDirection();
//        setNextPosition();
    }

    public Ball(Rectangle zone){
        this(zone, Color.YELLOW);
    }

    private Ball(Ball copy){
        setSize(SIZE, SIZE);
        color = copy.color;
        setLocation(copy.x, copy.y);
        direction = copy.direction;
        nextPosition = copy.nextPosition;
    }

    private void setInitialDirection() {
//        this.direction = new dataClasses.Coordinate(
//                r.nextBoolean() ? ballSize : -ballSize,
//                r.nextBoolean() ? -ballSize : +ballSize);
        this.direction = new Coordinate(r.nextBoolean() ? width : -width, height);
    }

    private void setRandomPosition(Rectangle zone){
        int padding = 3 * width; // or height, is the same
//        TODO: set ball moving towards player
        int randomX = r.nextInt(padding, zone.width - width - padding);
        int randomY = r.nextInt(zone.y, zone.y + zone.height - height);
        setBounds(randomX, randomY, width, height);
    }

    private void startPositionForTesting(Rectangle zone){
        int startX = zone.width/3;
        setLocation(startX, zone.y+zone.height/2);
        direction = new Coordinate(this.width, this.height);
        setNextPosition();
    }

    public void setNextPosition() {
        Rectangle nextPos = new Rectangle(this.x, this.y, this.width, this.height);
        nextPos.translate(direction.getX(), direction.getY());
        this.nextPosition = nextPos;
    }

    private void adjustNextPosition(Point p){
        nextPosition.setLocation(p);
    }

    public Rectangle getNextPosition(){
        return nextPosition;
    }


    public void bounceOffPlayer(Player player){
//        where: left,top, right
        direction.reverseY();
        int maxBallYToPlayer = player.y - this.height;
        nextPosition.setLocation(nextPosition.x, maxBallYToPlayer);
        adjustNextPosition(nextPosition.getLocation());
    }

    public void bounceOffBoundaries(Rectangle panelBoundaries){
        int margin = width/3; // width same as height
        Point nextPos = nextPosition.getLocation();
//        if exceeded or 15 left
        if(nextPos.x <= panelBoundaries.x || nextPos.x < margin) {
            nextPos.x = panelBoundaries.x;
            direction.reverseX();
        }else if(nextPos.x + width >= panelBoundaries.width || panelBoundaries.width - (nextPos.x - width) < margin){
            nextPos.x = panelBoundaries.width - width;
            direction.reverseX();
        }

        if(nextPos.y < panelBoundaries.y || nextPos.y < margin){
            nextPos.y = panelBoundaries.y;
            direction.reverseY();
        }else if(nextPos.y + height > panelBoundaries.height || panelBoundaries.height - (nextPos.y - height) < margin){
            nextPos.y = panelBoundaries.height - height;
        }
        adjustNextPosition(nextPos);
    }

    public void move() {
        this.setLocation(nextPosition.getLocation());
        setNextPosition();
    }

    public boolean outOfBound(int panelHeight){
        return this.y >= panelHeight - this.height;
    }

    public void draw(Graphics2D g) {
        g.setColor(color);
        g.fillOval(this.x, this.y, this.width, this.height);
    }

}
