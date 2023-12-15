import java.awt.*;

public class Player {

    private Coordinate c;
    private RectangleDimension d;
    private Color color;

    public Player(Coordinate c, RectangleDimension d, Color color) {
        this.c = c;
        this.d = d;
        this.color = color;
    }

    public void draw(Graphics2D g){
        g.setColor(color);
        g.fillRect(c.getX(), c.getY(), d.width(), d.height());
        g.setStroke(new BasicStroke(3));
        g.setColor(Color.WHITE);
        g.drawRect(c.getX(), c.getY(), d.width(), d.height());
    }

    public Color getColor() {
        return color;
    }
}
