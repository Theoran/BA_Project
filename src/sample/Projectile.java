package sample;
import javafx.scene.image.Image;

public class Projectile extends Sprite {

    private double dmg;

    public Projectile(Image img, double dmg, double position_x , double position_y, double velocity_x)
    {
        this.setImage(img);
        this.dmg = dmg;
        this.setPosition(position_x, position_y);
        this.setVelocity(velocity_x, 0);
    }

    public double getDmg() {
        return this.dmg;
    }

    public void setDmg(double dmg) {
        this.dmg = dmg;
    }
}