import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class GamePane extends JPanel implements KeyListener, ActionListener {

    private WallGenerator wallGenerator;
    private Dimension wallDimension;
    private Ball ball;

    public GamePane() {
        wallDimension = new Dimension(8,20);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        lazyInitialize();
        paintBackground(g);
        wallGenerator.draw((Graphics2D) g);
        ball.draw(g);
    }

    private void lazyInitialize() {
        if(wallGenerator == null) wallGenerator = new WallGenerator(this.getSize(), wallDimension.width, wallDimension.height);
        ballInitialPosition(20);
    }
    public void ballInitialPosition(int ballSize){
        int padding = 2 * ballSize;
        int ballUpperBound = this.getHeight()/2;
        int ballLowerBound = (this.getHeight()/4)*3;
        Random rand = new Random();
        Coordinate ballPos = new Coordinate(
                rand.nextInt(padding, this.getWidth() - ballSize - padding),
                rand.nextInt(ballUpperBound, ballLowerBound-ballSize)
        );
        ball = new Ball(new Coordinate(ballPos.getX(), ballPos.getY()), ballSize, Color.YELLOW);
    }

    public void testBallPosition(Graphics g, int ballSize){
        int padding = 2 * ballSize;
        int ballUpperBound = this.getHeight()/2;
        int ballLowerBound = (this.getHeight()/4)*3;
        Coordinate up = new Coordinate(this.getWidth()/2, ballUpperBound);
        ball = new Ball(up, ballSize, Color.YELLOW);
        ball.draw(g);
        Coordinate down = new Coordinate(this.getWidth()/2, ballLowerBound);
        ball = new Ball(down, ballSize, Color.YELLOW);
        ball.draw(g);
        Coordinate left = new Coordinate(padding, ballUpperBound);
        ball = new Ball(left, ballSize, Color.YELLOW);
        ball.draw(g);
        Coordinate right = new Coordinate(this.getWidth() - padding - ballSize, ballLowerBound);
        ball = new Ball(right, ballSize, Color.YELLOW);
        ball.draw(g);
    }

    private void paintBackground(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0,0, this.getWidth(),this.getHeight());
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
