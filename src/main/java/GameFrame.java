import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    private static final Dimension FRAME_SIZE = new Dimension(1000,600);

    public GameFrame(){
        setUpFrame();
    }

    public void setUpFrame(){
        this.setTitle("Game Frame");
        this.setMinimumSize(FRAME_SIZE);
        this.setResizable(false);
        this.add(new GamePane());
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public static Dimension getDimension(){
        return FRAME_SIZE;
    }
}
