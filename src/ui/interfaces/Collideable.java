package ui.interfaces;

import world.Projectile;

public interface Collideable {

    public boolean collidesWith(Projectile projectile);
    public double getSumOfObjectsRadius(Projectile projectile);

}
