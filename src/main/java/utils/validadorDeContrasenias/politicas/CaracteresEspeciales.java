package utils.validadorDeContrasenias.politicas;
import java.util.Arrays;
import java.util.List;

public class CaracteresEspeciales implements IPoliticas{
    @Override
    public void chequearPolitica(String nombreUsuario, String contrasenia) throws Exception {
        List<Character> caracteresEspeciales = Arrays.asList('!', '"', '#', '$', '%', '&', '\'',
                '(', ')', '*', '+', ',', '-', '.', '/', ':', ';', '<', '=', '>', '?', '@',
                '[', '\\', ']', '^', '_', '`', '{', '|', '}', '~',' ','´');

        for (char character : (contrasenia.toCharArray())){
            if(caracteresEspeciales.contains(character)){
                return;
            }
        }
        throw new Exception("La contraseña no tiene ningún caracter especial");
    }
}
