package sample;

public class Projectile extends Sprite {

    private double dmg;

    public Projectile(String filename, double dmg, double position_x , double position_y, double velocity_x)
    {
        this.setImage(filename);
        this.dmg = dmg;
        this.setPosition(position_x, position_y);
        this.setVelocity(velocity_x, 0);
    }

    public double getDmg() {
        return this.dmg;
    }
}