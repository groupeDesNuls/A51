package wrk;

import ctrl.Ctrl;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sallinlu
 */
public class Wrk {

    private Ctrl ctrl;
    private WrkStreamVisualiseur wrkWebcam;
    private WrkStreamVisualiseurDetectColor wrkDetect;
    private WrkRobot wrkRobot;
    private WrkManette wrkManette;
    private boolean webcamOn = false;
    private boolean robotOn = false;
    private boolean manetteOn = false;
    private final static int TIMEOUT = 10000;

    public Wrk() {
    }

    public void connecteRobot() {
        if (!robotOn) {
            wrkRobot = new WrkRobot(this);
            wrkRobot.start();
            int compteurWhile = 0;
            System.out.println("Chargement robot");
            while (!wrkRobot.isConnected() && compteurWhile < TIMEOUT) {
                compteurWhile++;
                try {
                    Thread.sleep(1);
                } catch (Exception e) {
                }
            }
            if (compteurWhile < 10000) {

                ctrl.playSound("C:/Users/sallinlu/Desktop/A51/Projet/Projet/src/sound/robotco.wav");
                ctrl.afficheMessage("Le robot a été connecté !");
                robotOn = true;
            } else {
                ctrl.afficheMessage("Impossible de connecter le robot au bout de 10 secondes !");
            }
        } else {
            ctrl.playSound("C:/Users/sallinlu/Desktop/A51/Projet/Projet/src/sound/already.wav");
            ctrl.afficheMessage("Le robot est déjà connectée !");
        }

    }

    public void connecteManette() {
        if (!manetteOn) {
            wrkManette = new WrkManette(this);
            wrkManette.start();
            int compteurWhile = 0;
            while (!wrkManette.isConnected() && compteurWhile < TIMEOUT) {
                System.out.println("manette   " + compteurWhile);
                compteurWhile++;
                try {
                    Thread.sleep(1);
                } catch (Exception e) {
                }
            }
            if (compteurWhile < 10000) {
                ctrl.playSound("C:/Users/sallinlu/Desktop/A51/Projet/Projet/src/sound/controllerco.wav");
                ctrl.afficheMessage("La manette a été connecté !");
                manetteOn = true;
            } else {
                ctrl.afficheMessage("Impossible de connecter la manette au bout de 10 secondes !");
            }
        } else {
            ctrl.playSound("C:/Users/sallinlu/Desktop/A51/Projet/Projet/src/sound/already.wav");
            ctrl.afficheMessage("La manette est déjà connectée !");
        }


    }

    public void connecteWebcam() {
        if (wrkDetect == null || !wrkDetect.isRunning()) {
            wrkDetect = new WrkStreamVisualiseurDetectColor(this);
            wrkDetect.start();
            ctrl.playSound("C:/Users/sallinlu/Desktop/A51/Projet/Projet/src/sound/camco.wav");
            ctrl.afficheMessage("La caméra a été connectée !");
        } else {
            ctrl.playSound("C:/Users/sallinlu/Desktop/A51/Projet/Projet/src/sound/already.wav");
            ctrl.afficheMessage("La webcam est déjà connectée !");
        }

    }

    public void setCtrl(Ctrl ctrl) {
        this.ctrl = ctrl;
    }

    void afficheFrame(BufferedImage bi) {
        ctrl.afficheFram(bi);
    }

    public void startWebcam() {
        wrkWebcam.start();
    }

    public void avance(String vitesse) {
        wrkRobot.avance(vitesse);
    }

    public void stopRobot() {
        wrkRobot.stopRobot();
    }

    public void recule(String vitesse) {
        wrkRobot.recule(vitesse);
    }

    public void tourne(boolean left) {
        wrkRobot.tourne(left);
    }

    void tourneEnMarche(boolean left) {
        wrkRobot.tourneEnMarche(left);
    }

    void klaxonne() {
        wrkRobot.klaxonne();
    }

    boolean isRobotOn() {
        boolean connected = false;
        if (wrkRobot != null) {
            connected = wrkRobot.isConnected();
        }
        return connected;
    }
}
