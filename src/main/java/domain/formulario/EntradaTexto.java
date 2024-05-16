package domain.formulario;

import lombok.Getter;

@Getter
public class EntradaTexto extends Entrada{
    private String entrada;

    @Override
    public void cargarEntrada(String entrada) {
        this.entrada = validarRespuesta(entrada);
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
    public String mostrarEntrada() {
        return this.entrada;
    }
}
