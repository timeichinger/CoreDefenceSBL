package ui;

import config.Constants;
import de.ur.mi.graphics.Label;
import de.ur.mi.graphics.Rect;
import ui.interfaces.LabelHelper;

public class Header implements LabelHelper {

    private Label lbPoints;
    private Label lbLives;
    private Label lbLevel;
    private Rect headerContainer;

    public static int points = Constants.START_POINTS;
    public static int lives = Constants.LIVES;
    public static int level = Constants.START_LEVEL;

    public Header() {

        headerContainer = new Rect(0,0, Constants.HEADER_CONTAINER_WIDTH, Constants.HEADER_CONTAINER_HEIGHT, Constants.CONTAINER_COLOR);

        lbPoints = new Label(headerContainer.getX()+10, headerContainer.getY() + (headerContainer.getHeight()-15), "0 Punkte", Constants.TEXT_COLOR_HEADER, Constants.TEXT_FONTSIZE_HEADER);

        lbLives = new Label(0, 0, "X Lives remain", Constants.TEXT_COLOR_HEADER, Constants.TEXT_FONTSIZE_HEADER);
        lbLives.setPosition(centerLabelHorizontal(getLabelLength(lbLives)), headerContainer.getHeight()-15);

        lbLevel = new Label(0,0, "Level 1", Constants.TEXT_COLOR_HEADER, Constants.TEXT_FONTSIZE_HEADER);
        lbLevel.setPosition(Constants.CANVAS_WIDTH - getLabelLength(lbLevel) - 10, headerContainer.getHeight()-15);

    }

    public void update() {
        updatePoints();
        updateLife();
        updateLevel();

    }

    private void updateLevel() {
        lbLevel.setText("Level " + String.valueOf(level));
    }

    private void updateLife() {
        if (lives == 1) {
            lbLives.setText(String.valueOf(lives) + " Life left");
        } else {
            lbLives.setText(String.valueOf(lives) + " Lives left");
        }
    }

    private void updatePoints() {
        if (points == 1) {
            lbPoints.setText(String.valueOf(points) + " Point");
        } else {
            lbPoints.setText(String.valueOf(points) + " Points");
        }
    }

    public void draw() {
        headerContainer.draw();
        lbPoints.draw();
        lbLives.draw();
        lbLevel.draw();
    }

    public void resetStats() {
        points = Constants.START_POINTS;
        lives = Constants.LIVES;
        level = Constants.START_LEVEL;
    }

    @Override
    public double getLabelLength(Label label) {
        return label.getText().length()*(label.getFontSize()/2);
    }

    @Override
    public double centerLabelHorizontal(double labelLength) {
        return Constants.CANVAS_CENTER_X - (labelLength / 2);
    }

}
