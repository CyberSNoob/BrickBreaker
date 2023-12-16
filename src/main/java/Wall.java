import java.awt.*;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Wall extends Rectangle{

    private final int MIN_VALUE = 0;
    private final int PADDING = 30;
    private List<List<Integer>> map;
    private final RectangleDimension brickSize;
    private final RectangleDimension leftoverBrickSize;

    public Wall(Rectangle wallZone, int rows, int cols){
        setUpWall(wallZone);
        fillMapWithFixedValue(rows, cols, 1);
        brickSize = new RectangleDimension(this.width / map.get(0).size(),
                this.height / map.size());
        leftoverBrickSize = new RectangleDimension(this.width % map.get(0).size(),
                this.height % map.size());
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

    private void drawWallLines(Graphics2D g) {
        g.setStroke(new BasicStroke(2));
        g.setColor(Color.WHITE);
        for (int row = 0; row < map.size(); row++) {
            for (int column = 0; column < map.get(row).size(); column++){
                g.drawRect(column * brickSize.width()+PADDING, row * brickSize.height()+PADDING,
                        brickSize.width(), brickSize.height());
            }
        }
    }

    private void drawWall(Graphics2D g){
        g.setColor(Color.RED);
        g.fillRect(PADDING, PADDING, this.width - leftoverBrickSize.width(),
                this.height - leftoverBrickSize.height());
    }

    public void fillMapWithFixedValue(int rows, int cols, int fixedValue) {
        fillMap(i -> Math.max(MIN_VALUE, fixedValue), rows, cols);
    }

    public void fillMapWithRandomNumbers(int rows, int cols, int minBoundary, int maxBoundary){
        Random random = new Random();
        fillMap(i -> random.nextInt(minBoundary,maxBoundary), rows, cols);
    }

    public void fillMapWithSequentialNumbers(int rows, int cols){
        this.map = IntStream.range(MIN_VALUE, rows)
                .mapToObj(i -> IntStream.range(MIN_VALUE, cols)
                        .boxed().collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    private void fillMap(Function<Integer, Integer> elementGenerator, int rows, int cols) {
        this.map = IntStream.range(MIN_VALUE, rows)
                .mapToObj(i -> IntStream.range(MIN_VALUE, cols)
                        .mapToObj(elementGenerator::apply)
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    public void printResults(){
        this.map.forEach(System.out::println);
    }

}


