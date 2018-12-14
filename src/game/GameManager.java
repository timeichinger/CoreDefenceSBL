package game;

import de.ur.mi.geom.Point;
import de.ur.mi.graphics.Color;
import de.ur.mi.graphics.Label;

import ui.*;
import ui.enums.ColorType;
import ui.enums.MovementType;
import ui.enums.UIType;
import ui.interfaces.LabelHelper;
import world.*;

import java.util.Timer;
import java.util.TimerTask;

public class GameManager implements LabelHelper {

    private Sky sky;
    private Player player;
    private Core core;
    private Projectile projectile;
    private ProjectileGenerator projectileGenerator;
    private Header header;
    private StartScreen startScreen;
    private GameOverScreen gameOverScreen;

    private Label lbCountdown;

    private int gameState = Constants.GAME_STATE_FIRST_START;
    private int count = Constants.COUNTDOWN_NUM;

    public GameManager() {
        setupGameObjects();
        if (gameState == Constants.GAME_STATE_STARTED) {
            startTimer();
        }

    }


    public void update() {
        if (Header.lives == 0) {
            gameState = Constants.GAME_STATE_ENDED;
        }

        if (gameState == Constants.GAME_STATE_STARTED) {
            updateObjectsWhileGameStarted();
        }

        if (gameState == Constants.GAME_STATE_RUNNING) {
            updateObjectsWhileGameRunning();
        }

    }

    private void updateObjectsWhileGameStarted() {
        sky.update();
        player.update();
    }

    private void updateObjectsWhileGameRunning() {
        sky.update();
        player.update();
        projectile.update();
        header.update();
        checkAndHandleCollision();
    }


    //The draw method calls other methods in despendence of the gameState
    public void draw() {
        switch (gameState) {
            case Constants.GAME_STATE_STARTED:
                drawObjectsWhenGameStarted();
                break;
            case Constants.GAME_STATE_RUNNING:
                drawObjectsWhenGameRunning();
                break;
            case Constants.GAME_STATE_ENDED:
                drawObjectsWhenGameEnded();
                break;
            case Constants.GAME_STATE_FIRST_START:
                drawObjectsStartScreen();
        }
    }

    private void drawObjectsStartScreen() {
        startScreen.draw();
    }

    private void drawObjectsWhenGameStarted() {
        sky.draw();
        player.draw();
        core.draw();
        lbCountdown.draw();
    }

    private void drawObjectsWhenGameEnded() {
        gameOverScreen.draw();
    }

    private void drawObjectsWhenGameRunning() {
        sky.draw();
        player.draw();
        core.draw();
        projectile.draw();
        header.draw();

    }

    //This method handles what happens when the method is called, when a specific Key Press happens
    public void transmitStartRestartPressCommand(UIType startType) {
        if (startType.equals(UIType.RESTART)) {
            resetGame();
        } else if (startType.equals(UIType.START)) {
            startGame();
        } else if (startType.equals(UIType.CLOSE)) {
            System.exit(0);
        }
    }

    public void transmitPlayerColorPickPressCommand(ColorType colorType) {
        switch (colorType) {
            case BLUE:
                player.setPlayerColor(Color.BLUE);
                startScreen.setPlayerColorString("BLUE");
                break;
            case GREEN:
                player.setPlayerColor(Color.GREEN);
                startScreen.setPlayerColorString("GREEN");
                break;
            case ORANGE:
                player.setPlayerColor(Color.ORANGE);
                startScreen.setPlayerColorString("ORANGE");
                break;
            case YELLOW:
                player.setPlayerColor(Color.YELLOW);
                startScreen.setPlayerColorString("YELLOW");
                break;
        }
        startScreen.updateSelectedTypesLabel();
    }

    private void startGame() {
        gameState = Constants.GAME_STATE_STARTED;
        startTimer();
    }

    //This method resets all stats, the speed, sets the gamestate to Game started and resets the timer.
    private void resetGame() {
        header.resetStats();
        projectileGenerator.resetSpeed();
        gameState = Constants.GAME_STATE_STARTED;
        projectile = projectileGenerator.newProjectile();
        count = Constants.COUNTDOWN_NUM;
        startTimer();
    }


    public void transmitMovementCommand(MovementType type) {

        if (type.equals(MovementType.ORBIT_NONE) || type.equals(MovementType.CLOCKWISE) || type.equals(MovementType.COUNTER_CLOCKWISE)) {
            player.setOrbitMovementType(type);
        } else {
            player.setRangeMovementType(type);
        }
    }

    private void setupGameObjects() {
        Point orbitCenter = new Point(Constants.CANVAS_CENTER_X, Constants.CANVAS_CENTER_Y);

        sky = new Sky(Constants.STAR_SPARKLING_PROPORTION);
        core = new Core(orbitCenter);
        player = new Player(orbitCenter, Constants.PLAYER_START_RANGE, Constants.PLAYER_START_ANGLE, core.getCoreRadius());
        projectileGenerator = new ProjectileGenerator(orbitCenter);
        projectile = projectileGenerator.newProjectile();

        header = new Header();
        startScreen = new StartScreen();
        gameOverScreen = new GameOverScreen();

        setupLabelCountdown();

    }

    private void setupLabelCountdown() {
        lbCountdown = new Label(0, 0, "5", Constants.TEXT_COLOR_COUNTDOWN, 40);
        lbCountdown.setPosition(centerLabelHorizontal(getLabelLength(lbCountdown)), Constants.CANVAS_CENTER_Y - 100);
    }

    private void checkAndHandleCollision() {
        if (core.collidesWith(projectile)) {
            handleCoreCollidesWithProjectile();
        } else if (player.collidesWith(projectile)) {
            handlePlayerCollidesWithProjectile();
        }
    }

    private void handleCoreCollidesWithProjectile() {
        Constants.EXPLOSION_SOUND.play();
        Header.lives--;
        projectile = projectileGenerator.newProjectile();
        if (Header.lives != 0) {
            Constants.SHOT_SOUND.play();
        }

    }

    private void handlePlayerCollidesWithProjectile() {
        Header.points++;

        //in this if-condition is checked, whether the points can be divided by 10.
        // So the condition is true, when points equals to 10,20,30,...
        //And if the condition is true, the level gets increased
        if (Header.points % 10 == 0 && Header.points != 0) {
            Header.level += 1;
            projectileGenerator.updateSpeedPerLevel();
        }

        projectile = projectileGenerator.newProjectile();
        Constants.SHOT_SOUND.play();
    }

    //This method creates and starts a new Timer, which starts at count(=5)
    private void startTimer() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (count > 0)
                    lbCountdown.setText(String.valueOf(count));
                    count--;

                if (count == 0) {
                    lbCountdown.setText(String.valueOf(count));
                    gameState = Constants.GAME_STATE_RUNNING;Constants.SHOT_SOUND.play();
                    timer.cancel();
                }
            }
        };
        timer.schedule(task, 0, 1000);
    }

    @Override
    public double getLabelLength(Label label) {
        return label.getText().length() * (label.getFontSize() / 2);
    }

    @Override
    public double centerLabelHorizontal(double labelLength) {
        return Constants.CANVAS_CENTER_X - (labelLength / 2);
    }
}
