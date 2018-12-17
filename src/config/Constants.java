package config;

import de.ur.mi.graphics.Color;
import de.ur.mi.sound.Sound;

public class Constants {

    //Constants for all classes
    public static final int CANVAS_WIDTH = 800;
    public static final int CANVAS_HEIGHT = 800;
    public static final int CANVAS_CENTER_X = 400;
    public static final int CANVAS_CENTER_Y = 400;


    //Constants for class Sky
    public static final int NUMBER_OF_STARS = 110;
    public static final int STARS_MAX_SIZE = 10;
    public static final int STARS_MIN_SIZE = 4;
    public static final String STAR_SRC = "data/assets/star.png";

    //Constants for class Header
    public static final Color CONTAINER_COLOR = Color.DARK_GRAY;
    public static final Color TEXT_COLOR_HEADER = Color.GREEN;
    public static final int TEXT_FONTSIZE_HEADER = 24;
    public static final int HEADER_CONTAINER_HEIGHT = 50;
    public static final int HEADER_CONTAINER_WIDTH = CANVAS_WIDTH;
    public static final int LIVES = 6;
    public static final int START_LEVEL = 1;
    public static final int START_POINTS = 0;

    //Constants for class Core
    public static final int CORE_SIZE = 130;
    public static final Color CORE_COLOR = Color.TRANSPARENT;
    public static final int COLLIDING_TOLERANCE_CORE = 5;
    public static final String EARTH_SRC = "data/assets/earth.png"; //URL:

    //Constants for class Player
    public static final Color PLAYER_COLOR = Color.ORANGE;
    public static final double ANGLE_SPEED = 4.0;
    public static final double RANGE_SPEED = 3.0;
    public static final double MAX_RANGE = 150.0;
    public static final double MIN_RANGE = 35.0;
    public static final double ANGLE_LINE_BALANCE = 900.0;
    public static final int COLLIDING_TOLERANCEPLAYER = 15;
    public static final double PLAYER_LINE_BORDERWEIGHT = 13.0;

    //Constants for class Projectile
    public static final Color PROJECTILE_COLOR = Color.TRANSPARENT;
    public static final String PROJECTILE_IMAGE_SRC = "data/assets/meteorit.png"; //Meteorit Image was selfdesigned in Adobe Photoshop

    //Constants for class ProjectileGenerator
    public static final int PROJECTILE_MIN_SIZE = 30;
    public static final int PROJECTILE_MAX_SIZE = 50;
    public static final double PROJECTILE_MIN_START_SPEED = 3.0;
    public static final double PROJECTILE_MAX_START_SPEED = 4.0;
    public static final double PROJECTILE_LEVEL_SPEED_UP = 1.0;

    //Constansts for class StartScreen
    public static final int TEXT_FONTSIZE_GAME_NAME = 40;
    public static final int TEXT_FONTSIZE_START_GAME = 24;
    public static final String LOGO_SRC = "data/assets/logo.png"; //URL:https://www.br.de/themen/wissen/meteoriten-einschlag-krater-100.html, 10.12.18
    public static final String COLOR_PICK_SRC = "data/assets/selectplayercolor.png"; //Self Designed in Adobe Photoshop
    public static final int LOGO_WIDTH = 200;
    public static final int LOGO_HEIGHT = 120;
    public static final int COLOR_PICKER_WIDTH = 180;
    public static final int COLOR_PICKER_HEIGHT = 164;
    public static final Color TEXT_COLOR_GAME_NAME = Color.CYAN;
    public static final Color TEXT_COLOR_START_GAME = Color.YELLOW;
    public static final String START_GAME_TEXT = "PRESS (SPACE) ON KEYBOARD TO START THE GAME";

    //Constants for class GameOverScreen
    public static final int TEXT_FONTSIZE_RESTART = 18;
    public static final int TEXT_FONTSIZE_GAMEOVER = 24;
    public static final Color TEXT_COLOR_GAMEOVERSCREEN = Color.ORANGE;
    public static final int GAME_OVER_IMAGE_SIZE = 200;
    public static final String GAME_OVER_IMAGE_SRC = "data/assets/earthdestroyed.png";

    //Constants for class GameManager
    public static final Sound SHOT_SOUND = new Sound("/data/assets/projectilesound.wav"); //URL:
    public static final Sound EXPLOSION_SOUND = new Sound("/data/assets/explosion.wav"); //URL
    public static final int GAME_STATE_RUNNING = 2;
    public static final int GAME_STATE_STARTED = 1;
    public static final int GAME_STATE_ENDED = 0;
    public static final int GAME_STATE_FIRST_START = -1;
    public static final double PLAYER_START_RANGE = 40.0;
    public static final double PLAYER_START_ANGLE = 90.0;
    public static final double STAR_SPARKLING_PROPORTION = 90;
    public static final Color TEXT_COLOR_COUNTDOWN = Color.YELLOW;
    public static final int COUNTDOWN_NUM = 5;

    //Constants for class CoreDefence
    public static final int FRAMERATE = 60;


}
