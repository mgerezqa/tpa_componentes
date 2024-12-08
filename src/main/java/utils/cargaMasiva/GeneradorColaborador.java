package utils.cargaMasiva;
import config.ServiceLocator;
import domain.contacto.Email;
import domain.formulario.documentos.Documento;
import domain.usuarios.ColaboradorFisico;
import domain.usuarios.Usuario;
import repositorios.repositoriosBDD.RepositorioColaboradores;

public class GeneradorColaborador {

    public static ColaboradorFisico crearColaboradorFisico(RegistroCSV lecturaRegistro) {
        ColaboradorFisico colaborador = new ColaboradorFisico();
        Documento doc = new Documento(lecturaRegistro.getTipoDoc(), String.valueOf(lecturaRegistro.getNroDoc()));
        colaborador.setDocumento(doc);
        colaborador.setNombre(lecturaRegistro.getNombre());
        colaborador.setApellido(lecturaRegistro.getApellido());

        Email email = new Email(lecturaRegistro.getMail());

        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(colaborador.getDocumento().getNumeroDeDocumento());
        usuario.setContrasenia(colaborador.getDocumento().getNumeroDeDocumento());
        colaborador.setUsuario(usuario);

        colaborador.agregarMedioDeContacto(email);

        return colaborador;
    }
}
