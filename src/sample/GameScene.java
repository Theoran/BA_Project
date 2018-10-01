package sample;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.ArrayList;

public class GameScene {

    //Canvas + Graphicscontext
    private Canvas gameCanvas = new Canvas(1280, 600);
    private GraphicsContext gc = gameCanvas.getGraphicsContext2D();

    // Stage-Variable für späteres Aufrufen in der handle-Funnktion der GameLoop
    private Stage stage;

    //Layout des Game Menues
    private Group root = new Group();

    //Scene des Game Menues wird initialisiert
    private Scene game = new Scene(root, 1280, 600);

    // Hintergrund
    private Image bgImage = new Image("Pictures/Background/Space.jpg");

    // Spielfigur erstellen
    private Playership playership = new Playership();

    // ArrayList eigene Projektile
    private ArrayList<Projectile> myProjectileList = new ArrayList<>();

    // ArrayList feindliche Projektile
    private ArrayList<Projectile> enemyProjectileList = new ArrayList<>();

    // ArrayList Gegner
    private ArrayList<Enemy>enemyList = new ArrayList<>();

    // ArrayList Asteroiden
    private ArrayList<Asteroid> asteroidList = new ArrayList<>();

    // ArrayList Player Inputs
    private ArrayList<String> userInput = new ArrayList<>();

    // Instanz zum Gegner Spawn
    private EnemyFactory factory = new EnemyFactory();

    //Integer für Highscore
    private int highscore = 0;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return this.stage;
    }


    public void initialize(Stage stage) {
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
        this.GameLoop.start();

        Sound.music(Sound.musicList[(int) Math.round(Math.random())], 0.1);

    }

    private void GameOver() {
        Sound.getMusicClip().close();
        GameOverMenu gameOverMenu = new GameOverMenu();
        gameOverMenu.initialize(this.stage);
    }

    private AnimationTimer GameLoop = new AnimationTimer(){
        long lastNanoTime = (System.nanoTime());
        double elapsedTime;
        double overallTime = 0;
        double timeSinceSpawn = 0;
        double timeSinceAsteroid = 0;

        public void handle(long currentNanoTime)
        {
            elapsedTime = (currentNanoTime - lastNanoTime)  / 1e9f;
            lastNanoTime = currentNanoTime;
            overallTime += elapsedTime;
            timeSinceSpawn += elapsedTime;
            timeSinceAsteroid += elapsedTime;
            playership.addToTimeSinceLastShot(elapsedTime);

            String highscoreText = "Highscore: " + 100 * highscore;

            gc.drawImage(bgImage, 0, 0);
            gc.fillText(highscoreText, 20, 35);
            gc.setFill(Color.WHITE);
            Font highScoreFont = Font.font("Arial", FontWeight.BOLD, 25);
            gc.setFont(highScoreFont);


            // Schleife durch Gegner-Projektile
            for (int i=0; i<enemyProjectileList.size(); i++) {
                Projectile p = enemyProjectileList.get(i);
                if (playership.intersects(p)) {
                    playership.takeDamage(p.getDmg());
                    if (playership.getHealth() <= 0) {
                        Sound.sound(Sound.playerShipDestroyed, 0.25);
                        GameLoop.stop();
                        GameOver();
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

            // Schleife durch Gegner
            for (int i=0; i<enemyList.size(); i++) {
                Enemy e = enemyList.get(i);

                e.addToTimeSinceLastShot(elapsedTime);

            // Sound bei Zerstörung und GameOver-Screen
                if (playership.intersects(e)) {
                    Sound.sound(Sound.playerShipDestroyed, 0.25);
                    GameLoop.stop();
                    GameOver();
                }

                if (e.getPosition_x() <= 0 - e.getWidth()) enemyList.remove(e);

                // Unterschleife: Für jeden Gegner durch Spieler-Projektil-Schleife abfragen, ob Treffer
                for (int j=0; j<myProjectileList.size(); j++) {
                    Projectile myP = myProjectileList.get(j);
                    if (e.intersects(myP)) {
                        e.takeDamage(myP.getDmg());
                        if (e.getHealth() <= 0) {
                            enemyList.remove(e);
                            Sound.sound(Sound.enemyShipDestroyed, 0.4);
                            highscore++;

                        }
                        myProjectileList.remove(myP);
                    }
                }

                // Wenn genug Zeit vergangen ist, dann schießen
                if (e.getTimeSinceLastShot() >= 4){
                    enemyProjectileList.add(e.shoot());
                    Sound.sound(Sound.enemyShipLaser, 0.25);
                    e.resetTimeSinceLastShot();
                }

                e.update(elapsedTime);
                e.render(gc);
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

            // asteroiden Spawn
            if (timeSinceAsteroid >= 7) {
                Asteroid asteroid = new Asteroid();
                boolean intersectsEnemy = true;
                while (intersectsEnemy) {
                    asteroid.setPosition(1300, Math.random()* 500);
                    for (int i=0; i<enemyList.size(); i++) {
                        if (asteroid.intersects(enemyList.get(i))) break;
                    }
                    intersectsEnemy = false;
                }
                asteroidList.add(asteroid);
                timeSinceAsteroid = 0;
            }

            for (int i=0; i<asteroidList.size(); i++) {
                Asteroid a = asteroidList.get(i);
                if (a.intersects(playership)) GameOver();
                else if (a.getPosition_x() <= -10) asteroidList.remove(a);
                a.update(elapsedTime);
                a.render(gc);
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

        } // Ende Handle

    }; // Ende Definition GameLoop

} // Ende GameScene