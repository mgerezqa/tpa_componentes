package domain.formulario;

import lombok.Getter;

@Getter
public class EntradaEmail extends Entrada{
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

        // Validar que la entrada sea un email
        if (!entrada.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new IllegalArgumentException("La entrada no es un email válido");
        }
        return entrada;
    }

    @Override
    public String mostrarEntrada() {
        return this.entrada;
    }
}
