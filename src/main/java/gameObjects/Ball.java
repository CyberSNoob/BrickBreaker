package gameObjects;

import dataClasses.Coordinate;

import java.awt.*;
import java.util.List;
import java.util.Random;

public class Ball extends Rectangle{

    private final int BALL_SIZE = 20;
    private Color color;
    private Coordinate direction;
    private final Random r = new Random();

    public Ball(Rectangle zone, Color color) {
        setRandomPosition(zone);
        setInitialDirection();
        this.color = color;
    }

    public Ball(Rectangle zone){
        this(zone, Color.YELLOW);
    }

    private void setRandomPosition(Rectangle zone){
        int padding = 2 * BALL_SIZE;
//        TODO: set ball moving towards player
        int randomX = r.nextInt(padding, zone.width - BALL_SIZE - padding);
        int randomY = r.nextInt(zone.y, zone.y + zone.height - BALL_SIZE);
        setBounds(randomX, randomY, BALL_SIZE, BALL_SIZE);
    }

//    TODO: player side and corners detection
    public void move(Rectangle panelBoundaries, Player player) {
        int nextXPos = this.x + direction.getX();
        int nextYPos = this.y + direction.getY();
        int maxY = player.y - BALL_SIZE;

        if(willCollideWith(player)){
            System.out.println(player.getBounds().getLocation() + ", " + getLocation());
            if(this.y >= maxY) direction.setY(-direction.getY());
            setLocation(nextXPos, maxY);
        }
        else{
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
//        this.direction = new dataClasses.Coordinate(
//                r.nextBoolean() ? ballSize : -ballSize,
//                r.nextBoolean() ? -ballSize : +ballSize);
        this.direction = new Coordinate(r.nextBoolean() ? BALL_SIZE : -BALL_SIZE, BALL_SIZE);
    }

    public void draw(Graphics2D g) {
        g.setColor(color);
        g.fillOval(this.x, this.y, BALL_SIZE, BALL_SIZE);
    }

    public boolean willCollideWith(Rectangle obj){
        return nextPosition().intersects(obj.getBounds());
    }

    private Rectangle nextPosition(){
        Rectangle tempNextPos = this;
        tempNextPos.setLocation(tempNextPos.x+direction.getX(), tempNextPos.y+direction.getY());
        return tempNextPos;
    }

    public boolean outOfBound(int panelHeight){
        return this.y >= panelHeight - BALL_SIZE;
    }

    public boolean WillCollideWith(Wall wall){
        boolean willCollide = false;
        List<List<Brick>> bricks = wall.getBricks();
        for (List<Brick> row : bricks) {
            for (Brick brick : row) {
                willCollide = nextPosition().intersects(brick.getBounds());
            }
        }
        return willCollide;
    }

}
