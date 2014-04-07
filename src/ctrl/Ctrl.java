/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ctrl;

import ihm.Ihm;
import ihm.SoundtrackPlayer;
import java.awt.image.BufferedImage;
import wrk.Wrk;

/**
 *
 * @author sallinlu
 */
public class Ctrl {
    private Ihm ihm;
    private SoundtrackPlayer sdtp;
    private Wrk wrk;

    public void setIhm(Ihm ihm) {
        this.ihm = ihm;
    }

    public void setWrk(Wrk wrk) {
        this.wrk = wrk;
    }

    public void start() {
        ihm.setVisible(true);
        sdtp = new SoundtrackPlayer();
        sdtp.start();
    }

    public void afficheFram(BufferedImage bi) {
        ihm.afficheFrame(bi);
    }

    public void avance(String vitesse) {
        wrk.avance(vitesse);
    }
    public void stopRobot(){
        wrk.stopRobot();
    }

    public void recule(String vitesse) {
        wrk.recule(vitesse);
    }

    public void tourne(boolean left) {
        wrk.tourne(left);
    }

    public void connecteWebcam() {
        wrk.connecteWebcam();
            }

    public void afficheMessage(String msg) {
        ihm.afficheMessage(msg);
    }

    public void connecteRobot() {
        wrk.connecteRobot();
    }

    public void connecteManette() {
        wrk.connecteManette();
    }

    public void playSound(String url) {
        ihm.playSound(url);
    }
    
    
}
