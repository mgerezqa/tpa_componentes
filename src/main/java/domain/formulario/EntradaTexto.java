package domain.formulario;

import lombok.Getter;

@Getter
public class EntradaTexto extends Entrada{
    private String texto;

    @Override
    public void ingresarRespuesta(String textoIngresado) {
        this.texto = validarRespuesta(textoIngresado);
    }

    private String validarRespuesta(String entrada) {
        // Validar que la entrada no sea vacía
        if (entrada.isEmpty()) {
            throw new IllegalArgumentException("La entrada no puede ser vacía");
        }
        // Validar que la entrada no sea mayor a 100 caracteres
        if (entrada.length() > 100) {
            throw new IllegalArgumentException("La entrada no puede ser mayor a 100 caracteres");
        }
        return entrada;
    }

    @Override
    public String obtenerRespuesta() {
        return this.texto;
    }
}
