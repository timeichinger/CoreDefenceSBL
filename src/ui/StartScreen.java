package ui;

import config.Constants;
import de.ur.mi.graphics.Color;
import de.ur.mi.graphics.Image;
import de.ur.mi.graphics.Label;
import ui.interfaces.LabelHelper;

public class StartScreen implements LabelHelper {

    private Label lbGameName;
    private Label lbStartGame;
    private Image logoImage;
    private Image selectColorImage;
    private Label lbSelectedPlayerColor;

    private String playerColor = "ORANGE";

    public StartScreen() {
        lbGameName = new Label(0,0,"Defend The Earth", Constants.TEXT_COLOR_GAME_NAME, Constants.TEXT_FONTSIZE_GAME_NAME);
        lbGameName.setPosition(centerLabelHorizontal(getLabelLength(lbGameName)), Constants.CANVAS_CENTER_Y/2);

        lbStartGame = new Label(0, 0, "PRESS (SPACE) ON KEYBOARD TO START THE GAME", Constants.TEXT_COLOR_START_GAME, Constants.TEXT_FONTSIZE_START_GAME);
        lbStartGame.setPosition(centerLabelHorizontal(getLabelLength(lbStartGame)), Constants.CANVAS_CENTER_Y);

        logoImage = new Image(0,0, Constants.LOGO_WIDTH, Constants.LOGO_HEIGHT, Constants.LOGO_SRC);
        logoImage.setPosition(Constants.CANVAS_CENTER_X - (logoImage.getWidth()/2), lbGameName.getY() + 30);

        selectColorImage = new Image(0,0, Constants.COLOR_PICKER_WIDTH, Constants.COLOR_PICKER_HEIGHT, Constants.COLOR_PICK_SRC);
        selectColorImage.setPosition(Constants.CANVAS_CENTER_X - (selectColorImage.getWidth()/2), lbStartGame.getY() + lbStartGame.getHeight() + 10);

        lbSelectedPlayerColor = new Label(0, 0, "Selected Player-Color: ORANGE", Color.WHITE, 18);
        lbSelectedPlayerColor.setPosition(centerLabelHorizontal(getLabelLength(lbSelectedPlayerColor)), selectColorImage.getY() + selectColorImage.getWidth() + 20);
    }

    public void draw() {
        lbGameName.draw();
        lbStartGame.draw();
        logoImage.draw();
        selectColorImage.draw();
        lbSelectedPlayerColor.draw();
    }

    public void setPlayerColorString(String s) {
       playerColor = s;
    }

    public void updateSelectedColorLabel() {
        lbSelectedPlayerColor.setText(String.format("Selected Player-Color: %s", playerColor));
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
