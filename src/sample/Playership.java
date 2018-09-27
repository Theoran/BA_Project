package sample;

import javafx.scene.image.Image;

public class Playership extends Ship implements IShip {

    private double health = 100;
    private Image shotImage;
    private double shotVelocity;
    private double timeSinceLastShot;
    private double shotDmg;
    private double projectileOffsetX = this.getWidth()*0.5 + 10;

    public Playership() {
        this.setImage("sample/playerShip1_orange.png");
        this.setPosition(50, 360);
        this.setShotImage("sample/laserBlue06.png");
        this.setShotDmg(100);
        this.setShotVelocity(450);
    }

    @Override
    public double getHealth() {
        return this.health;
    }

    @Override
    public void setHealth(double health) {
        this.health = health;
    }

    @Override
    public void setShotImage(String filename) {
        this.shotImage = new Image(filename);
    }

    public void setShotImage(Image shotImage) {
        this.shotImage = shotImage;
    }

    @Override
    public Image getShotImage() {
        return this.shotImage;
    }

    @Override
    public double getShotVelocity() {
        return this.shotVelocity;
    }

    @Override
    public void setShotVelocity(double shotVelocity) {
        this.shotVelocity = shotVelocity;
    }

    @Override
    public double getTimeSinceLastShot() {
        return this.timeSinceLastShot;
    }

    @Override
    public void resetTimeSinceLastShot() {
        this.timeSinceLastShot = 0;
    }

    @Override
    public void addToTimeSinceLastShot(double time) {
        this.timeSinceLastShot += time;
    }

    @Override
    public double getShotDmg() {
        return this.shotDmg;
    }

    @Override
    public void setShotDmg(double shotDmg) {
        this.shotDmg = shotDmg;
    }

    @Override
    public Projectile shoot() {
        return new Projectile(this.shotImage, this.shotDmg, this.getPosition_x() + this.projectileOffsetX, this.getPosition_y() + this.getHeight()*0.5-this.shotImage.getHeight()*0.5, this.getShotVelocity());
    }

    @Override
    public void takeDamage(double dmg) {
        this.health -= dmg;
    }
}
