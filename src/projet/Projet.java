/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projet;

import ctrl.Ctrl;
import ihm.Ihm;
import wrk.Wrk;

/**
 *
 * @author sallinlu
 */
public class Projet {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Ctrl ctrl = new Ctrl();
        Ihm ihm = new Ihm();
        Wrk wrk = new Wrk();
        
        wrk.setCtrl(ctrl);
        ihm.setCtrl(ctrl);
        ctrl.setIhm(ihm);
        ctrl.setWrk(wrk);
        ctrl.start();
        
        
    }
}
