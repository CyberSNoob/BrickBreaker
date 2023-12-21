package gameObjects;

import dataClasses.Coordinate;
import dataClasses.RectangleDimension;

import java.awt.*;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Wall extends Rectangle{

    private final int MIN_VALUE = 0;
    private final int PADDING = 30;
    private List<List<Rectangle>> bricks;
    private final RectangleDimension brickSize;

    public Wall(Rectangle wallZone, int rows, int cols){
        setUpWall(wallZone);
        brickSize = new RectangleDimension(this.width / cols,this.height / rows);
        buildWall(rows, cols);
    }

    public void setUpWall(Rectangle wallZone){
        int two = 2;
        this.x = PADDING;
        this.y = PADDING;
        this.width = wallZone.width - (two*PADDING);
        this.height = wallZone.height - (two*PADDING);
    }

    public void draw(Graphics2D g) {
        drawWall(g);
        drawWallLines(g);
    }

    @FunctionalInterface
    public interface DrawAction{
        void apply(int x, int y);
    }

    private void loopThroughBricks(DrawAction action){
        IntStream.range(MIN_VALUE, bricks.size()).forEach(row -> {
            IntStream.range(MIN_VALUE, bricks.get(row).size()).forEach(col -> {
                int x = this.x + (col * brickSize.width());
                int y = this.y + (row * brickSize.height());
                action.apply(x,y);
            });
        });
    }

    private void drawWallLines(Graphics2D g2) {
        g2.setStroke(new BasicStroke(2));
        g2.setColor(Color.BLACK);
        loopThroughBricks((x, y) -> g2.drawRect(x, y, brickSize.width(), brickSize.height()));
    }

    private void drawWall(Graphics2D g){
        Random r = new Random();
        loopThroughBricks((x, y) -> {
            g.setColor(WallColor.getRandomColor(r));
            g.fillRect(x, y, brickSize.width(), brickSize.height());
        });
    }

    private void buildWall(int rows, int cols) {
        Function<Coordinate, Rectangle> generator = c -> {
            Coordinate brickPosition = new Coordinate(
                    this.x + (c.getX()*brickSize.width()), this.y + (c.getY()* brickSize.height()));
            return new Rectangle(brickPosition.getX(), brickPosition.getY());
        };
        fillWall(generator, rows, cols);
    }

    private void fillWall(Function<Coordinate, Rectangle> elementGenerator, int rows, int cols) {
        this.bricks = IntStream.range(MIN_VALUE, rows)
                .mapToObj(row -> IntStream.range(MIN_VALUE, cols)
                        .mapToObj(col -> elementGenerator.apply(new Coordinate(col, row)))
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    public void printResults(){
        this.bricks.forEach(System.out::println);
    }

}


