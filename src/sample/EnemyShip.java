package sample;

public class EnemyShip extends Ship {

    public double getTimeSinceLastShot() {
        return timeSinceLastShot;
    }

    public void setTimeSinceLastShot(double timeSinceLastShot) {
        this.timeSinceLastShot = timeSinceLastShot;
    }

    private double timeSinceLastShot = 0;




}
