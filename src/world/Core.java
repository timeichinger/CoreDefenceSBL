package world;

import de.ur.mi.geom.Point;
import de.ur.mi.graphics.Ellipse;
import de.ur.mi.graphics.Image;
import ui.interfaces.Collideable;
import ui.Constants;

public class Core implements Collideable {

    private Ellipse target;
    private Image targetImage;
    private Point orbitCenter;


    public Core(Point orbitCenter) {
        this.orbitCenter = orbitCenter;
        target = new Ellipse(this.orbitCenter, Constants.CORE_SIZE, Constants.CORE_SIZE, Constants.CORE_COLOR);
        targetImage = new Image(this.orbitCenter, Constants.CORE_SIZE, Constants.CORE_SIZE, Constants.EARTH_SRC);
        targetImage.setPosition(this.orbitCenter.getX() - (targetImage.getWidth()/2), this.orbitCenter.getY() - (targetImage.getHeight()/2));

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
        return projectile.distanceTo(target) <= (getCoreRadius() + projectile.getRadius()) + Constants.TOLERANCE && projectile.distanceTo(target) >= (getCoreRadius() + projectile.getRadius()) - Constants.TOLERANCE;
    }
}
