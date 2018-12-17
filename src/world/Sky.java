package world;

import de.ur.mi.geom.Point;
import de.ur.mi.graphics.Image;
import de.ur.mi.util.RandomGenerator;
import config.Constants;

public class Sky {

    private int frameCounter;
    private double starProportion;

    private RandomGenerator random;

    private Image[] allStars = new Image[Constants.NUMBER_OF_STARS];
    private int[] sparklingStars;

    public Sky(double starProportion) {
        this.starProportion = starProportion;
        random = RandomGenerator.getInstance();
        sparklingStars = new int[(int) (Constants.NUMBER_OF_STARS*(this.starProportion/100))];
        createStars();
    }

    public void update() {
        //This if-condition makes sure, that just all 40 frames, the drawn stars should be changed.
        if(frameCounter >= 40) {
            updateSparklingStars();
        }
        frameCounter++;
    }

    //This method updates the sparkling stars array and selects indirectly which stars should be drawn next.
    private void updateSparklingStars() {
        for (int i = 0; i < (Constants.NUMBER_OF_STARS * (starProportion / 100)); i++) {
            sparklingStars[i] = random.nextInt(0, allStars.length - 1);
        }
        frameCounter = 0;
    }

    //The public draw method draws all stars, which were selected to be drawn (Array sparklingStars) before in the update method.
    public void draw() {
        for (int i = 0; i < Constants.NUMBER_OF_STARS; i++) {
            for (int sparklingStar : sparklingStars) {
                if (i == sparklingStar) {
                    allStars[i].draw();
                }
            }
        }
    }

    //This method creates one star and adds it to the allStars arrray.
    private void createStar(int i) {
        int size = getNextSize();
        Point centerPoint = getNextPoint(size);
        Image oneStar = new Image(centerPoint, size, size, Constants.STAR_SRC);
        allStars[i] = oneStar;
    }

    //This method creates all stars out of the method createStar
    private void createStars() {
        for (int i = 0; i < Constants.NUMBER_OF_STARS; i++) {
            createStar(i);
        }
    }

    //This method creates a random size for a star
    private int getNextSize() {
        return random.nextInt(Constants.STARS_MIN_SIZE, Constants.STARS_MAX_SIZE);
    }

    //This method creates a random new point in the canvas.
    private Point getNextPoint(int size) {

        int lowerX = size / 2;
        int upperX = Constants.CANVAS_WIDTH - (size / 2);
        int lowerY = size / 2;
        int upperY = Constants.CANVAS_HEIGHT - (size / 2);

        int x = random.nextInt(lowerX, upperX);
        int y = random.nextInt(lowerY, upperY);

        return new Point(x, y);
    }



}