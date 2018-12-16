package ui;

import config.Constants;
import de.ur.mi.graphics.Image;
import de.ur.mi.graphics.Label;
import ui.interfaces.LabelHelper;

public class GameOverScreen implements LabelHelper {

    private Label lbRestart;
    private Label lbGameOver;
    private Label lbEndScore;
    private Image earthDestroyed;

    public GameOverScreen() {
        lbRestart = new Label(0, 0, " + + + PRESS (R) TO RESTART | PRESS (C) TO CLOSE THE GAME + + + ", Constants.TEXT_COLOR_GAMEOVERSCREEN, Constants.TEXT_FONTSIZE_RESTART);
        lbRestart.setPosition(centerLabelHorizontal(getLabelLength(lbRestart)), Constants.CANVAS_CENTER_Y);

        lbGameOver = new Label(0,0, "Game Over! The earth was completely destroyed by meteorites", Constants.TEXT_COLOR_GAMEOVERSCREEN, Constants.TEXT_FONTSIZE_GAMEOVER);
        lbGameOver.setPosition(centerLabelHorizontal(getLabelLength(lbGameOver)), Constants.CANVAS_CENTER_Y - 100);

        lbEndScore = new Label(0,0, "Your score: 20", Constants.TEXT_COLOR_GAMEOVERSCREEN, Constants.TEXT_FONTSIZE_GAMEOVER);
        lbEndScore.setPosition(centerLabelHorizontal(getLabelLength(lbEndScore)), lbGameOver.getY() + 50);

        earthDestroyed = new Image(0, 0, Constants.GAME_OVER_IMAGE_SIZE, Constants.GAME_OVER_IMAGE_SIZE, Constants.GAME_OVER_IMAGE_SRC);
        earthDestroyed.setPosition(Constants.CANVAS_CENTER_X - (earthDestroyed.getWidth()/2), 20);

    }

    public void draw() {
        lbEndScore.setText("Your Score: " + Header.points);
        lbRestart.draw();
        lbGameOver.draw();
        lbEndScore.draw();
        earthDestroyed.draw();
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
