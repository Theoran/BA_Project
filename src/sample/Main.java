package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import sun.audio.*;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;


public class Main extends Application {



    public Wave wave;


    public static void main(String[] args) {launch(args);}

    @Override
    public void start(Stage primaryStage) throws Exception{

        //Canvas für Playership
        Canvas game_menu_c = new Canvas(1280, 720);
        GraphicsContext gc = game_menu_c.getGraphicsContext2D();

        Image space = new Image("sample/Space.jpg");

        Ship playership = new Ship();
        playership.setImage("sample/playerShip1_orange.png");
        playership.setPosition(0, 600);

        //Buttons zum Start und Verlassen des Spiels
        Button start_game_b = new Button("Start a new Game");
        Button exit_b = new Button("Close");


        //Layout des Main Menues inklusive Zuweisung und Positionierung der Buttons
        VBox main_menue_l = new VBox();
        main_menue_l.setAlignment(Pos.CENTER);
        main_menue_l.setSpacing(10);
        main_menue_l.getChildren().addAll(start_game_b, exit_b);


        //Scene des Main Menues wird initialisiert
        Scene main_menue_s = new Scene(main_menue_l);


        //Main Menu Scene dem Primary Stage zuweisen
        primaryStage.setScene(main_menue_s);
        primaryStage.show();

        //Layout des Game Menues
        Group game_menu_l = new Group();
        game_menu_l.getChildren().addAll(game_menu_c);


        //Scene des Game Menues wird initialisiert
        Scene game_menu_s = new Scene(game_menu_l, 1280, 720);


        //Eventhandler für Start- und Exit-Button
        start_game_b.setOnAction(e -> {
            primaryStage.setScene(game_menu_s);
            System.out.println("Spiel gestartet...");

            AudioPlayer soundLoop = AudioPlayer.player;
            AudioStream BGM;
            AudioData MD;
            ContinuousAudioDataStream loop = null;
            try{
                System.out.println("Test");
                BGM = new AudioStream(new FileInputStream("sample/Ultima_Weapon.mp3"));
                MD = BGM.getData();
                loop = new ContinuousAudioDataStream(MD);

            }catch(Exception e1){}

            soundLoop.start(loop);
        });


        exit_b.setOnAction(e -> {
            primaryStage.close();
            System.out.println("Spiel beendet...");
        });


        ArrayList<String> userinput = new ArrayList<>();


        game_menu_s.setOnKeyPressed(
                e -> {
                    String code = e.getCode().toString();
                    if(!userinput.contains(code))
                        userinput.add(code);
                }
        );


        game_menu_s.setOnKeyReleased(
                e -> {
                    String code = e.getCode().toString();
                    userinput.remove(code);
                }
        );


        ArrayList<Projectile>myProjectileList = new ArrayList<>();

        ArrayList<Projectile>foeProjectileList = new ArrayList<>();

        wave = new Wave(4, "sample/enemyBlack1.png", 200);


        new AnimationTimer(){
            float lastShotTime = 0;
            int waveCounter = 0;
            LongValue lastNanoTime = new LongValue(System.nanoTime());

            public void handle(long currentNanoTime)
            {
                double elapsedTime = (currentNanoTime - lastNanoTime.value)  / 1e9f;
                lastShotTime += elapsedTime;
                lastNanoTime.value = currentNanoTime;

                playership.setVelocity(0,0);

                gc.drawImage(space, 0, 0);

                if(!wave.updateEnemies()) {
                    wave = new Wave(3, "sample/playership1_orange.png", 300);
                }


                ArrayList<EnemyShip> enemyList = wave.getWaveList();


                if (userinput.contains("UP")) playership.addVelocity(0, -400);
                if (userinput.contains("DOWN")) playership.addVelocity(0, 400);
                if (userinput.contains("LEFT")) playership.addVelocity(-400, 0);
                if (userinput.contains("RIGHT")) playership.addVelocity(400, 0);

                if (userinput.contains("SPACE") && lastShotTime >= 0.25){
                    Projectile activeProjectile = new Projectile("sample/laserBlue06.png");
                    activeProjectile.setPosition(playership.getPosition_x() + 75, playership.getPosition_y() + 43);
                    activeProjectile.setVelocity(400, 0);
                    myProjectileList.add(activeProjectile);
                    lastShotTime = 0;
                }


                playership.update(elapsedTime);
                playership.render(gc);


                for (Projectile projectile : myProjectileList) {
                    projectile.update(elapsedTime);
                    projectile.render(gc);
                }


                for (Projectile projectile : foeProjectileList) {
                    projectile.update(elapsedTime);
                    projectile.render(gc);
                }


                for (EnemyShip enemy : enemyList) {
                    if (playership.intersects(enemy)) {
                        enemyList.remove(enemy);
                    }

                    for (Projectile projectile : myProjectileList) {
                        if (projectile.intersects(enemy)) {
                            enemyList.remove(enemy);
                            myProjectileList.remove(projectile);
                        }
                    }

                    enemy.update(elapsedTime);
                    enemy.setTimeSinceLastShot(enemy.getTimeSinceLastShot() + elapsedTime);

                    if (enemy.getTimeSinceLastShot() > 2) {
                        Projectile bullet = new Projectile("spaceshooter/PNG/Lasers/laserRed08.png");
                        bullet.setVelocity(-400, 0);
                        bullet.setPosition(enemy.getPosition_x(), enemy.getPosition_y() + 43);
                        foeProjectileList.add(bullet);
                        enemy.setTimeSinceLastShot(0);
                    }

                    if (enemy.getPosition_x() <= -85) {
                        enemyList.remove(enemy);
                    }

                    enemy.render(gc);
                }


                for (Projectile projectile : foeProjectileList) {
                    if (projectile.intersects(playership)) {
                        foeProjectileList.remove(projectile);
                        // Playership tot now
                    }
                }

                wave.setWaveList(enemyList);

            }

        }.start();

    }
}
