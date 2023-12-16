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
        if(wall == null) {
            separateZones();
            createWall();
            createBall(20, Color.YELLOW);
            createPlayer();
            setKeyBindings();
        }
    }

    private void separateZones(){
        int startingPoint = 0, two = 2;
        int halfPanel = this.getHeight() / two;
        int quarterPanel = halfPanel / two;
        zones = new HashMap<>();
        zones.put("wall", new Rectangle(startingPoint, startingPoint, this.getWidth(), halfPanel));
        zones.put("ball", new Rectangle(startingPoint, halfPanel, this.getWidth(), quarterPanel));
        zones.put("player", new Rectangle(startingPoint, halfPanel+quarterPanel, this.getWidth(), quarterPanel));
    }

    public void createWall(){
        int rows = 8,  cols = 10;
        wall = new Wall(zones.get("wall"), rows, cols);
    }

    public void createBall(int ballSize, Color color){
        this.ball = new Ball(zones.get("ball"), ballSize, color);
    }

    public void createPlayer(){
        this.player = new Player(zones.get("player"), Color.RED);
    }

    private void paintBackground(Graphics2D g) {
        g.setColor(Color.black);
        g.fillRect(0,0, this.getWidth(),this.getHeight());
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
