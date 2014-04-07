package wrk;

import com.googlecode.javacv.FrameGrabber;
import com.googlecode.javacv.FrameRecorder;
import com.googlecode.javacv.OpenCVFrameGrabber;
import com.googlecode.javacv.cpp.opencv_core;
import com.googlecode.javacv.cpp.opencv_core.CvPoint;
import com.googlecode.javacv.cpp.opencv_core.CvRect;
import com.googlecode.javacv.cpp.opencv_core.CvScalar;
import com.googlecode.javacv.cpp.opencv_core.IplImage;
import static com.googlecode.javacv.cpp.opencv_core.cvCreateImage;
import static com.googlecode.javacv.cpp.opencv_core.cvGetSize;
import static com.googlecode.javacv.cpp.opencv_core.cvInRangeS;
import static com.googlecode.javacv.cpp.opencv_core.cvScalar;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_MEDIAN;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvSmooth;
import java.awt.image.BufferedImage;

/**
 *
 * @author sallinlu
 */
public class WrkStreamVisualiseurDetectColor extends Thread {

    private opencv_core.IplImage grabbedImage;
    private FrameGrabber grabbedPlayer;
    private volatile FrameGrabber grabber;
    private opencv_core.CvMemStorage storage;
    private FrameRecorder recorder;
    private Wrk wrk;
    public volatile boolean running = false;
    private volatile boolean pause;
    private volatile boolean record;
    static opencv_core.CvScalar minRed = cvScalar(0, 0, 130, 0);//BGR-A
    static opencv_core.CvScalar maxRed = cvScalar(140, 110, 255, 0);//BGR-A
    static opencv_core.CvScalar minGreen = cvScalar(110, 148, 90, 0);//BGR-A
    static opencv_core.CvScalar maxGreen = cvScalar(150, 255, 135, 0);//BGR-A

    public boolean isRunning() {
        return running;
    }

    public WrkStreamVisualiseurDetectColor(Wrk wrk) {
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
                        opencv_core.IplImage orgImg = grabbedImage;
                        //create binary image of original size
                        grabbedImage = cvCreateImage(cvGetSize(orgImg), 8, 1);
                        //apply thresholding
                        cvInRangeS(orgImg, minRed, maxRed, grabbedImage);
                        //smooth filter- median
                        cvSmooth(grabbedImage, grabbedImage, CV_MEDIAN, 13);
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
