import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.function.Consumer;

public class GamePane extends JPanel implements ActionListener {

    private Map<String, Rectangle> zones;
    private Rectangle boundaries;
    private Wall wall;
    private Ball ball;
    private Player player;
    private Timer t;

    public GamePane(){
        SwingUtilities.invokeLater(this::lazyInitialize);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        paintBackground(g2);
        wall.draw(g2);
        ball.draw(g2);
        player.draw(g2);
    }

    public void lazyInitialize() {
        boundaries = new Rectangle(0, 0, this.getWidth(), this.getHeight());
        separateZones();
        createWall();
        createBall(20, Color.YELLOW);
        createPlayer();
        setKeyBindings();
        t.start();
    }

    private void separateZones(){
        int startingPoint = 0, two = 2;
        int halfPanel = boundaries.height / two;
        int quarterPanel = halfPanel / two;
        zones = new HashMap<>();
        zones.put("wall", new Rectangle(startingPoint, startingPoint, boundaries.width, halfPanel));
        zones.put("ball", new Rectangle(startingPoint, halfPanel, boundaries.width, quarterPanel));
        zones.put("player", new Rectangle(startingPoint, halfPanel+quarterPanel, boundaries.width, quarterPanel));
    }

    public void createWall(){
        int rows = 8,  cols = 10;
        wall = new Wall(zones.get("wall"), rows, cols);
    }

    public void createBall(int ballSize, Color color){
        int initialVelocity = 40;
        this.ball = new Ball(zones.get("ball"), ballSize, initialVelocity, color);
        t = new Timer(ball.getVelocity(), e -> {
            ball.move(boundaries);
            repaint();
        });
    }

    public void createPlayer(){
        this.player = new Player(zones.get("player"), Color.RED);
    }

    private void paintBackground(Graphics2D g) {
        g.setColor(Color.black);
        g.fillRect(0,0, boundaries.width, boundaries.height);
    }

    @Override
    public void actionPerformed(ActionEvent e) {}

    private void setKeyBindings(){
        setPlayerDirectionKeyBindings(KeyEvent.VK_LEFT, "moveLeft", player.moveLeft());
        setPlayerDirectionKeyBindings(KeyEvent.VK_RIGHT, "moveRight", player.moveRight());
    }

    private void setPlayerDirectionKeyBindings(int keyDirection, String keyStrokeId, Consumer<ActionEvent> action){
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(keyDirection, 0), keyStrokeId);
        getActionMap().put(keyStrokeId, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                action.accept(e);
                repaint();
            }
        });
    }

}
