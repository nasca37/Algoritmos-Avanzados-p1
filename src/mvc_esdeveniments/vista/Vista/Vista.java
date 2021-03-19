/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc_esdeveniments.vista.Vista;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import mvc_esdeveniments.MVC_Esdeveniments;
import mvc_esdeveniments.MeuError;
import mvc_esdeveniments.PerEsdeveniments;

/**
 *
 * @author mascport
 */
public class Vista extends JFrame implements ActionListener, PerEsdeveniments {

    private MVC_Esdeveniments prog;

    public Vista(String s, MVC_Esdeveniments p) {
        super(s);
        prog = p;
        this.getContentPane().setLayout(new BorderLayout());
        JPanel bots = new JPanel();
        JButton boto1 = new JButton("Arrancar");
        boto1.addActionListener(this);
        bots.add(boto1);
        JButton boto2 = new JButton("Reset");
        boto2.addActionListener(this);
        bots.add(boto2);
        this.add(BorderLayout.NORTH, bots);
        PanellDibuix panell = new PanellDibuix(800, 400, prog.getModel(), this);
        JCheckBox lineal = new JCheckBox("O(n)");
        JCheckBox cuadratic = new JCheckBox("O(n^2)");
        JCheckBox logaritmic = new JCheckBox("O(n x log(n))");
        lineal.setSelected(true);
        cuadratic.setSelected(true);
        logaritmic.setSelected(true);
        lineal.addActionListener(this);
        cuadratic.addActionListener(this);
        logaritmic.addActionListener(this);
        
        bots.add(lineal);
        bots.add(cuadratic);
        bots.add(logaritmic);
        this.add(BorderLayout.CENTER, panell);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void mostrar() {
        this.pack();
        this.setVisible(true);
        try {
            Thread.sleep(900);
        } catch (Exception e) {
            MeuError.informaError(e);
        }
        this.revalidate();
        this.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comanda = e.toString();
        int a = comanda.indexOf(",cmd=") + 5;
        comanda = comanda.substring(a, comanda.indexOf(",", a));
        prog.notificar(comanda);
    }

    @Override
    public void notificar(String s) {
    }

}
