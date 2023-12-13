import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePane extends JPanel implements KeyListener, ActionListener {

    private final WallGenerator wallGenerator = new WallGenerator(this.getSize());

    public void paint(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0,0, this.getWidth(),this.getHeight());
        wallGenerator.draw((Graphics2D) g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }
    public void keyTyped(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}
}
