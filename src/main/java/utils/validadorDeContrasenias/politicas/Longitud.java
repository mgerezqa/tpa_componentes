package utils.validadorDeContrasenias.politicas;

public class Longitud implements IPoliticas{
    @Override
    public void chequearPolitica(String nombreUsuario, String contrasenia) throws Exception {
        if(contrasenia.length() < 8 || contrasenia.length() > 64){
            throw new Exception("La clave debe tener entre 8 y 64 caracteres");
        }
        else return;
    }
}
