package co.edu.uptc.sala.modelo;

/** Un portatil de la sala de sistemas. */
public class Portatil {

    private String codigo;
    private String marca;
    private EstadoPortatil estado;   // PUNTO 1 · cree el enum EstadoPortatil

    /** Un portatil nuevo siempre empieza DISPONIBLE. */
    public Portatil(String codigo, String marca) {
        this(codigo, marca, EstadoPortatil.DISPONIBLE);
    }

    /** Para reconstruirlo desde el archivo, con el estado que tenia. */
    public Portatil(String codigo, String marca, EstadoPortatil estado) {
        // PUNTO 2 · Si el codigo o la marca vienen vacios, lance DatoInvalidoException.
        if (codigo.isBlank()){
            throw new DatoInvalidoException("[!] Debe ingresar un codigo");
        } else if (marca.isBlank()) {
            throw new DatoInvalidoException("[!] Debe ingresar un nombre");
        }

        this.codigo = codigo;
        this.marca = marca;
        this.estado = estado;
    }

    public String getCodigo(){
        return codigo;
    }
    public String getMarca(){
        return marca;
    }
    public EstadoPortatil getEstado(){
        return estado;
    }

    /** Solo se presta uno DISPONIBLE. */
    public void prestar() throws EstadoInvalidoException{
        // PUNTO 2 · Si no esta DISPONIBLE, lance EstadoInvalidoException (accion "prestar").
        if (estado== EstadoPortatil.DISPONIBLE) {
            estado = EstadoPortatil.PRESTADO;
        } else if (estado== EstadoPortatil.PRESTADO) {
            throw new EstadoInvalidoException("[!] El portatil ya ha sido prestado.");
        } else {
            throw new EstadoInvalidoException("[!] El computador esta en mantenimiento.");
        }
    }

    /** Solo se devuelve uno PRESTADO. */
    public void devolver() throws EstadoInvalidoException{
        // PUNTO 2 · Si no esta PRESTADO, lance EstadoInvalidoException (accion "devolver").
        if (estado== EstadoPortatil.PRESTADO) {
            estado = EstadoPortatil.DISPONIBLE;
        } else {
            throw new EstadoInvalidoException("[!] Equipo no ha sido prestado.");
        }
    }

    @Override
    public String toString() {
        return codigo + " | " + marca + " | " + estado.getTexto();
    }
}
