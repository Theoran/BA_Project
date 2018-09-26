package sample;

public class EnemyFactory {

    public EnemyShip spawnEnemy(double time) {
        EnemyShip enemy = new EnemyType1();
        enemy.setPosition(1350, Math.random()*620);
        enemy.setVelocity(-200, 0);
        enemy.setFriendly(false);
        return enemy;
    }

    class EnemyType1 extends EnemyShip {
        public EnemyType1 () {
            this.setImage("sample/enemyBlack1.png");
        }
    }
}
