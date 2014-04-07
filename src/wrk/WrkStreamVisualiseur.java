/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wrk;

import com.googlecode.javacv.OpenCVFrameGrabber;
import com.googlecode.javacv.FrameGrabber;
import com.googlecode.javacv.FrameRecorder;
import com.googlecode.javacv.OpenCVFrameRecorder;
import com.googlecode.javacv.cpp.opencv_core;
import ihm.Ihm;

import java.awt.image.BufferedImage;

/**
 *
 * @author sallinlu
 */
public class WrkStreamVisualiseur extends Thread {

    private opencv_core.IplImage grabbedImage;
    private FrameGrabber grabbedPlayer;
    private volatile FrameGrabber grabber;
    private opencv_core.CvMemStorage storage;
    private FrameRecorder recorder;
    private Wrk wrk;
    public volatile boolean running = false;
    private volatile boolean pause;
    private volatile boolean record;

    public boolean isRunning() {
        return running;
    }

    
    public WrkStreamVisualiseur(Wrk wrk) {
        this.wrk = wrk;
        running = true;
        pause = false;
    }

    @Override
    public void run() {
        try {
            grabber = new OpenCVFrameGrabber(0);
            grabber.start();
            grabbedImage = grabber.grab();
            int i = 0;
            while (running) {
                if (!pause) {
                    i++;
                    BufferedImage bi = grabbedImage.getBufferedImage();
                    wrk.afficheFrame(bi);
                    try {
                        grabbedImage = grabber.grab();
                        
                    } catch (Exception e) {
                        stopLecture();
                    }
                    if (record) {
                        recorder.record(grabbedImage);
                    }
                }
                sleep(1);
            }
            grabber.stop();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void stopLecture() {
        running = false;
        try {
            join();
        } catch (Exception e) {
            System.out.println(e);
        }
        grabber = null;

        System.gc();
    }
}
