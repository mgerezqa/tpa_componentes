package domain.carga_masiva;

import com.opencsv.exceptions.CsvValidationException;
import domain.donaciones.*;
import domain.usuarios.Colaborador;
import domain.usuarios.ColaboradorFisico;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImportacionColaboraciones {
    public List<Contribucion> importarColaboracion(String archivo, List<ColaboradorFisico> listaColabFisico, List<ColaboradorFisico> colabImportados) throws CsvValidationException, IOException {
        List<Contribucion> donacionesMigradas = new ArrayList<>();
        LectorCSV unLector = new LectorCSV();
        List<String[]> listado = unLector.obtenerListaDeLecturas(archivo);
        listado.forEach(fila -> donacionesMigradas.add(importar(new ContribucionCSV(fila), listaColabFisico, colabImportados)));
        return donacionesMigradas;
    }
    private Contribucion importar(ContribucionCSV unaLectura, List<ColaboradorFisico> listColabFisico, List<ColaboradorFisico> colabImportados){
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
    private ColaboradorFisico buscarEnLista(ContribucionCSV unaLectura, List<ColaboradorFisico> unaLista){
        return unaLista.stream()
                .filter(unColab -> unColab.identificarPorDocumento(unaLectura.getTipoDoc(), unaLectura.getNroDoc()))
                .findFirst().orElse(null);
    }

    private void enviarCorreo(String direccion){
        EmailSender enviador = new EmailSender();
        String mensajeDefault = "Buenas, le informamos que se le dio de alta en nuestro nuevo sistema de colaboradores.\nSus credenciales son su numero de documento como usuario y contraseña.\nLe solicitamos que ingrese y complete sus datos.";
        enviador.enviarMensaje(direccion, "Alta en nuevo sistema de colaboradores", mensajeDefault);
    }

    private Contribucion generarDonacion(ContribucionCSV unaLectura, Colaborador unColab){
        Contribucion nueva = crearDonacion(unaLectura.getTipoColab());
        if(nueva == null){
            throw new RuntimeException("Tipo de donacion no admitida");
        } else {
            nueva.importarColaborador(unColab);
            return nueva;
        }

    }
    private Contribucion crearDonacion(TipoContribucion unTipo){
        switch(unTipo){
            case DINERO:
                return new Dinero();
            case VIANDA:
                return new Vianda();
            case DISTRIBUCION:
                return new Distribuir();
            case TARJETA:
                return new RegistroDePersonaVulnerable();
            default: return null;
        }
    }

}
