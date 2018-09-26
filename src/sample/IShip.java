package sample;

import javafx.scene.image.Image;

public interface IShip {

    double getHealth();
    void setHealth(double health);

    void setShotImage(String shotImage);
    Image getShotImage();

    double getShotVelocity();
    void setShotVelocity(double shotVelocity);

    double getTimeSinceLastShot();
    void resetTimeSinceLastShot();
    void addToTimeSinceLastShot(double time);

    double getShotDmg();
    void setShotDmg(double dmg);

    Projectile shoot();

    void getHit(Projectile projectile);

}
