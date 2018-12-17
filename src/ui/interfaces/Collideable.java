package ui.interfaces;

import world.Projectile;

public interface Collideable {

    boolean collidesWith(Projectile projectile);
    double getSumOfObjectsRadius(Projectile projectile);

}
