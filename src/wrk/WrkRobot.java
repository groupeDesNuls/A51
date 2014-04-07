/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wrk;

import jssc.SerialPort;
import jssc.SerialPortException;

/**
 *
 * @author sallinlu
 */
public class WrkRobot extends Thread {

    private int baudRate = 9600;
    private int dataBits = 8;
    private int stopBits = 1;
    private int parity = 0;
    private SerialPort serialPort;
    private boolean connected = false;
    private Wrk wrk;

    public boolean isConnected() {
        return connected;
    }

    
    public WrkRobot(Wrk wrk) {
        this.wrk = wrk;
        serialPort = new SerialPort("COM6");
    }

    void avance(String vitesse) {
        envoieMessage("D," + vitesse + "," + vitesse);
    }

    void recule(String vitesse) {
        envoieMessage("D,-" + vitesse + ",-" + vitesse);
    }

    void tourne(boolean left) {
        if (left) {
            envoieMessage("D,-6,6");
            stopRobot();
        }else{
            envoieMessage("D,6,-6");
            stopRobot();
        }
    }

    void stopRobot() {
        
        envoieMessage("D,0,0");
    }

    @Override
    public void run() {
        try {
            System.out.println("Port opened: " + serialPort.openPort());
            System.out.println("Params setted: " + serialPort.setParams(baudRate, dataBits, stopBits, parity));
            connected = true;
        } catch (SerialPortException ex) {
            System.out.println(ex);
        }
    }

    public boolean envoieMessage(String msg) {
        boolean ok = false;
        try {
            msg = msg + "\n";
            ok = serialPort.writeBytes(msg.getBytes());
        } catch (SerialPortException ex) {
        }
        return ok;
    }

    void tourneEnMarche(boolean left) {
        if(left){
            envoieMessage("D,0,10");
        }else{
            envoieMessage("D,10,0");
        }
    }

    void klaxonne() {
        envoieMessage("H,1");
        try{
        Thread.sleep(1000);
        }catch(Exception e){
            
        }
        envoieMessage("H,0");
    }
}
