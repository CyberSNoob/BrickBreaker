import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.function.Consumer;

public class Player extends Rectangle{

    private Rectangle zone;
    private Color color;
    private int moveSpace = 100;

    public Player(Rectangle playerZone, Color color){
        this.zone = playerZone;
        int two = 2, playerSizeRatio = 5;
        this.width = this.zone.width / playerSizeRatio;
        this.x = this.zone.width / two - this.width / two;
        this.height = this.zone.height / playerSizeRatio;
        this.y = this.zone.y + this.zone.height / two;
        this.color = color;
    }

    public Consumer<ActionEvent> moveLeft(){
        return e -> this.x = Math.max(this.x - moveSpace, 0);
    }

    public Consumer<ActionEvent> moveRight() {
        return e -> {
            int totalWidth = this.x + this.width + moveSpace;
            this.x = totalWidth > this.zone.width ? this.zone.width - this.width : this.x + moveSpace;
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
