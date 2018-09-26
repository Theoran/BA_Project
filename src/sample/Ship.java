package sample;

import javafx.scene.image.Image;

public class Ship extends Sprite implements IShip {

    private double health;
    private Image shotImage;
    private double shotVelocity;
    private double timeSinceLastShot;
    private double shotDmg;

    public double getHealth() {
        return this.health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public void setShotImage(String shotImage) {
        Image img = new Image(shotImage);
        this.shotImage = img;
    }

    public Image getShotImage(){
        return this.shotImage;
    }

    public double getShotVelocity(){
        return this.shotVelocity;
    }

    public void setShotVelocity(double shotVelocity){
        this.shotVelocity = shotVelocity;
    }

    public double getTimeSinceLastShot(){
        return this.timeSinceLastShot;
    }
    public void resetTimeSinceLastShot(){
        this.timeSinceLastShot = 0;
    }
    public void addToTimeSinceLastShot(double time){
        this.timeSinceLastShot += time;
    }

    public double getShotDmg(){
        return this.shotDmg;
    }

    public void setShotDmg(double dmg){
     this.shotDmg = shotDmg;
    }

    public void getHit(Projectile projectile){
        this.health = this.health - projectile.getDmg();
    }

    public Projectile shoot(){
        Projectile projectile = new Projectile(this.shotImage, this.shotDmg, this.getPosition_x(), this.getPosition_y() + 0.5*this.getHeight(), this.shotVelocity);
        return projectile;
    }
}