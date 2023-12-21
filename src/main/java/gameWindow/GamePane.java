package gameWindow;

import gameObjects.Ball;
import gameObjects.Player;
import gameObjects.Wall;

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
    private boolean isRunning = false;

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
        player.draw(g2);
        if(ball != null) ball.draw(g2);
        g.dispose();
    }

    public void lazyInitialize() {
        separateZones();
        createWall();
        createPlayer();
        setKeyBindings();
        setFocusable(true);
        setDoubleBuffered(true);
    }

    private void startGame() {
        if(!isRunning){
            player.setInitialPosition();
            createBall();
            timer = new Timer(1000/16, e -> {
                updateGame();
            });
            timer.start();
            isRunning = true;
        }
    }

    private void updateGame(){
        player.update();
        if(ball != null) {
            ball.move(getBounds(), player);
            checkHasLost();
        }
        repaint();
    }

    private void checkHasLost(){
        if(ball.outOfBound(getHeight())) {
            timer.stop();
            isRunning = !isRunning;
        }
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

    public void createBall()  {
        this.ball = new Ball(zones.get("ball"));
    }

    public void createPlayer(){
        this.player = new Player(zones.get("player"), Color.RED);
    }

    private void paintBackground(Graphics2D g) {
        g.setColor(Color.black);
        g.fillRect(0,0, getWidth(), getHeight());
    }

    @Override
    public void actionPerformed(ActionEvent e) {}

//    TODO: adjust player key bindings to ensure smooth movement
    private void setKeyBindings(){
        setPlayerDirectionKeyBindings(KeyEvent.VK_LEFT, false, e -> player.setXDirection(-player.getSpeed()));
        setPlayerDirectionKeyBindings(KeyEvent.VK_LEFT, true, e -> player.setXDirection(0));
        setPlayerDirectionKeyBindings(KeyEvent.VK_RIGHT, false, e -> player.setXDirection(player.getSpeed()));
        setPlayerDirectionKeyBindings(KeyEvent.VK_RIGHT, true, e -> player.setXDirection(0));
        setPlayerDirectionKeyBindings(KeyEvent.VK_SPACE, false, e -> startGame());
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
