package world;

import de.ur.mi.geom.Point;
import de.ur.mi.graphics.Ellipse;
import de.ur.mi.graphics.Image;
import ui.interfaces.Collideable;
import ui.Constants;

public class Core implements Collideable {

    private Ellipse target;
    private Image targetImage;


    public Core(Point orbitCenter) {
        target = new Ellipse(orbitCenter, Constants.CORE_SIZE, Constants.CORE_SIZE, Constants.CORE_COLOR);
        targetImage = new Image(orbitCenter, Constants.CORE_SIZE, Constants.CORE_SIZE, Constants.EARTH_SRC);
        targetImage.setPosition(orbitCenter.getX() - (targetImage.getWidth()/2), orbitCenter.getY() - (targetImage.getHeight()/2));

    }

    public void draw() {
        target.draw();
        targetImage.draw();
    }

    public double getCoreRadius() {
        return target.getWidth()/2.0;
    }

    @Override
    public boolean collidesWith(Projectile projectile) {
        return projectile.distanceTo(target) <= (getSumOfObjectsRadius(projectile) + Constants.TOLERANCE) && projectile.distanceTo(target) >= (getSumOfObjectsRadius(projectile) - Constants.TOLERANCE);
    }

    @Override
    public double getSumOfObjectsRadius(Projectile projectile) {
        return getCoreRadius() + projectile.getRadius();
    }
}
