import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.function.Consumer;

public class Player extends Rectangle{

    private Rectangle playerZone;
    private Color color;
    private int moveSpace = 60;

    public Player(Rectangle playerZone, Color color){
        this.playerZone = playerZone;
        int two = 2, playerSizeRatio = 5;
        this.width = this.playerZone.width / playerSizeRatio;
        this.x = this.playerZone.width / two - this.width / two;
        this.height = this.playerZone.height / playerSizeRatio;
        this.y = this.playerZone.y + this.playerZone.height / two;
        this.color = color;
    }

    public Consumer<ActionEvent> moveLeft(){
        return e -> this.x = Math.max(this.x - moveSpace, 0);
    }

    public Consumer<ActionEvent> moveRight() {
        return e -> {
            int totalWidth = this.x + this.width + moveSpace;
            this.x = totalWidth > this.playerZone.width ? this.playerZone.width - this.width : this.x + moveSpace;
        };
    }

    public void draw(Graphics2D g){
        g.setColor(color);
        g.fillRect(this.x, this.y, this.width, this.height);
        g.setStroke(new BasicStroke(3));
        g.setColor(Color.WHITE);
        g.drawRect(this.x, this.y, this.width, this.height);
    }

}
