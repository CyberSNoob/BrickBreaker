package gameObjects;

import java.awt.*;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public enum WallColor {

    RED(new Color(255, 0, 0)),
    YELLOW(new Color(255, 255, 0)),
    BLUE(new Color(0, 0, 255)),
    ORANGE(new Color(255, 165, 0)),
    VIOLET(new Color(128, 0, 128)),
    CYAN(Color.CYAN),
    GREEN(Color.GREEN);

//    GREEN(new Color(0, 128, 0)),
//    RED_ORANGE(new Color(255, 69, 0)),
//    YELLOW_ORANGE(new Color(255, 215, 0)),
//    YELLOW_GREEN(new Color(154, 205, 50)),
//    BLUE_GREEN(new Color(0, 100, 0)),
//    BLUE_VIOLET(new Color(138, 43, 226)),
//    RED_VIOLET(new Color(199, 21, 133));

    private final Color color;
    WallColor(Color color) {
        this.color = color;
    }

    private Color getRandomColor() {
        return color;
    }

    public static Color getRandomColor(Random r){
        WallColor[] colors = WallColor.values();
        return colors[r.nextInt(colors.length)].getRandomColor();
    }

    private Color allRandomColor(Random r) {
        List<Integer> rgb = Stream.generate(() -> r.nextInt(256))
                .limit(3).toList();
        return new Color(rgb.get(0), rgb.get(1), rgb.get(2));
    }
}
