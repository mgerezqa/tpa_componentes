package domain.formulario.tiposdeentrada;

public class EntradaNombre extends Entrada{
    private String nombre;

    @Override
    public void ingresarRespuesta(String nombreIngresado) {
        this.nombre = validarRespuesta(nombreIngresado);
    }

    private String validarRespuesta(String entrada) {
        // Validar que la entrada no sea vacía
        if (entrada.isEmpty()) {
            throw new IllegalArgumentException("La entrada no puede ser vacía");
        }
        // Validar que la entrada no sea mayor a 64 caracteres
        if (entrada.length() > 64) {
            throw new IllegalArgumentException("La entrada no puede ser mayor a 64 caracteres");
        }
        //Solo letras y espacios
        if (!entrada.matches("^[a-zA-Z ]*$")) {
            throw new IllegalArgumentException("La entrada solo puede contener letras y espacios");
        }
        return entrada;
    }

    @Override
    public String obtenerRespuesta() {
        return this.nombre;
    }
}

