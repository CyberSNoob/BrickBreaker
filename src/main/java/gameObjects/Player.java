package gameObjects;

import dataClasses.Coordinate;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Set;
import java.util.function.Consumer;

public class Player extends Rectangle{

    private final Rectangle playerZone;
    private static Color color;
    private int directionX = 0;
    private Coordinate initialPosition;
    private int speed = 40;

    public Player(Rectangle playerZone, Color color){
        this.playerZone = playerZone;
        int two = 2, playerSizeRatio = 5;
        this.width = this.playerZone.width / playerSizeRatio;
        this.x = this.playerZone.width / two - this.width / two;
        this.height = this.playerZone.height / playerSizeRatio;
        this.y = this.playerZone.y + this.playerZone.height / two;
        Player.color = color;
        this.initialPosition = new Coordinate(this.x, this.y);
    }

//    public Consumer<ActionEvent> moveLeft(){
//        return e -> this.x = Math.max(this.x - directionX, 0);
//    }
//
//    public Consumer<ActionEvent> moveRight() {
//        return e -> {
//            int totalWidth = this.x + this.width + directionX;
//            this.x = totalWidth > this.playerZone.width ? this.playerZone.width - this.width : this.x + directionX;
//        };
//    }
//
//    public void move(Set<Integer> keyPressed){
//
//    }

//    same as player move
    public void update(){
        int nextPos = this.x + directionX;
        if (nextPos <= 0) {
            this.x = 0;
        }else if(nextPos+this.width > playerZone.width){
            this.x = this.playerZone.width - this.width;
        }else{
            this.x = nextPos;
        }
    }

    public void setXDirection(int direction){
        this.directionX = direction;
    }

    public void draw(Graphics2D g){
        g.setColor(color);
        g.fillRect(this.x, this.y, this.width, this.height);
        g.setStroke(new BasicStroke(1));
        g.setColor(Color.WHITE);
        g.drawRect(this.x, this.y, this.width, this.height);
    }

    public void setInitialPosition() {
        this.setLocation(initialPosition.getX(), initialPosition.getY());
    }

    public int getSpeed() {
        return speed;
    }
}
