package gameObjects;

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

    public void detect(){
        if(willCollideWith(panelBoundaries)){
            ball.bounceOffBoundaries(panelBoundaries);
        }
        if(willCollideWith(player)){
            ball.bounceOffPlayer(player);
        }
        if(willCollideWith(wall)){}

    }

//    if it doesn't collide, how much distance left till next collision
    public boolean willCollideWith(Rectangle obj){
        boolean willCollide = false;
        Rectangle nextPos = ball.getNextPosition();
        if(obj instanceof Wall){
            List<List<Brick>> bricks = ((Wall) obj).getBricks();
            for (List<Brick> row : bricks) {
                for (Brick brick : row) {
//                    needs adjustment
                    willCollide = nextPos.intersects(brick.getBounds());
                    if(willCollide) break;
                }
            }
        }
        else if(obj instanceof Player){
            willCollide = nextPos.intersects(obj.getBounds()) || nextPos.y+ball.height == obj.y;
        }else if(obj == panelBoundaries){
            willCollide = nextPos.x <= panelBoundaries.x || nextPos.y <= panelBoundaries.y ||
                    nextPos.x+ball.width >= panelBoundaries.width;
        }
        return willCollide;
    }
}
