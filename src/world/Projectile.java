package world;

import de.ur.mi.geom.Point;
import de.ur.mi.graphics.Ellipse;
import de.ur.mi.graphics.GraphicsObject;
import de.ur.mi.graphics.Image;
import config.Constants;

public class Projectile {

    private Ellipse projectile;
    private Image projectileImage;

    private double speed;
    private double directionX;
    private double directionY;

    private Point corePos;

    public Projectile(Point corePos, Point startPoint, int size, double speed) {

        this.corePos = corePos;
        this.speed = speed;

        projectile = new Ellipse(startPoint, size, size, Constants.PROJECTILE_COLOR);
        projectileImage = new Image(startPoint, size, size, Constants.PROJECTILE_IMAGE_SRC);
        projectileImage.setPosition(projectile.getX()-(projectileImage.getWidth()/2), projectile.getY() - (projectileImage.getHeight()/2));

        setDirection();

    }

    private void setDirection() {
        directionX = corePos.getX() - projectile.getX();
        directionY = corePos.getY() - projectile.getY();

        double length = Math.sqrt(directionX*directionX + directionY*directionY);

        if (length != 0) {
            directionX = directionX/length;
            directionY = directionY/length;
        }
    }

    public void update() {
        projectile.move(directionX*speed, directionY*speed);
        projectileImage.setPosition(projectile.getX()-(projectileImage.getWidth()/2), projectile.getY() - (projectileImage.getHeight()/2));
    }

    public void draw() {
        projectile.draw();
        projectileImage.draw();
    }

    public double getRadius() {
        return projectile.getWidth()/2.0;
    }

    public double distanceTo(GraphicsObject graphicsObject) {
        return projectile.distanceTo(graphicsObject);
    }

}
