package sample;

import javafx.scene.image.Image;

public class Asteroid extends Sprite {

    public Asteroid(){
        this.setVelocity(-300, 0);
        this.setImage(new Image("/spaceshooter/PNG/Meteors/meteorBrown_med1.png"));
    }
}
