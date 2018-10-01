package sample;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;

public class Sound {

    private static Clip musicClip;

    public static Clip getMusicClip(){
        return musicClip;
    }

    public static String musicList[] = {"src/Sound/Music/Shirobon.wav", "src/Sound/Music/Ultima_Weapon.wav"};

    public static synchronized void music(String musicFile, double volume) {

        final String titleName = musicFile;

        try {

            musicClip = AudioSystem.getClip();
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


    public static String playerShipLaser = "src/Sound/Sounds/laser2.wav";
    public static String enemyShipLaser = "src/Sound/Sounds/laser8.wav";
    public static String enemyShipDestroyed = "src/Sound/Sounds/highDown.wav";
    public static String playerShipDestroyed = "src/Sound/Sounds/zapThreeToneDown.wav";
    public static String playerShipHit = "src/Sound/Sounds/twoTone1.wav";


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
