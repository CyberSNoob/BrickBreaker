package gameObjects;

import java.awt.*;

public class Brick extends Rectangle{

    private Color color = Color.MAGENTA;
    private boolean isHit = false;

    public Brick(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setHit(boolean hit) {
        isHit = hit;
    }

    public boolean isHit() {
        return isHit;
    }
}
