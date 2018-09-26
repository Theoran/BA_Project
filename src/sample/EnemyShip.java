package sample;

public class EnemyShip extends Ship {

    private boolean isFriendly = false;
    private double shotVelocity = -100;

    public Projectile shoot() {
        Projectile shot = new Projectile("sample/laserBlue06.png", 10, this.getPosition_x(), this.getPosition_y() + 0.5*this.getHeight() ,this.getShotVelocity());
        shot.setFriendly(false);
        return shot;
    }
}
