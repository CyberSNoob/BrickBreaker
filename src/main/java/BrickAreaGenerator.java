import java.awt.*;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BrickAreaGenerator {

    private List<List<Integer>> map;
    private final int MIN_VALUE = 0;
    private final int ROW_COUNT = 5;
    private final int COL_COUNT = 10;
    private int brickWidth;
    private int brickHeight;

    public BrickAreaGenerator(Dimension brickArea){
        brickWidth = (int) (brickArea.getWidth()/COL_COUNT);
        brickHeight = (int) (brickArea.getHeight()/ROW_COUNT);
    }

    private void fillMap(Function<Integer, Integer> elementGenerator) {
        this.map = IntStream.range(MIN_VALUE, ROW_COUNT)
                .mapToObj(i -> IntStream.range(MIN_VALUE, COL_COUNT)
                        .mapToObj(elementGenerator::apply)
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
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
}
