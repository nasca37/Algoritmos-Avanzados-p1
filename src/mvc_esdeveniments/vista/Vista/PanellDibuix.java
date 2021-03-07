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
    protected final int FPS = 60;  // 24 frames per segon
    private final ProcesPintat procpin;
    private BufferedImage bima;

    private final int PADDING = 10;
    private final int TAMPUNTO = 5;
    private final int ESPACIOPUNTO = 20;
    private final int ESPACIOPUNTO2 = 5;
    private final int ESPACIOPUNTO3 = 15;
    private final int NUMELE = 15;

    private Point computacionalOn[];
    private Point computacionalOn2[];
    private Point computacionalOn3[];

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
        this.llenarOdeN();
        this.llenarOdeN2();
        this.llenarOdeLogN();
    }

    public void llenarOdeN() {
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

        pintarGraficas(computacionalOn, gr2);
        pintarGraficas(computacionalOn2, gr2);
        pintarGraficas(computacionalOn3, gr2);
    }

    public void pintarGraficas(Point[] puntos, Graphics2D gr2) {
        for (int i = 0; i < puntos.length; i++) {
            int x = puntos[i].x;
            int y = puntos[i].y;
            gr2.fillRect(x - TAMPUNTO / 2, y - TAMPUNTO / 2, TAMPUNTO, TAMPUNTO);
            if ((i + 1) < puntos.length) {
                Line2D linea = new Line2D.Double(x, y, puntos[i + 1].x, puntos[i + 1].y);
                gr2.draw(linea);
            }
        }
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
                pan.repaint();
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
