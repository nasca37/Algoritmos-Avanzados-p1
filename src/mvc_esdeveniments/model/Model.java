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

    private final int NUMPOINTS = 15;
    private final int ELEMENTS = 100;

    private Point2D[] computacionalOn;
    private Point2D[] computacionalOn2;
    private Point2D[] computacionalOn3;

    private boolean lineal = true;
    private boolean cuadratic = true;
    private boolean logartimic = true;
    
    private boolean dibujando = false;
    public Model(MVC_Esdeveniments p) {
        prog = p;
        computacionalOn = new Point2D[NUMPOINTS];
        computacionalOn2 = new Point2D[NUMPOINTS];
        computacionalOn3 = new Point2D[NUMPOINTS];
    }

    public double lineal(int n) {
        double startTime = System.nanoTime();
        for (int k = 0; k < n; k++) {
            try {
                Thread.sleep(0, 1);
            } catch (InterruptedException ex) {
                Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return System.nanoTime() - startTime;

    }

    public Point2D[] lineal() {


        computacionalOn[0] = new Point2D.Double(0, 0);
        int numLength = 1;
        for (int i = 1; i < NUMPOINTS; i++) {
            int numElementsActual = i * (ELEMENTS / NUMPOINTS);
            double time = lineal(numElementsActual);

            computacionalOn[numLength] = new Point2D.Double(numElementsActual, (time / 10000000));
            numLength++;

        }
        System.out.println("end lineal");
        return computacionalOn;
    }

    public double cuadratic(int n) {
        double startTime = System.nanoTime();
        for (int k = 0; k < n; k++) {
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


        computacionalOn2[0] = new Point2D.Double(0, 0);
        int numLength = 1;

        for (int i = 1; i < NUMPOINTS; i++) {
            int numElementsActual = i * (ELEMENTS / NUMPOINTS);
            double time = cuadratic(numElementsActual);
            computacionalOn2[numLength++] = new Point2D.Double(numElementsActual, (time / 10000000));

        }

        return computacionalOn2;
    }

    public double logaritmic(int n) {
        double startTime = System.nanoTime();
        int logScale = (int) Math.ceil(Math.log(n));
        for (int k = 0; k < n; k++) {

            for (int j = 0; j < logScale; j++) {
                try {
                    Thread.sleep(0, 1);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }

        return System.nanoTime() - startTime;

    }

    public Point2D[] logaritmic() {


        computacionalOn3[0] = new Point2D.Double(0, 0);
        int numLength = 1;

        for (int i = 1; i < NUMPOINTS; i++) {
            int numElementsActual = i * (ELEMENTS / NUMPOINTS);
            double time = logaritmic(numElementsActual);
            computacionalOn3[numLength++] = new Point2D.Double(numElementsActual, (time / 10000000));

        }

        return computacionalOn3;
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

    public Point2D[] getComputacionalOn() {
        return computacionalOn;
    }

    public void setComputacionalOn(Point2D[] computacionalOn) {
        this.computacionalOn = computacionalOn;
    }

    public Point2D[] getComputacionalOn2() {
        return computacionalOn2;
    }

    public void setComputacionalOn2(Point2D[] computacionalOn2) {
        this.computacionalOn2 = computacionalOn2;
    }

    public Point2D[] getComputacionalOn3() {
        return computacionalOn3;
    }

    public void setComputacionalOn3(Point2D[] computacionalOn3) {
        this.computacionalOn3 = computacionalOn3;
    }

    public void vaciarArrays(){
        computacionalOn = new Point2D[NUMPOINTS];
        computacionalOn2 = new Point2D[NUMPOINTS];
        computacionalOn3 = new Point2D[NUMPOINTS];
        this.dibujando = false;
    }

    public boolean isLineal() {
        return lineal;
    }

    public void setLineal(boolean lineal) {
        this.lineal = lineal;
    }

    public boolean isCuadratic() {
        return cuadratic;
    }

    public void setCuadratic(boolean cuadratic) {
        this.cuadratic = cuadratic;
    }

    public boolean isLogartimic() {
        return logartimic;
    }

    public void setLogartimic(boolean logartimic) {
        this.logartimic = logartimic;
    }
    
    
    
    @Override
    public void notificar(String s) {
        if (s.startsWith("dibuixar") && !this.dibujando) {
            
            if (computacionalOn[this.NUMPOINTS-1] == null && this.lineal) {
                this.lineal();
            }
            if (computacionalOn2[this.NUMPOINTS-1] == null && this.cuadratic) {
                this.cuadratic();
            }
            if (computacionalOn3[this.NUMPOINTS-1] == null && this.logartimic) {
                this.logaritmic();
            }
            this.dibujando = true;
        }
    }
}
