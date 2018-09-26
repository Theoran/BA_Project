package sample;

import javafx.scene.image.Image;

public class Ship extends Sprite {

    private double health = 100;
    private Image shotImage = new Image("sample/laserBlue06.png");
    private double shotVelocity = 450;
    private double timeSinceLastShot = 0;
    private double shotDmg = 40;

    public double getHealth() {
        return health;
    }

    public Image getShotImage() {
        return this.shotImage;
    }

    public void setShotImage(Image shotImage) {
        this.shotImage = shotImage;
    }

    public double getShotVelocity() {
        return shotVelocity;
    }

    public void setShotVelocity(double shotVelocity) {
        this.shotVelocity = shotVelocity;
    }

    public Projectile shoot() {
        Projectile shot = new Projectile("sample/laserBlue06.png", this.shotDmg, this.getPosition_x(), this.getPosition_y() + 0.5*this.getHeight() ,this.getShotVelocity());
        shot.setFriendly(this.getFriendly());
        return shot;
    }

    public double getTimeSinceLastShot() {
        return timeSinceLastShot;
    }

    public void resetTimeSinceLastShot() {
        this.timeSinceLastShot = 0;
    }

    public void addToTimeSinceLastShot(double time) {
        this.timeSinceLastShot += time;
    }

    public double getShotDmg() {
        return shotDmg;
    }

    public void setShotDmg(double dmg) {
        this.shotDmg = dmg;
    }

    public void getHit(Projectile projectile) {
        this.health = this.health - projectile.getDmg();
    }
}