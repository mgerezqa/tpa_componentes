package domain.formulario;

public class EntradaTelefono extends Entrada{
    private String telefonoIngresado;

    @Override
    public void ingresarRespuesta(String nroTelefono) {
        //las validaciones se hacen para cada operador de telefonía
        this.telefonoIngresado = nroTelefono;
    }


    @Override
    public String obtenerRespuesta() {
        return telefonoIngresado.toString();
    }


}
