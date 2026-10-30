package co.edu.uptc.sala.negocio;

import co.edu.uptc.sala.modelo.DatoInvalidoException;
import co.edu.uptc.sala.modelo.EstadoPortatil;
import co.edu.uptc.sala.modelo.Portatil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/** Guarda y lee los portatiles en datos/portatiles.txt. Formato: P-01;Lenovo;DISPONIBLE */
public class ArchivoSala {

    private static final String SEP = ";";

    private Path carpeta;
    private Path archivo;

    public ArchivoSala(String carpeta) {
        this.carpeta = Path.of(carpeta);
        this.archivo = this.carpeta.resolve("portatiles.txt");
    }

    public boolean existe() {
        return Files.exists(archivo);
    }

    public void guardar(List<Portatil> portatiles) throws IOException {
        List<String> lineas = new ArrayList<>();
        for (Portatil p : portatiles) {
            lineas.add(p.getCodigo() + SEP + p.getMarca() + SEP + p.getEstado().name());
        }
        Files.createDirectories(carpeta);
        Files.write(archivo, lineas);
    }

    /** PUNTO 3 · Lee el archivo y devuelve la lista de portatiles. */
    public List<Portatil> cargar() throws IOException, DatoInvalidoException {
        List<Portatil> portatiles = new ArrayList<>();
        List<String> lineas= Files.readAllLines(archivo);
        // PUNTO 3 · Escriba aqui la lectura del archivo.
        for (int i = 0; i < lineas.size(); i++) {
            if (lineas.get(i).isBlank()){
                continue;
            }

            String[] dato= lineas.get(i).split(SEP);
            if (dato.length!=3){
                throw new DatoInvalidoException("Archivo portatiles esta dañado en la linea " + (i+1));
            }

            portatiles.add(new Portatil(dato[0], dato[1], EstadoPortatil.valueOf(dato[2])));
        }
        return portatiles;
    }
}
