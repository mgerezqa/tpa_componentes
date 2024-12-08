package utils.validadorDeContrasenias.politicas;

public class CaracteresRepetidos implements IPoliticas {
    @Override
    public void chequearPolitica(String nombreUsuario, String contrasenia) throws Exception {
        int consecutivos = 1;
        char prevChar = '\0';

        for (char character : contrasenia.toCharArray()) {
            if (character == prevChar) {
                consecutivos++;
                if (consecutivos > 3) {
                    throw new Exception("No pueden haber más de 3 caracteres iguales consecutivos");
                }
            } else {
                consecutivos = 1;
            }
            prevChar = character;
        }

        return; // Contraseña válida si no se encuentra ningún problema
    }
}


