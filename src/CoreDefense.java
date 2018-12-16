import de.ur.mi.graphics.Color;
import de.ur.mi.graphicsapp.GraphicsApp;
import game.GameManager;
import ui.*;
import ui.enums.ColorType;
import ui.enums.MovementType;
import ui.enums.UIType;

import java.awt.event.KeyEvent;

public class CoreDefense extends GraphicsApp {


    private GameManager gameManager;
    private int keyStartPressedCounter = 0;
    private int keyColorPressedCounter = 0;

    public void setup() {
        setupCanvas();
        setupGame();
    }

    private void setupGame() {
        gameManager = new GameManager();
    }

    private void setupCanvas() {
        size(Constants.CANVAS_WIDTH, Constants.CANVAS_HEIGHT);
        frameRate(Constants.FRAMERATE);
    }

    public void draw() {
        background(Color.BLACK);
        gameManager.update();
        gameManager.draw();
    }

    public void keyPressed(KeyEvent e) {
        handleMovementEvent(e);
        handleRestartCloseEvent(e);
        handleStartEvent(e);
        handlePlayerColorPickEvent(e);
    }

    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_RIGHT:
                gameManager.transmitMovementCommand(MovementType.ORBIT_NONE);
                break;
            case KeyEvent.VK_LEFT:
                gameManager.transmitMovementCommand(MovementType.ORBIT_NONE);
                break;
            case KeyEvent.VK_UP:
                gameManager.transmitMovementCommand(MovementType.RANGE_NONE);
                break;
            case KeyEvent.VK_DOWN:
                gameManager.transmitMovementCommand(MovementType.RANGE_NONE);
                break;
        }
    }

    private void handlePlayerColorPickEvent(KeyEvent e) {
        if (keyColorPressedCounter == 0) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_O:
                    gameManager.transmitPlayerColorPickPressCommand(ColorType.ORANGE);
                    break;
                case KeyEvent.VK_B:
                    gameManager.transmitPlayerColorPickPressCommand(ColorType.BLUE);
                    break;
                case KeyEvent.VK_G:
                    gameManager.transmitPlayerColorPickPressCommand(ColorType.GREEN);
                    break;
                case KeyEvent.VK_Y:
                    gameManager.transmitPlayerColorPickPressCommand(ColorType.YELLOW);
                    break;
            }
        }
    }

    private void handleStartEvent(KeyEvent e) {
        if (keyStartPressedCounter == 0) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                keyStartPressedCounter++;
                keyColorPressedCounter++;
                gameManager.transmitStartRestartPressCommand(UIType.START);
            }
        }
    }

    private void handleRestartCloseEvent(KeyEvent e) {
        if (keyStartPressedCounter == 1) {
            if (e.getKeyCode() == KeyEvent.VK_R) {
                gameManager.transmitStartRestartPressCommand(UIType.RESTART);
            } else if (e.getKeyCode() == KeyEvent.VK_C) {
                gameManager.transmitStartRestartPressCommand(UIType.CLOSE);
            }
        }

    }

    private void handleMovementEvent(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_RIGHT:
                gameManager.transmitMovementCommand(MovementType.CLOCKWISE);
                break;
            case KeyEvent.VK_LEFT:
                gameManager.transmitMovementCommand(MovementType.COUNTER_CLOCKWISE);
                break;
            case KeyEvent.VK_UP:
                gameManager.transmitMovementCommand(MovementType.RANGE_UP);
                break;
            case KeyEvent.VK_DOWN:
                gameManager.transmitMovementCommand(MovementType.RANGE_DOWN);
                break;
        }
    }


}
