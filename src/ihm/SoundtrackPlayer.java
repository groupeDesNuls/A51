/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ihm;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 *
 * @author sallinlu
 */
public class SoundtrackPlayer extends Thread {

    @Override
    public void run() {
            try {
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("C:/Users/sallinlu/Desktop/A51/Projet/Projet/src/sound/soundtrack2.wav").getAbsoluteFile());
        Clip clip = null;
        clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.start();
    } catch(Exception ex) {
        System.out.println("Error with playing sound.");
        ex.printStackTrace();
    }
    }
    
    
    
}
