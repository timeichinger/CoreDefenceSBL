package ui;

import de.ur.mi.graphics.Color;
import de.ur.mi.graphics.Image;
import de.ur.mi.graphics.Label;
import ui.interfaces.LabelHelper;

public class StartScreen implements LabelHelper {

    private Label lbgameName;
    private Label lbStartGame;
    private Image logoImage;
    private Image selectColorImage;
    private Label selectedPlayer;

    private String playerColor = "RED";

    public StartScreen() {
        lbgameName = new Label(0,0,"Defend The Earth", Constants.TEXT_COLOR_GAME_NAME, Constants.TEXT_FONTSIZE_GAME_NAME);
        lbgameName.setPosition(centerLabelHorizontal(getLabelLength(lbgameName)), Constants.CANVAS_CENTER_Y/2);

        lbStartGame = new Label(0, 0, "PRESS (SPACE) ON KEYBOARD TO START THE GAME", Constants.TEXT_COLOR_START_GAME, Constants.TEXT_FONTSIZE_START_GAME);
        lbStartGame.setPosition(centerLabelHorizontal(getLabelLength(lbStartGame)), Constants.CANVAS_CENTER_Y);

        logoImage = new Image(0,0, Constants.LOGO_WIDTH, Constants.LOGO_HEIGHT, Constants.LOGO_SRC);
        logoImage.setPosition(Constants.CANVAS_CENTER_X - (logoImage.getWidth()/2), lbgameName.getY() + 30);

        selectColorImage = new Image(0,0, Constants.COLOR_PICKER_WIDTH, Constants.COLOR_PICKER_HEIGHT, Constants.COLOR_PICK_SRC);
        selectColorImage.setPosition(Constants.CANVAS_CENTER_X - (selectColorImage.getWidth()/2), lbStartGame.getY() + lbStartGame.getHeight() + 10);

        selectedPlayer = new Label(0, 0, "Selected Player-Color: ORANGE", Color.WHITE, 18);
        selectedPlayer.setPosition(centerLabelHorizontal(getLabelLength(selectedPlayer)), selectColorImage.getY() + selectColorImage.getWidth() + 20);
    }

    public void draw() {
        lbgameName.draw();
        lbStartGame.draw();
        logoImage.draw();
        selectColorImage.draw();
        selectedPlayer.draw();
    }

    public void setPlayerColorString(String s) {
       playerColor = s;
    }

    public void updateSelectedTypesLabel() {
        selectedPlayer.setText(String.format("Selected Player-Color: %s", playerColor));
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
