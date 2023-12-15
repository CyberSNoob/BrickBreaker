import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

public class GamePane extends JPanel implements KeyListener, ActionListener {

    private Wall wall;
    private Map<String, Boundary> zones;
    private Ball ball;
    private Player player;
    private final Random r;

    public GamePane(){
        r = new Random();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        lazyInitialize();
        paintBackground(g2);
        wall.draw(g2);
        ball.draw(g2);
        System.out.println(player.getColor() + " " + Color.RED);
        player.draw(g2);
    }

    private void lazyInitialize() {
        if(wall == null) {
            separateZones();
            int rows = 8,  cols = 10;
            wall = new Wall(this.getSize(), rows, cols);
            createBall(20, Color.YELLOW);
            createPlayer();
        }
    }

    private void separateZones(){
        zones = new HashMap<>();
        int startingPoint = 0, two = 2;
        int wallZoneHighestBound = this.getHeight() / two;
        int ballZoneHighestBound = wallZoneHighestBound + (wallZoneHighestBound / two);
        zones.put("wall", new Boundary(new Coordinate(startingPoint,startingPoint),
                new Coordinate(this.getWidth(), wallZoneHighestBound)));
        zones.put("ball", new Boundary(new Coordinate(startingPoint, wallZoneHighestBound),
                new Coordinate(this.getWidth(), ballZoneHighestBound)));
        zones.put("player", new Boundary(new Coordinate(startingPoint, ballZoneHighestBound),
                new Coordinate(this.getWidth(), this.getHeight())));
    }

    public void createBall(int ballSize, Color color){
        int padding = 2 * ballSize;
        Boundary boundary = zones.get("ball");
        Coordinate ballPos = new Coordinate(
                r.nextInt(padding, boundary.high().getX() - ballSize - padding),
                r.nextInt(boundary.low().getY(), boundary.high().getY() - ballSize)
        );
        ball = new Ball(ballPos, ballSize, color);
    }

    public void createPlayer(){
        int two = 2;
        Boundary boundary = zones.get("player");
        int zoneWidth = boundary.high().getX() - boundary.low().getX();
        int playerWidth = zoneWidth / 5;
//        int xPos = r.nextInt(boundary.low().getX(), boundary.high().getX() - playerWidth);
        int xPos = zoneWidth/2 - playerWidth / 2;
        int zoneHeight = boundary.high().getY() - boundary.low().getY();
        int playerHeight = zoneHeight / 5;
        int yPos = boundary.low().getY() + (zoneHeight / two);
        Coordinate playerPos = new Coordinate(xPos, yPos);
        RectangleDimension playerSize = new RectangleDimension(playerWidth, playerHeight);
        player = new Player(playerPos, playerSize, Color.CYAN);
    }

    public void testBallPosition(Graphics2D g, int ballSize){
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

    private void paintBackground(Graphics2D g) {
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
