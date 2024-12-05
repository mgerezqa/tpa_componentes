package utils.validadorDeContrasenias.politicas;
import java.util.Objects;

public class DataRepetida implements IPoliticas{
    @Override
    public void chequearPolitica(String nombreUsuario, String contrasenia) throws Exception {
        if(contrasenia.contains(nombreUsuario)){
            throw new Exception("El nombre de usuario no puede ser igual a la contraseña");
        }
        return;
    }
}

