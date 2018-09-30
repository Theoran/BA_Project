package sample;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Sprite {

    private Image image;
    private double position_x;

    public double getPosition_x() {
        return position_x;
    }

    public double getPosition_y() {
        return position_y;
    }

    private double position_y;
    private double velocity_x;
    private double velocity_y;

    public double getWidth() {
        return width;
    }

    private double width;
    private double height;
    private boolean isFriendly = false;

    public void setFriendly() {
        this.isFriendly = true;
    }
    public boolean getFriendly() {
        return this.isFriendly;
    }
    public double getHeight() {
        return height;
    }

    public Sprite(){
        position_x = 0;
        position_y = 0;
        velocity_x = 0;
        velocity_y = 0;
    }

    public void setImage(Image i){
        image = i;
        width = i.getWidth();
        height = i.getHeight();
    }

    public void setImage(String filename){
        Image i = new Image(filename);
        setImage(i);
    }

    public void setPosition(double position_x, double position_y) {
        this.position_x = position_x;
        this.position_y = position_y;
    }

    public void setPositionX(double x){
        this.position_x = x;
    }

    public void setPositionY(double y){
        this.position_y = y;
    }

    public void setVelocity(double x, double y){
        velocity_x = x;
        velocity_y = y;
    }

    public double getVelocity_x() {
        return velocity_x;
    }

    public double getVelocity_y() {
        return this.velocity_y;
    }

    public void addVelocity(double x, double y){
        velocity_x += x;
        velocity_y += y;
    }

    public void update(double time){
        position_x += velocity_x * time;
        position_y += velocity_y * time;
    }

    public void render(GraphicsContext gc){
        gc.drawImage(image, position_x, position_y);
    }

    public Rectangle2D getBoundary(){
        return new Rectangle2D(position_x, position_y, width, height);
    }

    public boolean intersects(Sprite s){
        return s.getBoundary().intersects(this.getBoundary());
    }

    public String toString(){
        return " Position: [" + position_x + "," + position_y + "]" + "Velocity: [" + velocity_x + "," + velocity_y + "]";
    }

}


