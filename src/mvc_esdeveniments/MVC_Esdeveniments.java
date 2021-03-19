/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc_esdeveniments;

import mvc_esdeveniments.control.Control;
import mvc_esdeveniments.model.Model;
import mvc_esdeveniments.vista.Vista.Vista;

/**
 *
 * @author mascport
 */
public class MVC_Esdeveniments implements PerEsdeveniments {

    private Model mod;    // Punter al Model del patró
    private Vista vis;    // Punter a la Vista del patró
    private Control con;  // punter al Control del patró

    /*
        Construcció de l'esquema MVC
     */
    private void inicio() {
        mod = new Model(this);
        con = null;
        vis = new Vista("Coste computacional", this);
        vis.mostrar();
    }

    public static void main(String[] args) {
        (new MVC_Esdeveniments()).inicio();
    }

    /*
        Funció símple de la comunicació per Patró d'esdeveniments
     */
    @Override
    public void notificar(String s) {
        if (s.startsWith("Arrancar")) {
            if (con == null) {
                con = new Control(this);
                con.notificar(s);
            }
        } else if (s.startsWith("Reset")) {
            if (con != null) {
                mod.vaciarArrays();
                con.notificar(s);
                con = null;
            }
        } else if (s.startsWith("O(n)")) {
            mod.setLineal(!mod.isLineal());
        } else if (s.startsWith("O(n^2)")) {
            mod.setCuadratic(!mod.isCuadratic());

        } else if (s.startsWith("O(n x log(n))")) {
            mod.setLogartimic(!mod.isLogartimic());
        }

    }

    /*
        Mètode public de retorn de la instància del model de dades
     */
    public Model getModel() {
        return mod;
    }
}
