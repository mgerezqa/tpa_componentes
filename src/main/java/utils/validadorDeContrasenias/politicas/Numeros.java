package utils.validadorDeContrasenias.politicas;
import java.util.Arrays;
import java.util.List;

public class Numeros implements IPoliticas{
    @Override
    public void chequearPolitica(String nombreUsuario, String contrasenia) throws Exception {
        List<Character> numbersCharacters = Arrays.asList('1', '2', '3', '4', '5', '6', '7', '8', '9', '0');

        for (char character : (contrasenia.toCharArray())){
            if(numbersCharacters.contains(character)){
                return;
            }
        }
        throw new Exception("La contraseña no tiene ningún caracter numérico");
    }
}
