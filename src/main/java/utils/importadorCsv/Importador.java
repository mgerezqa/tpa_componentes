package utils.importadorCsv;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import domain.contribucionNuevo.*;
import domain.usuariosNuevo.Colaborador;
import domain.usuariosNuevo.ColaboradorFisico;
import utils.generadores.GeneradorContribucion;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Importador { //TODO: en proceso de eliminar clase LectorCSV y usarlo aca directamente
    public List<Contribucion> importarColaboracion(String archivo, List<ColaboradorFisico> listaColabFisico, List<ColaboradorFisico> colabImportados) throws CsvValidationException, IOException {
        List<Contribucion> colaboracionesImportadas = new ArrayList<>();
        String[] lecturaFila;
        CSVReader lectorCSV = new CSVReader(new FileReader(archivo));
        while((lecturaFila = lectorCSV.readNext()) != null){
            colaboracionesImportadas.add()
        }
        listado.forEach(fila -> colaboracionesImportadas.add(importar(new RegistroCSV(lecturaFila), listaColabFisico, colabImportados)));
        lectorCSV.close();
        return colaboracionesImportadas;
    }
    private Contribucion importar(RegistroCSV unaLectura, List<ColaboradorFisico> listColabFisico, List<ColaboradorFisico> colabImportados){
        ColaboradorFisico aux = buscarEnLista(unaLectura, listColabFisico);
        if(aux == null){
            aux = buscarEnLista(unaLectura, colabImportados);
            if(aux == null){
                aux = new ColaboradorFisico(unaLectura);
                colabImportados.add(aux);
                enviarCorreo(unaLectura.getMail());
            }
        }
        return generarDonacion(unaLectura, aux);
    }

    // esto deberia hacerlo el responsable de la lista, por ahora lo hacemos aca
    private ColaboradorFisico buscarEnLista(RegistroCSV unaLectura, List<ColaboradorFisico> unaLista){
        return unaLista.stream()
                .filter(unColab -> unColab.identificarPorDocumento(unaLectura.getTipoDoc(), unaLectura.getNroDoc()))
                .findFirst().orElse(null);
    }

    private void enviarCorreo(String direccion){
        EmailSender enviador = new EmailSender();
        String mensajeDefault = "Buenas, le informamos que se le dio de alta en nuestro nuevo sistema de colaboradores.\nSus credenciales son su numero de documento como usuario y contraseña.\nLe solicitamos que ingrese y complete sus datos.";
        enviador.enviarMensaje(direccion, "Alta en nuevo sistema de colaboradores", mensajeDefault);
    }

    private Contribucion generarDonacion(RegistroCSV unaLectura, Colaborador unColab){
        Contribucion nueva = GeneradorContribucion.generar(unaLectura.getTipoColab());
        nueva.registrarColaborador(unColab);
        return nueva;
    }

}
