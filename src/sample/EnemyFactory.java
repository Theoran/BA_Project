package sample;

import javafx.scene.image.Image;


public class EnemyFactory {

    private static boolean isBetween(double x, double floor, double ceiling) {
        return floor <= x && x <= ceiling;
    }

    public Enemy spawnEnemy(double time) {

        Enemy enemy;

        // Zufallszahl zum Bestimmen des GegnerTypen generieren
        double decider = Math.random();

        // Grundzustand: GegnerTyp 1
        enemy = new EnemyType1();

        // je nach Spieldauer ueber  schwereren GegnerTyp entscheiden
        if (isBetween(time, 0, 30)) {
            if (decider > (0.99 - time*0.01)) enemy = new EnemyType2();
        }

        else if (isBetween(time, 30, 60 )) {
            if (decider >= 0.5) enemy = new EnemyType2();
            if (decider > (0.99 ) - (time-30)*0.01) enemy = new EnemyType3();
        }

        enemy.setPosition(1350, Math.random()* 500);
        return enemy;
    }

    class EnemyType1 extends Enemy {

        Image enemyLaser = new Image("Pictures/Effects/Laser/laserRed02.png");

        public EnemyType1 () {
            this.setImage("Pictures/Enemyships/enemyBlack1.png");
            this.setHealth(100);
            this.setVelocity(-100, 0);
            this.setShotVelocity(-200);
            this.setShotImage(enemyLaser);
        }
    }

    class EnemyType2 extends Enemy {

        Image enemyLaser = new Image("Pictures/Effects/Laser/laserGreen14.png");

        public EnemyType2 () {
            this.setImage("Pictures/Enemyships/enemyGreen3.png");
            this.setHealth(150);
            this.setVelocity(-150, 0);
            this.setShotVelocity(-300);
            this.setShotImage(enemyLaser);
        }
    }

    class EnemyType3 extends Enemy {

        Image enemyLaser = new Image("Pictures/Effects/Laser/laserGreen03.png");

        public EnemyType3() {
            this.setImage("Pictures/Enemyships/enemyRed5.png");
            this.setHealth(200);
            this.setVelocity(-200,0);
            this.setShotVelocity(-400);
            this.setShotDmg(30);
            this.setShotImage(enemyLaser);
            }
    }
}
