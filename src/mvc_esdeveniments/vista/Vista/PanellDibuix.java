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
public class PanellDibuix extends JPanel implements MouseListener {

    private int w;
    private int h;
    private Model mod;
    private Vista vis;
    protected final int FPS = 24;  // 24 frames per segon
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
        this.addMouseListener(this);
        this.setPreferredSize(new Dimension(w, h));
        procpin = new ProcesPintat(this);
        procpin.start();
        factorEscaladoX = (double) (this.w - PADDING) /(double) mod.getELEMENTS();
        //  this.llenarOdeN();
        //  this.llenarOdeN2();
        //  this.llenarOdeLogN();
    }

    /*  public void llenarOdeN() {
        computacionalOn = new Point[NUMELE];
        for (int i = 0; i < computacionalOn.length; i++) {
            computacionalOn[i] = new Point(PADDING + i * ESPACIOPUNTO, this.h - PADDING - i * ESPACIOPUNTO);
        }
    }

    public void llenarOdeN2() {
        computacionalOn2 = new Point[NUMELE];
        for (int i = 0; i < computacionalOn2.length; i++) {
            computacionalOn2[i] = new Point(PADDING + i * ESPACIOPUNTO2, this.h - PADDING - i * i * ESPACIOPUNTO2);
        }
    }

    public void llenarOdeLogN() {
        computacionalOn3 = new Point[NUMELE];
        for (int i = 0; i < computacionalOn3.length; i++) {
            computacionalOn3[i] = new Point(PADDING + i * ESPACIOPUNTO3, (int) (this.h - PADDING - Math.log(i) * ESPACIOPUNTO3));
        }
    }
     */
    public void llenarGraficas() {

        computacionalOn = mod.lineal();
        
        computacionalOn2 = mod.cuadratic();
        computacionalOn3 = mod.logaritmic();
        factorEscaladoY = (double) (this.h - PADDING) /(double)  300;
        this.repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        vis.notificar("Picat:" + e.getX() + "," + e.getY());
    }

    @Override
    public void mousePressed(MouseEvent e) {
        ;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        ;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        ;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        ;
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

        if (factorEscaladoY ==0) {
            System.out.println("Fuera");
            return -1;
        }

        double inversionY = this.h - PADDING;



        for (int i = 0; i < puntos.length; i++) {
            double x = puntos[i].getX();
            double y = puntos[i].getY();
            if ((i + 1) < puntos.length) {
                Line2D.Double linea = new Line2D.Double((double) x * factorEscaladoX + PADDING, inversionY -(double) y * factorEscaladoY,(double) puntos[i + 1].getX() * factorEscaladoX+PADDING, inversionY - (double)puntos[i + 1].getY() * factorEscaladoY);
                gr2.draw(linea);
            }
        }
        return 0;
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
        boolean done = false;
        while (true) {
            if ((System.nanoTime() - temps) > tram) {
                pan.repaint();
                if (!done) {
                    pan.llenarGraficas();
                    done = true;
                }
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
