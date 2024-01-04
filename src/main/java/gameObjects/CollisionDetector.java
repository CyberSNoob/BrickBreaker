package gameObjects;

import dataClasses.Coordinate;
import gameWindow.GamePane;

import java.awt.*;
import java.util.List;
import java.util.Map;

public class CollisionDetector {

    private final Rectangle panelBoundaries;
    private Wall wall;
    private Player player;
    private Ball ball;
    public CollisionDetector(Rectangle gamePaneSize, Map<String, Rectangle> gc) {
        this.panelBoundaries = gamePaneSize;
        this.wall = (Wall) gc.get("wall");
        this.player = (Player) gc.get("player");
        this.ball = (Ball) gc.get("ball");
    }

    public void checkForCollision(){
        if(willCollideWith(player)){
            ball.bounceOffPlayer(player);
        }else {
            ball.bounceOffBoundaries(panelBoundaries);
        }

    }

    public boolean willCollideWith(Rectangle obj){
        boolean willCollide = false;
        if(obj instanceof Wall){
            List<List<Brick>> bricks = ((Wall) obj).getBricks();
            for (List<Brick> row : bricks) {
                for (Brick brick : row) {
                    willCollide = ball.nextPosition().intersects(brick.getBounds());
                }
            }
        }
//        else if(obj instanceof Player){
//            willCollide = ball.nextPosition().intersects(obj.getBounds());
//        };
        return willCollide;
    }
}
