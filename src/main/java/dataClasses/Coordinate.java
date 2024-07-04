package dataClasses;

import java.awt.*;

public class Coordinate {

    private int x, y;

    public Coordinate(int x, int y) {
        updatePosition(x, y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void reverseX(){
        if(x == 0) return;
        x = x < 0 ? Math.abs(x) : -x;
    }

    public void reverseY(){
        if(y == 0) return;
        y = y < 0 ? Math.abs(y) : -y;
    }

    public void updatePosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void updatePosition(Point p){
        this.x = p.x;
        this.y = p.y;
    }

    @Override
    public String toString() {
        return "dataClasses.Coordinate{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
