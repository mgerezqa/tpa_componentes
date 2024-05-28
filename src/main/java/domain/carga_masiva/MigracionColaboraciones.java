package domain.carga_masiva;

import com.opencsv.exceptions.CsvValidationException;
import domain.contacto.Email;
import domain.donaciones.*;
import domain.usuarios.Colaborador;
import domain.usuarios.ColaboradorFisico;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MigracionColaboraciones {
    public List<Donacion> migrarColaboracion(String archivo, List<ColaboradorFisico> listaColabFisico) throws CsvValidationException, IOException {
        List<Donacion> donacionesMigradas = new ArrayList<>();
        LectorCSV unLector = new LectorCSV();
        List<String[]> listado = unLector.obtenerListaDeLecturas(archivo);
        listado.forEach(fila -> donacionesMigradas.add(migrar(new DonacionCVS(fila), listaColabFisico)));
        return donacionesMigradas;
    }
    private Donacion migrar(DonacionCVS unaLectura, List<ColaboradorFisico> listColabFisico){
        ColaboradorFisico aux = listColabFisico.stream()
                .filter(unColab -> unColab.identificarPorDocumento(unaLectura.getTipoDoc(), unaLectura.getNroDoc()))
                .findFirst().orElse(null);
        if(aux == null){
            ColaboradorFisico nuevoColab = new ColaboradorFisico(unaLectura.getNombre(), unaLectura.getApellido(), unaLectura.getTipoDoc(), unaLectura.getNroDoc(), new Email(unaLectura.getMail()));
            listColabFisico.add(nuevoColab);
            enviarCorreo(unaLectura.getMail());
        }
        return generarDonacion(unaLectura, aux);
    }

    private void enviarCorreo(String direccion){
        EmailSender enviador = new EmailSender();
        String mensajeDefault = "Buenas, le informamos que se le dio de alta en nuestro nuevo sistema de colaboradores.\nSus credenciales son su numero de documento como usuario y contraseña.\nLe solicitamos que ingrese y complete sus datos.";
        enviador.enviarMensaje(direccion, "Alta en nuevo sistema de colaboradores", mensajeDefault);
    }

    private Donacion generarDonacion(DonacionCVS unaLectura, Colaborador unColab){
        Donacion nueva = crearDonacion(unaLectura.getTipoColab());
        if(nueva == null){
            throw new RuntimeException("Tipo de donacion no admitida");
        } else {
            nueva.registrarDonacion(unColab);
            return nueva;
        }

    }
    private Donacion crearDonacion(TipoDonacion unTipo){
        switch(unTipo){
            case DONA_DINERO:
                return new DonacionDinero();
            case DONA_VIANDA:
                return new DonacionVianda();
            case DONA_REPARTO:
                return new DonacionReparto();
            case DONA_TARJETA:
                return new DonacionTarjeta();
            default: return null;
        }
    }

}
