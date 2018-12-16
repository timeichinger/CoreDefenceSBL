package world;

import de.ur.mi.geom.Point;
import de.ur.mi.util.RandomGenerator;
import config.Constants;

public class ProjectileGenerator {


    //lowerBorderSpeed and upperBorderSpeed defines the upper and lower border for the randomGenerator who selects the speed
    private double lowerBorderSpeed = Constants.PROJECTILE_MIN_START_SPEED;
    private double upperBorderSpeed = Constants.PROJECTILE_MAX_START_SPEED;

    private RandomGenerator random;
    private double speed;
    private int size;
    private Point projectileStartPoint;

    private Point corePos;

    public ProjectileGenerator(Point corePos) {
        this.corePos = corePos;
        random = new RandomGenerator();
    }

    /*newProjectile() is a public Method,which sets the attributes of an existing projectile
     *to the newly generated Attributes, which were created by the Method setNewAttributes()*/
    public Projectile newProjectile() {
        setnewAttributes();
        return new Projectile(corePos, projectileStartPoint, size, speed);
    }

    private void setnewAttributes() {
        setSpeedAndSize();
        setStartPosition();
    }

    //This method sets speed and size to a new random dimension
    private void setSpeedAndSize() {
        speed = random.nextDouble(lowerBorderSpeed, upperBorderSpeed);
        size = random.nextInt(Constants.PROJECTILE_MIN_SIZE, Constants.PROJECTILE_MAX_SIZE);
    }

    //This method modifies the projectileStartPoint, by a new random Point, which is always out of the displayed Panel.
    private void setStartPosition() {

        //different cases are: 1=west, 2=north, 3=east, 4=south
        int differentCases = random.nextInt(1,4);

        int startX = 0;
        int startY = 0;

        /*
          Case 1: StartPoint is on the west side
          Case 2: StartPoint is on the north side
          Case 3: StartPoint is on the east side
          Case 4: StartPoint is on the south side
         */

        switch (differentCases) {
            case 1:
                startX = 0;
                startY = random.nextInt(0, Constants.CANVAS_HEIGHT);
                break;
            case 2:
                startX = random.nextInt(0, Constants.CANVAS_WIDTH);
                startY = 0;
                break;
            case 3:
                startX = Constants.CANVAS_WIDTH;
                startY = random.nextInt(0, Constants.CANVAS_HEIGHT);
                break;
            case 4:
                startX = random.nextInt(0, Constants.CANVAS_WIDTH);
                startY = Constants.CANVAS_HEIGHT;
                break;
        }
        projectileStartPoint = new Point(startX, startY);
    }

    //This method increases the lower- and upperBorderSpeed, by the Constant PROJECTILE_LEVEL_SPEED_UP
    public void updateSpeedPerLevel() {
        lowerBorderSpeed += Constants.PROJECTILE_LEVEL_SPEED_UP;
        upperBorderSpeed += Constants.PROJECTILE_LEVEL_SPEED_UP;
    }

    //This method resets the lower- and upperBorderSpeed, by the constants PROJECTILE_MIN_START_SPEED and PROJECTILE_MAX_START_SPEED;
    public void resetSpeed() {
        lowerBorderSpeed = Constants.PROJECTILE_MIN_START_SPEED;
        upperBorderSpeed = Constants.PROJECTILE_MAX_START_SPEED;
    }

}
