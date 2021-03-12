/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc_esdeveniments.model;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.logging.Level;
import java.util.logging.Logger;
import mvc_esdeveniments.MVC_Esdeveniments;
import mvc_esdeveniments.PerEsdeveniments;

/**
 *
 * @author mascport
 */
public class Model implements PerEsdeveniments {

    private MVC_Esdeveniments prog;
    //private Point computacionalOn[];
    //private Point computacionalOn2[];
    //private Point computacionalOn3[];

    private final int NUMPOINTS = 10;
    private final int ELEMENTS = 100;

    public Model(MVC_Esdeveniments p) {
        prog = p;

    }

    public Point2D[] lineal() {

        Point2D[] computacionalOn = new Point2D[NUMPOINTS];
        computacionalOn[0] = new Point2D.Double(0, 0);
        double startTime = System.nanoTime();
        double actualTime = 0;
        int numLength = 1;
        for (int i = 0; i < ELEMENTS; i++) {
            try {
                if (i % (ELEMENTS / NUMPOINTS) == 0 && numLength < NUMPOINTS) {
                    actualTime = System.nanoTime() - startTime;
                    computacionalOn[numLength] = new Point2D.Double(i, (actualTime / 10000000));
                    numLength++;
                    //System.out.println("Lmao num elem: " + numLength);
                }
                Thread.sleep(0, 1);
            } catch (InterruptedException ex) {
                Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("Lineal end");
        return computacionalOn;
    }

    public double cuadratic(int n) {
        double startTime = System.nanoTime();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                try {
                    Thread.sleep(0, 1);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }

        return System.nanoTime() - startTime;

    }

    public Point2D[] cuadratic() {

        Point2D[] computacionalOn2 = new Point2D[NUMPOINTS];
        computacionalOn2[0] = new Point2D.Double(0, 0);
        int numLength = 1;
        boolean firstTime = true;
        for (int i = 1; i < NUMPOINTS; i++) {
            double time = cuadratic(i * (ELEMENTS/NUMPOINTS));
            computacionalOn2[numLength++] = new Point2D.Double(i, (time / 10000000));
            firstTime = false;
            System.out.println("Lmao num elem: " + i);

        }

        System.out.println(
                "cuadractic end");
        return computacionalOn2;
    }

    public MVC_Esdeveniments getProg() {
        return prog;
    }

    public void setProg(MVC_Esdeveniments prog) {
        this.prog = prog;
    }

    public int getNUMPOINTS() {
        return NUMPOINTS;
    }

    public int getELEMENTS() {
        return ELEMENTS;
    }

    @Override
    public void notificar(String s) {
        if (s.startsWith("IncGrau")) {

        }
    }
}
