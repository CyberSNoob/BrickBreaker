package gameObjects;

import dataClasses.Coordinate;
import dataClasses.RectangleDimension;

import java.awt.*;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Wall extends Rectangle implements Shape{

    private final int MIN_VALUE = 0;
    private List<List<Brick>> bricks;
    private final RectangleDimension brickSize;

    public Wall(Rectangle wallZone, int rows, int cols){
        setUpWall(wallZone);
        brickSize = new RectangleDimension(this.width / cols,this.height / rows);
        buildWall(rows, cols);
        initializeWallColor();
    }

    private void setUpWall(Rectangle wallZone){
        int two = 2, padding = 30;
        this.x = padding;
        this.y = padding;
        this.width = wallZone.width - (two* padding);
        this.height = wallZone.height - (two* padding);
    }

    public void draw(Graphics2D g) {
        drawWall(g);
        drawWallLines(g);
    }

    @FunctionalInterface
    public interface DrawAction{
        void apply(Brick brick);
    }

    public void loopThroughBricks(DrawAction action){
        IntStream.range(MIN_VALUE, bricks.size()).forEach(row -> {
            IntStream.range(MIN_VALUE, bricks.get(row).size()).forEach(col -> {
                action.apply(bricks.get(row).get(col));
            });
        });
    }

    private void drawWallLines(Graphics2D g2) {
        g2.setStroke(new BasicStroke(2));
//        if no collision detected, do nothing
        g2.setColor(Color.BLACK);
        loopThroughBricks(brick -> g2.drawRect(brick.x, brick.y, brick.width, brick.height));
    }

    public void initializeWallColor(){
        loopThroughBricks(brick -> {
//            initially black, then get random color once. if not hit, do nothing
            brick.setColor(WallColor.getRandomColor());
        });
    }

    private void drawWall(Graphics2D g){
        loopThroughBricks(brick -> {
            g.setColor(brick.getColor());
            g.fillRect(brick.x, brick.y, brick.width, brick.height);
        });
    }

    private void buildWall(int rows, int cols) {
        Function<Coordinate, Brick> brick = c -> {
            Coordinate brickPosition = new Coordinate(
                    this.x + (c.getX()*brickSize.width()), this.y + (c.getY()* brickSize.height()));
            return new Brick(brickPosition.getX(), brickPosition.getY(), brickSize.width(), brickSize.height());
        };
        generateBricks(brick, rows, cols);
    }

    private void generateBricks(Function<Coordinate, Brick> elementGenerator, int rows, int cols) {
        this.bricks = IntStream.range(MIN_VALUE, rows)
                .mapToObj(row -> IntStream.range(MIN_VALUE, cols)
                        .mapToObj(col -> elementGenerator.apply(new Coordinate(col, row)))
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    public List<List<Brick>> getBricks() {
        return bricks;
    }

}


