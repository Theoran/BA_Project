package sample;
import javafx.scene.image.Image;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class Wave {

    private ArrayList<EnemyShip> waveList = new ArrayList<>();


    public ArrayList<EnemyShip> getWaveList() {
        return waveList;
    }

    public void fillList(int x, Image image) {
        for (int i = 0; i < x; i++) {
            EnemyShip enemy = new EnemyShip();
            enemy.setImage(image);
            enemy.setPosition(1000, 350 * Math.random() + 50);
            enemy.setVelocity(-200, 0);

            waveList.add(enemy);
        }
    }

    public void fillList(int x, String image, int vel) {

        for (int i = 0; i < x; i++) {

            double rnd = Math.random()*620;
            boolean rndIsAcceptable = false;

            while (!rndIsAcceptable) {
              rnd = Math.random()*620;
              rndIsAcceptable = true;

              for (int j = 0; j < waveList.size(); j++) {
                  Double d = waveList.get(j).getPosition_y();
                  if  (!(rnd < d - 90 || rnd > d +90)) rndIsAcceptable = false;
              }
            }

            EnemyShip enemy = new EnemyShip();
            enemy.setImage(image);
            enemy.setPosition(1280, rnd);
            enemy.setVelocity(-vel, 0);

            waveList.add(enemy);
        }
    }



}
