package game;

import config.Constants;
import de.ur.mi.geom.Point;
import de.ur.mi.graphics.Color;
import de.ur.mi.graphics.Label;

import ui.*;
import ui.enums.ColorType;
import ui.enums.MovementType;
import ui.enums.GameStateType;
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
    private int countdownCounter = Constants.COUNTDOWN_NUM;

    //Makes sure gameOver sound is just played at the end of a game
    private int gameOverSoundCounter = 0;

    public GameManager() {
        setupGameObjects();
        handleGameStateStarted();

    }

    private void handleGameStateStarted() {
        if (gameState == Constants.GAME_STATE_STARTED) {
            startTimer();
        }
    }


    public void update() {
        handlePlayerNullLives();
        handleUpdateGameStateStarted();
        handleUpdateGameStateRunning();
    }

    //This method handles the objects-update, when the game is running
    private void handleUpdateGameStateRunning() {
        if (gameState == Constants.GAME_STATE_RUNNING) {
            updateObjectsWhileGameRunning();
        }
    }

    //This method handles the objects-update, when the game started
    private void handleUpdateGameStateStarted() {
        if (gameState == Constants.GAME_STATE_STARTED) {
            updateObjectsWhileGameStarted();
        }
    }

    //This method handles the case when the player has null lives left
    private void handlePlayerNullLives() {
        if (Header.lives == 0) {
            gameState = Constants.GAME_STATE_ENDED;
            playGameOverSound();
        }
    }

    //If the game is over, the GAME OVER SOUND is played
    private void playGameOverSound() {
        if (gameOverSoundCounter == 0) {
            Constants.GAME_OVER_SOUND.play();
            gameOverSoundCounter++;
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


    //The draw method calls other methods in dependence of the gameState
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
    public void transmitStartRestartPressCommand(GameStateType startType) {
        if (startType.equals(GameStateType.RESTART)) {
            resetGame();
        } else if (startType.equals(GameStateType.START)) {
            startGame();
        } else if (startType.equals(GameStateType.CLOSE)) {
            System.exit(0);
        }
    }

    public void transmitPlayerColorPickPressCommand(ColorType colorType) {
        switch (colorType) {
            case BLUE:
                updatePlayerColor(Color.BLUE, "BLUE");
                break;
            case GREEN:
                updatePlayerColor(Color.GREEN, "GREEN");
                break;
            case ORANGE:
                updatePlayerColor(Color.ORANGE, "ORANGE");
                break;
            case YELLOW:
                updatePlayerColor(Color.YELLOW, "YELLOW");
                break;
        }
        startScreen.updateSelectedColorLabel();
    }

    private void updatePlayerColor(Color color, String colorString) {
        player.setPlayerColor(color);
        startScreen.setPlayerColorString(colorString);
    }

    private void startGame() {
        gameState = Constants.GAME_STATE_STARTED;
        gameOverSoundCounter = 0;
        startTimer();
    }

    //This method resets all stats, the speed, sets the gameState to Game started and resets the timer.
    private void resetGame() {
        header.resetStats();
        projectileGenerator.resetSpeed();
        gameState = Constants.GAME_STATE_STARTED;
        gameOverSoundCounter = 0;
        projectile = projectileGenerator.newProjectile();
        countdownCounter = Constants.COUNTDOWN_NUM;
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

    //This method handles the collision between the core and a projectile
    private void handleCoreCollidesWithProjectile() {
        Constants.EXPLOSION_SOUND.play();
        Header.lives--;
        projectile = projectileGenerator.newProjectile();
        if (Header.lives != 0) {
            Constants.SHOT_SOUND.play();
        }

    }

    //This method handles the collision between the playerLine and a projectile
    private void handlePlayerCollidesWithProjectile() {
        Header.points++;

        //In this if-condition is checked, whether the points can be divided by 10.
        //So the condition is true, when points equals to 10,20,30,...
        //And if the condition is true, the level gets increased
        if (Header.points % 10 == 0 && Header.points != 0) {
            Header.level += 1;
            projectileGenerator.updateSpeedPerLevel();
        }

        projectile = projectileGenerator.newProjectile();
        Constants.SHOT_SOUND.play();
    }

    //This method creates and starts a new Timer, which starts at countdownCounter(=5)
    private void startTimer() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (countdownCounter > 0)
                    lbCountdown.setText(String.valueOf(countdownCounter));
                    countdownCounter--;

                if (countdownCounter == 0) {
                    lbCountdown.setText(String.valueOf(countdownCounter));
                    gameState = Constants.GAME_STATE_RUNNING;
                    Constants.SHOT_SOUND.play();
                    timer.cancel();
                }
            }
        };
        timer.schedule(task, 0, Constants.TIMER_PERIOD);
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
