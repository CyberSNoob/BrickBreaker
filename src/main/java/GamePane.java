import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.function.Consumer;

public class GamePane extends JPanel implements ActionListener {

    private Map<String, Rectangle> zones;
    private Wall wall;
    private Ball ball;
    private Player player;
    private Timer timer;
    private boolean hasStarted = false;

    public GamePane(){
        SwingUtilities.invokeLater(this::lazyInitialize);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        paintBackground(g2);
        if(wall != null) wall.draw(g2);
        player.draw(g2);
        if(ball != null) {
            ball.draw(g2);
            checkHasLost();
        }
        g.dispose();
    }

    public void lazyInitialize() {
        separateZones();
        createWall();
        createPlayer();
        setKeyBindings();
        setFocusable(true);
    }

    private void separateZones(){
        int two = 2;
        int halfPanel = getHeight() / two;
        int quarterPanel = halfPanel / two;
        zones = new HashMap<>();
        zones.put("wall", new Rectangle(0, 0, getWidth(), halfPanel));
        zones.put("ball", new Rectangle(0, halfPanel, getWidth(), quarterPanel));
        zones.put("player", new Rectangle(0, halfPanel+quarterPanel, getWidth(), quarterPanel));
    }

    public void createWall(){
        int rows = 8, cols = 10;
        wall = new Wall(zones.get("wall"), rows, cols);
    }

    public void createBall(Color color)  {
        if(ball != null) return;
        int initialVelocity = 100;
        this.ball = new Ball(zones.get("ball"), initialVelocity, color);
        setDoubleBuffered(true);
        timer = new Timer(1000/16, e -> {
            player.update();
            ball.move(getBounds(), player);
            repaint();
        });
        timer.start();
        hasStarted = true;
    }

    public void createPlayer(){
        this.player = new Player(zones.get("player"), Color.RED);
    }

    private void paintBackground(Graphics2D g) {
        g.setColor(Color.black);
        g.fillRect(0,0, getWidth(), getHeight());
    }

    private void checkHasLost(){
        if(ball.getBounds().y >= getHeight() - ball.getBallSize()) {
            timer.stop();
            hasStarted = !hasStarted;
            player.setInitialPosition();
            ball = null;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {}

    private void setKeyBindings(){
        setPlayerDirectionKeyBindings(KeyEvent.VK_LEFT, false, e -> player.setXDirection(-player.getSpeed()));
        setPlayerDirectionKeyBindings(KeyEvent.VK_LEFT, true, e -> player.setXDirection(0));
        setPlayerDirectionKeyBindings(KeyEvent.VK_RIGHT, false, e -> player.setXDirection(player.getSpeed()));
        setPlayerDirectionKeyBindings(KeyEvent.VK_RIGHT, true, e -> player.setXDirection(0));
        setPlayerDirectionKeyBindings(KeyEvent.VK_SPACE, false, e -> createBall(Color.YELLOW));
//        setPlayerDirectionKeyBindings(KeyEvent.VK_RIGHT, "moveRight", player.moveRight());
    }

    private void setPlayerDirectionKeyBindings(int keyDirection, boolean onKeyRelease, Consumer<ActionEvent> action){
        String keyStrokeId = String.format("keyStroke_%d_%b", keyDirection, onKeyRelease);
        KeyStroke ks = KeyStroke.getKeyStroke(keyDirection, 0, onKeyRelease);
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(ks, keyStrokeId);
        getActionMap().put(keyStrokeId, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                action.accept(e);
            }
        });
    }

}
