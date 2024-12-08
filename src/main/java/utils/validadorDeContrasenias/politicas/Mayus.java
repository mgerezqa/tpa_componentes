package utils.validadorDeContrasenias.politicas;

public class Mayus implements IPoliticas{
    @Override
    public void chequearPolitica(String nombreUsuario, String contrasenia) throws Exception {
        for (int i = 0; i < (contrasenia.length()); i++) {
            char character = contrasenia.charAt(i);

            if (Character.isUpperCase(character)) {
                return;
            }
        }
        throw new Exception("La contraseña no tiene ningún caracter en 'mayúscula' ");
    }
}
