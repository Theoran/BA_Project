package sample;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class Sound {

    public static String musicList[] = {"C:\\Users\\arthur.staehr\\IdeaProjects\\Project_BA\\src\\Sound\\Music\\Shirobon.wav", "C:\\Users\\arthur.staehr\\IdeaProjects\\Project_BA\\src\\Sound\\Music\\Ultima_Weapon.wav"};

    public static synchronized void music(String musicFile) {

        final String titleName = musicFile;

        try {

            Clip musicClip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(titleName));
            musicClip.open(inputStream);
            //musicClip.loop(musicClip.LOOP_CONTINUOUSLY);

        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    public static String playerShipLaser = "C:\\Users\\arthur.staehr\\IdeaProjects\\Project_BA\\src\\Sound\\Sounds\\laser2.wav";

    public static synchronized void sound(String soundFile) {

        final String soundName = soundFile;

        try{

            Clip soundClip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(soundName));
            soundClip.open(inputStream);
            soundClip.loop(0);


        } catch (Exception e) {

            e.printStackTrace();
        }


    }


}
