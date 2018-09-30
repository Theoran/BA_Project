package sample;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.ArrayList;

public class GameScene {

    //Canvas + Graphicscontext
    private static Canvas gameCanvas = new Canvas(1280, 600);
    private static GraphicsContext gc = gameCanvas.getGraphicsContext2D();

    //Layout des Game Menues
    private static Group root = new Group();

    //Scene des Game Menues wird initialisiert
    private static Scene game = new Scene(root, 1280, 600);

    // Hintergrund
    private static Image bgImage = new Image("Pictures/Background/Space.jpg");

    // Spielfigur erstellen
    private static Playership playership = new Playership();

    // ArrayList eigene Projektile
    private static ArrayList<Projectile> myProjectileList = new ArrayList<>();

    // ArrayList feindliche Projektile
    private static ArrayList<Projectile> enemyProjectileList = new ArrayList<>();

    // ArrayList Gegner
    private static ArrayList<Enemy>enemyList = new ArrayList<>();

    // ArrayList Player Inputs
    private static ArrayList<String> userInput = new ArrayList<>();

    // Instanz zum Gegner Spawn
    private static EnemyFactory factory = new EnemyFactory();

    //Integer für Highscore
    Highscore highscore = new Highscore(0);


    public static void initialize(Stage stage) {
        root.getChildren().addAll(gameCanvas);

        game.setOnKeyPressed(
                e -> {
                    String code = e.getCode().toString();
                    if(!userInput.contains(code))
                        userInput.add(code);
                }
        );
        game.setOnKeyReleased(
                e -> {
                    String code = e.getCode().toString();
                    userInput.remove(code);
                }
        );

        stage.setScene(game);
        stage.centerOnScreen();
    }

    static public void gameOver() {
        System.exit(0);
    }

    public static AnimationTimer GameLoop = new AnimationTimer(){
        long lastNanoTime = (System.nanoTime());
        double elapsedTime;
        double overallTime = 0;
        double timeSinceSpawn = 0;

        public void handle(long currentNanoTime)
        {
            elapsedTime = (currentNanoTime - lastNanoTime)  / 1e9f;
            lastNanoTime = currentNanoTime;
            overallTime += elapsedTime;
            timeSinceSpawn += elapsedTime;
            playership.addToTimeSinceLastShot(elapsedTime);

            gc.drawImage(bgImage, 0, 0);

            String scrore = "Highscore ";

            // SCHLEIFE GEGNER PROJEKTILE
            for (int i=0; i<enemyProjectileList.size(); i++) {
                Projectile p = enemyProjectileList.get(i);
                if (playership.intersects(p)) {
                    playership.takeDamage(p.getDmg());
                    if (playership.getHealth() <= 0) {
                        Sound.sound(Sound.playerShipDestroyed, 0.25);
                        gameOver();
                    }
                    enemyProjectileList.remove(p);
                }
                if (p.getPosition_x() <= 0 - p.getWidth()) {
                    enemyProjectileList.remove(p);
                    continue;
                }
                p.update(elapsedTime);
                p.render(gc);
            }

            // SCHLEIFE GEGNER
            for (int i=0; i<enemyList.size(); i++) {
                Enemy e = enemyList.get(i);

                e.addToTimeSinceLastShot(elapsedTime);

                if (playership.intersects(e)) {
                    Sound.sound(Sound.playerShipDestroyed, 0.25);
                    gameOver();
                }

                if (e.getPosition_x() <= 0 - e.getWidth()) enemyList.remove(e);

                // UNTERSCHLEIFE: FUER JEDEN GEGENR DURCH SPIELER PROJEKTILE SCHLEIFEN UND ABFRAGEN OB TREFFER
                for (int j=0; j<myProjectileList.size(); j++) {
                    Projectile myP = myProjectileList.get(j);
                    if (e.intersects(myP)) {
                        e.takeDamage(myP.getDmg());
                        if (e.getHealth() <= 0) {
                            enemyList.remove(e);
                            Sound.sound(Sound.enemyShipDestroyed, 0.4);

                        }
                        myProjectileList.remove(myP);
                    }
                }

                // WENN GENUG ZEIT VERGANGEN DANN SCHIESSEN
                if (e.getTimeSinceLastShot() >= 4){
                    enemyProjectileList.add(e.shoot());
                    Sound.sound(Sound.enemyShipLaser, 0.25);
                    e.resetTimeSinceLastShot();
                }

                e.update(elapsedTime);
                e.render(gc);
                System.out.println(e);
            }

                /*
                Gegner-Spawn Wave-System
                if(!wave.updateEnemies()) {
                    wave = new Wave(3, "sample/playership1_orange.png", 300);
                }

                ArrayList<EnemyShip> enemyList = wave.getWaveList();
                */

            playership.setVelocity(0, 0);
            if (userInput.contains("UP")) playership.addVelocity(0, -500);
            if (userInput.contains("DOWN")) playership.addVelocity(0, 500);
            if (userInput.contains("LEFT")) playership.addVelocity(-400, 0);
            if (userInput.contains("RIGHT")) playership.addVelocity(400, 0);

            if (userInput.contains("SPACE") && playership.getTimeSinceLastShot() >= 0.1) {
                Projectile activeProjectile = playership.shoot();
                activeProjectile.setDmg(playership.getShotDmg());
                myProjectileList.add(activeProjectile);
                playership.resetTimeSinceLastShot();
                Sound.sound(Sound.playerShipLaser, 0.2);
            }

            // Random-Spawn per Factory
            if (timeSinceSpawn >= 1.1) {
                Enemy enemy = factory.spawnEnemy(overallTime);
                enemyList.add(enemy);
                timeSinceSpawn = 0;
            }

            playership.update(elapsedTime);
            playership.render(gc);

            for (int i=0; i<myProjectileList.size(); i++) {
                Projectile p = myProjectileList.get(i);
                if (p.getPosition_x() > 1280) {
                    myProjectileList.remove(p);
                    continue;
                }
                p.update(elapsedTime);
                p.render(gc);
            }

        } // END HANDLE

    }; // END DEFINITION GameLoop

} // END GameScene