package world;

import de.ur.mi.geom.Point;
import de.ur.mi.graphics.Color;
import de.ur.mi.graphics.Line;
import ui.interfaces.Collideable;
import ui.enums.MovementType;
import config.Constants;

public class Player implements Collideable {

    private Point orbitCenter;
    private double angle;
    private double range;

    private double coreRadius;

    private MovementType typeOrbit;
    private MovementType typeRange;

    private Line player;

    private Point startPosLinePlayer;
    private Point endPosLinePlayer;

    public Player(Point orbitCenter, double startRange, double startAngle, double coreRadius) {

        this.orbitCenter = orbitCenter;
        this.coreRadius = coreRadius;
        angle = startAngle;
        range = startRange;

        calculateStartAndEndOfPlayerLine();
        initPlayer();

    }

    private void initPlayer() {
        player = new Line(startPosLinePlayer, endPosLinePlayer, Constants.PLAYER_COLOR);
        player.setBorderWeight(Constants.PLAYER_LINE_BORDERWEIGHT);
    }

    public void update() {
        updateAngle();
        updateRange();
        updatePosition();
    }

    public void draw() {
        player.draw();
    }

    public void setOrbitMovementType(MovementType type) {
        this.typeOrbit = type;
    }

    public void setRangeMovementType(MovementType type) {
        this.typeRange = type;
    }

    private void updateAngle() {
        if (typeOrbit != null) {
            if (typeOrbit.equals(MovementType.CLOCKWISE)) {
                angle += Constants.ANGLE_SPEED;
            } else if (typeOrbit.equals(MovementType.COUNTER_CLOCKWISE)) {
                angle -= Constants.ANGLE_SPEED;
            }
        }
    }

    private void updateRange() {
        if (typeRange != null) {
            if (typeRange.equals(MovementType.RANGE_UP)) {
                rangeUp();
            } else if (typeRange.equals(MovementType.RANGE_DOWN)) {
                rangeDown();
            }
        }
    }

    private void rangeDown() {
        if ((range-Constants.RANGE_SPEED) >= Constants.MIN_RANGE) {
            range -= Constants.RANGE_SPEED;
        }
    }

    private void rangeUp() {
        if ((range+Constants.RANGE_SPEED) <= Constants.MAX_RANGE) {
            range += Constants.RANGE_SPEED;
        }
    }

    private Point calculateOrbitPosition(Point orbitCenter, double distanceFromCenter, double orbitAngle) {
        double newX = orbitCenter.getX() + distanceFromCenter * Math.cos(Math.toRadians(orbitAngle));
        double newY = orbitCenter.getY() + distanceFromCenter * Math.sin(Math.toRadians(orbitAngle));
        return new Point(newX, newY);
    }

    private void updatePosition() {
        calculateStartAndEndOfPlayerLine();
        player.setDimension(startPosLinePlayer.getX(), startPosLinePlayer.getY(), endPosLinePlayer.getX(), endPosLinePlayer.getY());
    }

    private void calculateStartAndEndOfPlayerLine() {
        startPosLinePlayer = calculateOrbitPosition(orbitCenter, getRangeFromCenter(), angle-(Constants.ANGLE_LINE_BALANCE /getRangeFromCenter()));
        endPosLinePlayer = calculateOrbitPosition(orbitCenter, getRangeFromCenter(), angle+(Constants.ANGLE_LINE_BALANCE /getRangeFromCenter()));
    }

    private double getRangeFromCenter() {
        return range+coreRadius;
    }

    @Override
    public boolean collidesWith(Projectile projectile) {
        return projectile.distanceTo(player) <= (getSumOfObjectsRadius(projectile) + Constants.PLAYER_TOLERANCE) && projectile.distanceTo(player) >= (getSumOfObjectsRadius(projectile) - Constants.PLAYER_TOLERANCE);
    }

    @Override
    public double getSumOfObjectsRadius(Projectile projectile) {
        return (player.getBorderWeight()/2) + projectile.getRadius();
    }

    public void setPlayerColor(Color color) {
        player.setColor(color);
    }
}
