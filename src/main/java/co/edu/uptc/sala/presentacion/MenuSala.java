package co.edu.uptc.sala.presentacion;

import co.edu.uptc.sala.modelo.DatoInvalidoException;
import co.edu.uptc.sala.modelo.EstadoInvalidoException;
import co.edu.uptc.sala.modelo.EstadoPortatil;
import co.edu.uptc.sala.modelo.Portatil;
import co.edu.uptc.sala.negocio.PortatilNoEncontradoException;
import co.edu.uptc.sala.negocio.Sala;
import java.io.IOException;
import javax.swing.JOptionPane;

/** Todo lo que ve el usuario, con JOptionPane. */
public class MenuSala {

    private static final String TITULO = "Sala de sistemas";

    private Sala sala;

    public MenuSala(Sala sala) {
        this.sala = sala;
    }

    public void iniciar() {
        try {
            sala.cargar();
        } catch (IOException | DatoInvalidoException e) {
            avisar("No se pudieron cargar los datos: " + e.getMessage());
        }
        String opcion = "";
        while (!opcion.equals("0")) {
            opcion = mostrarMenu();
            if (opcion.equals("1")) { verPortatiles(); }
            else if (opcion.equals("2")) { prestar(); }
            else if (opcion.equals("3")) { devolver(); }
            else if (opcion.equals("4")) { verResumen(); }
            else if (opcion.equals("0")) {
                guardar();
                JOptionPane.showMessageDialog(null, "Ta luego...", TITULO, JOptionPane.INFORMATION_MESSAGE);
            }
            else { avisar("Esa opcion no existe."); }
        }
    }

    private String mostrarMenu() {
        String menu = "1. Ver portatiles\n"
                + "2. Prestar\n"
                + "3. Devolver\n"
                + "4. Resumen por estado\n"
                + "0. Salir\n\n"
                + "Escriba el numero de la opcion:";
        String opcion = JOptionPane.showInputDialog(null, menu, TITULO, JOptionPane.PLAIN_MESSAGE);
        if (opcion == null) {
            return "0";
        }
        return opcion.trim();
    }

    private String preguntar(String mensaje) {
        String texto = JOptionPane.showInputDialog(null, mensaje, TITULO, JOptionPane.QUESTION_MESSAGE);
        if (texto == null) {
            return "";
        }
        return texto.trim();
    }

    private void avisar(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, TITULO, JOptionPane.ERROR_MESSAGE);
    }

    private void verPortatiles(){
        // PUNTO 4 · Todos los portatiles en UNA ventana PLAIN_MESSAGE.
        String texto="";
        String portatil;
        for (Portatil p: sala.getPortatiles()){
            portatil= p.toString() + "\n";
            texto+= portatil + " ";
        }
        JOptionPane.showMessageDialog(null, texto, TITULO, JOptionPane.PLAIN_MESSAGE);
    }

    private void prestar(){
        // PUNTO 4 · Preguntar el codigo, buscar, confirmar con Si/No, prestar y avisar.
        String codigo= JOptionPane.showInputDialog("Ingrese el codigo del portatil: ");
        try {
            Portatil portatil= sala.buscar(codigo);
            int respuesta= JOptionPane.showConfirmDialog(null, "Prestar " + portatil.getCodigo() + " ("+ portatil.getMarca() + ")?", TITULO, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (respuesta== JOptionPane.YES_OPTION){
                portatil.prestar();
                JOptionPane.showMessageDialog(null, "portatil " + codigo + " prestado.");
            } else {
                JOptionPane.showMessageDialog(null, "No se ha prestado el equipo", TITULO, JOptionPane.ERROR_MESSAGE);
            }
        } catch (PortatilNoEncontradoException e) {
            avisar(e.getMessage());
        } catch (EstadoInvalidoException e) {
            avisar(e.getMessage());
        }
    }

    private void devolver() {
        // PUNTO 4 · Preguntar el codigo, buscar, devolver y avisar.
        String codigo= JOptionPane.showInputDialog("Ingrese el codigo del portatil: ");
        try {
            Portatil portatil = sala.buscar(codigo);
            portatil.devolver();
            JOptionPane.showMessageDialog(null, "portatil " + codigo + " devuelto.");
        } catch (PortatilNoEncontradoException e){
            avisar(e.getMessage());
        } catch (EstadoInvalidoException es){
            avisar(es.getMessage());
        }
    }

    private void verResumen() {
        // PUNTO 4 · Cuantos hay en cada estado, recorriendo EstadoPortatil.values().
        String texto="";
        String cantidad;

        for (EstadoPortatil e: EstadoPortatil.values()){
            cantidad= e.getTexto() + ": " +sala.contarPorEstado(e) + "\n" ;
            texto+= cantidad;
        }
        JOptionPane.showMessageDialog(null, texto, TITULO, JOptionPane.PLAIN_MESSAGE);

    }

    private void guardar() {
        try {
            sala.guardar();
        } catch (IOException e) {
            avisar("No se pudo guardar: " + e.getMessage());
        }
    }
}
