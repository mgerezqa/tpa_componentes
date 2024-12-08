package controladores;

import domain.usuarios.ColaboradorFisico;
import utils.cargaMasiva.ImportadorCSV;

import java.io.File;
import java.util.List;

public class ControladorCargaMasiva {

    private final ImportadorCSV importadorCSV;

    public ControladorCargaMasiva(ImportadorCSV importadorCSV) {
        this.importadorCSV = importadorCSV;
    }

    public String procesarCargaMasiva(File archivo, List<ColaboradorFisico> colaboradores) {
        try {
            importadorCSV.importarDonaciones(archivo.getAbsolutePath(), colaboradores);
            return "Archivo procesado correctamente.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al procesar el archivo: " + e.getMessage();
        }
    }
}
