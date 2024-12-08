package utils.validadorDeContrasenias.validador;
import lombok.Data;
import lombok.Setter;
import utils.validadorDeContrasenias.politicas.IPoliticas;
import java.util.ArrayList;
import java.util.List;

@Setter
@Data
public class Validador {

    private List<IPoliticas> ipoliticas = new ArrayList<>();
    private List<Exception> excepciones = new ArrayList<>();

    public Validador() {}

    public void agregarPolitica(IPoliticas politica) {
        ipoliticas.add(politica);
    }

    public void eliminarPolitica(IPoliticas politica) {
        ipoliticas.remove(politica);
    }

    // -------------------------------------------------------- //

    public boolean validarContrasenia(String nombreUsuario, String contrasenia) throws Exception {
        excepciones.clear();

        for(IPoliticas politica : ipoliticas){
            try{
                politica.chequearPolitica(nombreUsuario, contrasenia);
            }
            catch(Exception e)
            {
                excepciones.add(e);
            }
        }

        if(!excepciones.isEmpty()){
            for(Exception excepcion : excepciones){
                System.err.println(excepcion.getMessage());
            }
            return false;
        }
        System.out.println("La contraseña fue validada con éxito.");
        return true;
    }
}
