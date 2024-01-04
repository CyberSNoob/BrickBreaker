package gameObjects;

import dataClasses.Coordinate;

import java.awt.*;
import java.util.Random;

public class Ball extends Rectangle{

    private Color color;
    private Coordinate direction;
    private final Random r = new Random();

    public Ball(Rectangle zone, Color color) {
        setRandomPosition(zone);
        int size = 20;
        this.setSize(size, size);
        this.color = color;
        setInitialDirection();
    }

    public Ball(Rectangle zone){
        this(zone, Color.YELLOW);
    }

    private void setInitialDirection() {
//        this.direction = new dataClasses.Coordinate(
//                r.nextBoolean() ? ballSize : -ballSize,
//                r.nextBoolean() ? -ballSize : +ballSize);
        this.direction = new Coordinate(r.nextBoolean() ? width : -width, height);
    }

    private void setRandomPosition(Rectangle zone){
        int padding = 2 * width; // or height, is the same
//        TODO: set ball moving towards player
        int randomX = r.nextInt(padding, zone.width - width - padding);
        int randomY = r.nextInt(zone.y, zone.y + zone.height - height);
        setBounds(randomX, randomY, width, height);
    }

    public Rectangle nextPosition(){
        Rectangle nextPos = this;
        nextPos.translate(direction.getX(), direction.getY());
        return nextPos;
    }

    public void bounceOffPlayer(Player player){
        int nextXPos = this.nextPosition().x;
        int maxYPos = player.y - this.height;
        if(this.y >= maxYPos) direction.setY(-direction.getY());
        this.translate(nextXPos, maxYPos);
    }

    public void bounceOffBoundaries(Rectangle panelBoundaries){
        int margin = 5;
        Point nextPos = nextPosition().getLocation();
        if(nextPos.x <= panelBoundaries.x || nextPos.x <= margin) {
            nextPos.x = panelBoundaries.x;
            direction.setX(Math.abs(direction.getX()));
        }else if(nextPos.x + width > panelBoundaries.width || (panelBoundaries.width - nextPos.x) - width <= margin){
            nextPos.x = panelBoundaries.width - width;
            direction.setX(-direction.getX());
        }

        if(nextPos.y < panelBoundaries.y || nextPos.y <= margin){
            nextPos.y = panelBoundaries.y;
            direction.setY(Math.abs(direction.getY()));
        }else if(nextPos.y + height > panelBoundaries.height || (panelBoundaries.height - nextPos.y) - height <= margin){
            nextPos.y = panelBoundaries.height - height;
        }
        this.setLocation(nextPos.x, nextPos.y);
    }

//    TODO: player side and corners detection
    public void move() {
        this.translate(direction.getX(), direction.getY());
    }

    public boolean outOfBound(int panelHeight){
        return this.y >= panelHeight - this.height;
    }

    public void draw(Graphics2D g) {
        g.setColor(color);
        g.fillOval(this.x, this.y, this.width, this.height);
    }

}
