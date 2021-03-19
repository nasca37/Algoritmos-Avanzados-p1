/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc_esdeveniments.vista.Vista;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import mvc_esdeveniments.MeuError;
import mvc_esdeveniments.model.Model;

/**
 *
 * @author mascport
 */
public class PanellDibuix extends JPanel {

    private int w;
    private int h;
    private Model mod;
    private Vista vis;
    protected final int FPS = 1;  // 24 frames per segon
    private final ProcesPintat procpin;
    private BufferedImage bima;

    private final int PADDING = 10;

    private Point2D computacionalOn[];
    private Point2D computacionalOn2[];
    private Point2D computacionalOn3[];

    double factorEscaladoX = 0;
    double factorEscaladoY = 0;

    public PanellDibuix(int x, int y, Model m, Vista v) {
        w = x;
        h = y;
        mod = m;
        vis = v;
        bima = null;
        this.setPreferredSize(new Dimension(w, h));
        procpin = new ProcesPintat(this);
        procpin.start();

    }

    public void llenarGraficas() {

        computacionalOn = mod.getComputacionalOn();

        computacionalOn2 = mod.getComputacionalOn2();
        computacionalOn3 = mod.getComputacionalOn3();
        factorEscaladoX = (double) (this.w - PADDING) / (double) mod.getELEMENTS();
        factorEscaladoY = (double) (this.h - PADDING) / (double) 300;
        this.repaint();
    }


    public void repaint() {
        if (this.getGraphics() != null) {
            paint(this.getGraphics());
        }
    }

    public void paint(Graphics gr) {
        Graphics2D gr2 = (Graphics2D) gr;
        if (bima == null) {
            if (this.getWidth() > 0) {
                bima = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
                bima.getGraphics().setColor(Color.white);
                bima.getGraphics().fillRect(0, 0, bima.getWidth(), bima.getHeight());
            }
        }
        gr.drawImage(bima, 0, 0, this);
        Line2D.Double ejeX = new Line2D.Double(PADDING, this.h - PADDING, this.w - PADDING, this.h - PADDING);
        Line2D ejeY = new Line2D.Double(PADDING, PADDING, PADDING, this.h - PADDING);
        gr.setColor(Color.black);
        gr2.draw(ejeX);
        gr2.draw(ejeY);

        gr2.setColor(Color.red);
        pintarGraficas(computacionalOn, gr2);
        gr2.setColor(Color.green);
        pintarGraficas(computacionalOn2, gr2);
        gr2.setColor(Color.blue);
        pintarGraficas(computacionalOn3, gr2);
    }

    public int pintarGraficas(Point2D[] puntos, Graphics2D gr2) {
        if(puntos == null){
            return -1;
        }
        if (puntos[0] == null) {
            return -1;
        } else {
            System.out.println(puntos.length);
        }

        double inversionY = this.h - PADDING;

        for (int i = 0; i <numElements(puntos); i++) {
            System.out.println(i);
            double x = puntos[i].getX();
            double y = puntos[i].getY();
            if ((i + 1) < puntos.length) {
                if (puntos[i + 1] != null) {
                    Line2D.Double linea = new Line2D.Double((double) x * factorEscaladoX + PADDING, inversionY - (double) y * factorEscaladoY, (double) puntos[i + 1].getX() * factorEscaladoX + PADDING, inversionY - (double) puntos[i + 1].getY() * factorEscaladoY);
                    gr2.draw(linea);
                }

            }

        }

        return 0;
    }

    public int numElements(Point2D[] array) {
        int numElements = 0;

        for (int i = 0; i < array.length; i++) {
            if (array[i] != null) {
                numElements++;
            }else{
                return numElements;
            }
        }
        return numElements;
    }

}

class ProcesPintat extends Thread {

    private PanellDibuix pan;

    public ProcesPintat(PanellDibuix pd) {
        pan = pd;
    }

    public void run() {
        long temps = System.nanoTime();
        long tram = 1000000000L / pan.FPS;
        while (true) {
            if ((System.nanoTime() - temps) > tram) {

                pan.llenarGraficas();

                temps = System.nanoTime();
                espera((long) (tram / 2000000));
            }
        }
    }

    private void espera(long t) {
        try {
            Thread.sleep(t);
        } catch (Exception e) {
            MeuError.informaError(e);
        }
    }
}
