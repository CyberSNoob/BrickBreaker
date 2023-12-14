import java.awt.*;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class WallGenerator {

    private final int MIN_VALUE = 0;
    private final int PADDING = 30;
    private List<List<Integer>> map;
    private final RectangleDimension wallArea;
    private final RectangleDimension brickDimension;
    private final RectangleDimension brickDimensionLeft;

    public WallGenerator(Dimension panelDimension, int rows, int cols){
        wallArea = adjustWallAreaDimension(panelDimension);
//    set how to fillMap and which method to use
        fillMapWithFixedValue(rows, cols, 1);
        brickDimensionLeft = new RectangleDimension(wallArea.width() % map.get(0).size(), wallArea.height() % map.size());
        brickDimension = new RectangleDimension(wallArea.width() / map.get(0).size(), wallArea.height() / map.size());
        System.out.printf("Brick width = %d, height = %d\n", brickDimension.width(), brickDimension.height());
    }

    public RectangleDimension adjustWallAreaDimension(Dimension panelDimension){
        int halfPanelHeight = 2;
        double width = panelDimension.getWidth() - (2*PADDING);
        double height = (panelDimension.getHeight() / halfPanelHeight) - (2*PADDING);
        System.out.printf("Wall width = %.2f, height = %.2f\n", width, height);
        return new RectangleDimension((int) width, (int) height);
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
                g.drawRect(column*brickDimension.width()+PADDING, row*brickDimension.height()+PADDING,
                        brickDimension.width(), brickDimension.height());
            }
        }
    }

    private void drawWall(Graphics2D g){
        g.setColor(Color.RED);
        g.fillRect(PADDING, PADDING, wallArea.width() - brickDimensionLeft.width(), wallArea.height() - brickDimensionLeft.height());
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


