package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class Sound {
    Clip clip;
    URL soundURL[] = new URL[30];
    public Sound(){
        soundURL[0] = getClass().getClassLoader().getResource("sound/Moonlight-sonata.wav");
        soundURL[1] = getClass().getClassLoader().getResource("sound/dooropen.wav");
        soundURL[2] = getClass().getClassLoader().getResource("sound/coin.wav");
        soundURL[3] = getClass().getClassLoader().getResource("sound/fanfare.wav");
    }
    public void setFile(int i){
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (Exception e){
        }
    }
    public void play(){
        clip.start();
    }
    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop(){
        clip.stop();
    }
}
