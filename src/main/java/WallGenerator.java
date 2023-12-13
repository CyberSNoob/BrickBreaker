import java.awt.*;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class WallGenerator {

    private List<List<Integer>> map;
    private final int MIN_VALUE = 0;
    private final int ROW_COUNT = 5;
    private final int COL_COUNT = 10;
    private final int PADDING = 30;
    private final RectangleDimension wallArea;
    private final RectangleDimension brickDimension;

    public WallGenerator(Dimension wallArea){
        this.wallArea = adjustWallAreaDimension(wallArea);
        this.brickDimension = new RectangleDimension(
                (this.wallArea.width / COL_COUNT), (this.wallArea.height / ROW_COUNT));
        System.out.printf("Brick width = %d, height = %d\n", brickDimension.width, brickDimension.height);
//    set how to fillMap and which method to use
        fillMapWithFixedValue(1);
    }

    public RectangleDimension adjustWallAreaDimension(Dimension wallArea){
        double width = wallArea.getWidth() - (2*PADDING);
        double height = (wallArea.getHeight()/2) - (2*PADDING);
        System.out.printf("Wall width = %.2f, height = %.2f\n", width, height);
        return new RectangleDimension((int) width, (int) height);
    }

    public void draw(Graphics2D g) {
        drawWall(g);
        g.setStroke(new BasicStroke(2));
        g.setColor(Color.WHITE);
//        drawrect for every brick
        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.get(i).size(); j++){
                g.drawRect(j*brickDimension.width+PADDING, i*brickDimension.height+PADDING,
                        brickDimension.width, brickDimension.height);
            }
        }
    }

    public void drawWall(Graphics2D g){
        g.setColor(Color.RED);
        RectangleDimension brickDimensionShort = new RectangleDimension(
                wallArea.width%COL_COUNT, wallArea.height%ROW_COUNT);
        g.fillRect(PADDING, PADDING,
                wallArea.width-brickDimensionShort.width, wallArea.height-brickDimensionShort.height);
    }

    public void fillMapWithFixedValue(int fixedValue) {
        fillMap(i -> Math.max(MIN_VALUE, fixedValue));
    }

    public void fillMapWithRandomNumbers(int minBoundary, int maxBoundary){
        Random random = new Random();
        fillMap(i -> random.nextInt(minBoundary,maxBoundary));
    }

    public void fillMapWithSequentialNumbers(){
        this.map = IntStream.range(MIN_VALUE, ROW_COUNT)
                .mapToObj(i -> IntStream.range(MIN_VALUE, COL_COUNT)
                        .boxed().collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    public void printResults(){
        this.map.forEach(System.out::println);
    }

    public List<List<Integer>> getMap(){
        return this.map;
    }

    public RectangleDimension getWallArea() {
        return wallArea;
    }

    public RectangleDimension getRectangleDimension() {
        return brickDimension;
    }

    private void fillMap(Function<Integer, Integer> elementGenerator) {
        this.map = IntStream.range(MIN_VALUE, ROW_COUNT)
                .mapToObj(i -> IntStream.range(MIN_VALUE, COL_COUNT)
                        .mapToObj(elementGenerator::apply)
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    private record RectangleDimension(int width, int height) {}
}


