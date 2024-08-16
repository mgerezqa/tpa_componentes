package utils.importadorCsv;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import domain.contribucionNuevo.*;
import domain.usuariosNuevo.ColaboradorFisico;
import utils.generadores.GeneradorColaborador;
import utils.generadores.GeneradorContribucion;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Importador { //TODO: en proceso de eliminar clase LectorCSV y usarlo aca directamente

    private static final String asuntoDefault = "Alta en nuevo sistema de colaboradores";
    private static final String mensajeDefault = "Buenas, le informamos que se le dio de alta en nuestro nuevo sistema de colaboradores.\nSus credenciales son su numero de documento como usuario y contraseña.\nLe solicitamos que ingrese y complete sus datos.";

    public List<Contribucion> importarColaboraciones(String archivo, List<ColaboradorFisico> listaColabFisico) throws CsvValidationException, IOException {
        List<Contribucion> contribucionesImportadas = new ArrayList<>();
        String[] lecturaFila;
        EmailSender enviador = new EmailSender();

        CSVReader lectorCSV = new CSVReader(new FileReader(archivo));
        while((lecturaFila = lectorCSV.readNext()) != null){
            contribucionesImportadas.add(importar(new RegistroCSV(lecturaFila), listaColabFisico, enviador));
        }
        lectorCSV.close();
        return contribucionesImportadas;
    }
    private Contribucion importar(RegistroCSV unaLectura, List<ColaboradorFisico> listColabFisico, EmailSender enviador){
        ColaboradorFisico aux = buscarEnLista(unaLectura, listColabFisico);
        if(aux == null){
            aux = GeneradorColaborador.colabFisico(unaLectura);
            listColabFisico.add(aux);
            enviador.enviarMensaje(unaLectura.getMail(), asuntoDefault, mensajeDefault);
        }
        return GeneradorContribucion.generar(unaLectura, aux);
    }

    // esto deberia hacerlo el responsable de la lista(creo?), por ahora lo hacemos aca
    private ColaboradorFisico buscarEnLista(RegistroCSV unaLectura, List<ColaboradorFisico> unaLista){
        return unaLista.stream()
                .filter(unColab -> unColab.identificarPorDocumento(unaLectura.getTipoDoc(), unaLectura.getNroDoc()))
                .findFirst().orElse(null);
    }



}
