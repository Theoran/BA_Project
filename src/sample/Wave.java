package sample;

import javafx.scene.image.Image;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Wave {

    private ArrayList<EnemyShip> waveList;
    private int enemiesToSpawn;
    private int enemiesSpawned;
    private String image;
    private int vel;

    public Wave(int enemyCount, String image, int vel){
        this.enemiesToSpawn = enemyCount;
        this.enemiesSpawned = 0;
        this.waveList  = new ArrayList<>();
        this.image = image;
        this.vel = vel;

    }

    //waveList setter
    public void setWaveList(ArrayList<EnemyShip> waveList) {
        this.waveList = waveList;
    }
    //waveList getter
    public ArrayList<EnemyShip> getWaveList() {
        return waveList;
    }

    public boolean updateEnemies(){
        boolean needMoreEnemies = enemiesToSpawn != enemiesSpawned;
        if(!needMoreEnemies && waveList.size() == 0){
            return false;
        }

        if(needMoreEnemies && Math.ceil(60 * Math.random()) == 1){
            EnemyShip enemy = new EnemyShip();
            spawnEnemy(enemy);
            enemiesSpawned++;
        }

        return true;
    }

    private void spawnEnemy(EnemyShip enemy){
        double rnd = Math.random()*620;
        boolean rndIsAcceptable = false;

        while (!rndIsAcceptable) {
            rnd = Math.random()*620;
            rndIsAcceptable = true;

            for (int j = 0; j < waveList.size(); j++) {
                double d = waveList.get(j).getPosition_y();
                if  (!(rnd < d - 90 || rnd > d +90)) rndIsAcceptable = false;
            }
        }

        enemy.setImage(image);
        enemy.setPosition(1280, rnd);
        enemy.setVelocity(-vel, 0);

        waveList.add(enemy);
    }
}
