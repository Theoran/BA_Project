package sample;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;

public class Sound {

    public static String musicList[] = {"C:\\Users\\arthur.staehr\\IdeaProjects\\Project_BA\\src\\Sound\\Music\\Shirobon.wav", "C:\\Users\\arthur.staehr\\IdeaProjects\\Project_BA\\src\\Sound\\Music\\Ultima_Weapon.wav"};

    public static synchronized void music(String musicFile, double volume) {

        final String titleName = musicFile;

        try {

            Clip musicClip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(titleName));
            musicClip.open(inputStream);
            setMusicVolumen(volume, musicClip);
            musicClip.loop(musicClip.LOOP_CONTINUOUSLY);

        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    //Methode zur Bestimmung der Musiklautstärke
    private static void setMusicVolumen(double volume, Clip soundClip) {

        FloatControl volumeLevel = (FloatControl) soundClip.getControl(FloatControl.Type.MASTER_GAIN);
        Float decibel = (float)(Math.log(volume) / Math.log(10) * 20);
        volumeLevel.setValue(decibel);


    }


    public static String playerShipLaser = "C:\\Users\\arthur.staehr\\IdeaProjects\\Project_BA\\src\\Sound\\Sounds\\laser2.wav";
    public static String shipdestoryed = "C:\\Users\\arthur.staehr\\IdeaProjects\\Project_BA\\src\\Sound\\Sounds\\highDown.wav";

    public static synchronized void sound(String soundFile, double volume) {

        final String soundName = soundFile;

        try{

            Clip soundClip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(soundName));
            soundClip.open(inputStream);
            setSoundVolumen(volume, soundClip);
            soundClip.loop(0);


        } catch (Exception e) {

            e.printStackTrace();
        }


    }

    //Methode zur Bestimmung der Soundlautstärke
    private static void setSoundVolumen(double volume, Clip soundClip) {

        FloatControl volumeLevel = (FloatControl) soundClip.getControl(FloatControl.Type.MASTER_GAIN);
        Float decibel = (float)(Math.log(volume) / Math.log(10) * 20);
        volumeLevel.setValue(decibel);


    }
}
