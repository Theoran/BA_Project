package sample;

public class EnemyFactory {

    private static boolean isBetween(double x, double floor, double ceiling) {
        return floor <= x && x <= ceiling;
    }

    public Ship spawnEnemy(double time) {

        Ship enemy;

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

        enemy.setPosition(1350, Math.random()*620);
        return enemy;
    }

    class EnemyType1 extends Ship {
        public EnemyType1 () {
            this.setImage("sample/enemyBlack1.png");
            this.setHealth(100);
            this.setVelocity(-200, 0);
        }
    }

    class EnemyType2 extends Ship {
        public EnemyType2 () {
            this.setImage("spaceshooter/PNG/Enemies/enemyBlue3.png");
            this.setHealth(150);
            this.setVelocity(-220, 0);
        }
    }

    class EnemyType3 extends Ship {
        public EnemyType3() {
            this.setImage("spaceshooter/PNG/Enemies/enemyRed5.png");
            this.setHealth(200);
            this.setVelocity(-200,0);
            this.setShotVelocity(-300);
            this.setShotDmg(30);
        }
    }
}
