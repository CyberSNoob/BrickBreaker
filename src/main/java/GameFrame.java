import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    public void setUpFrame(){
        setTitle("Game Frame");
        setMinimumSize(new Dimension(1000,600));
        setResizable(false);
        add(new GamePane());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

}
