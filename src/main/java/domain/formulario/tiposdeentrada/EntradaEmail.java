package domain.formulario.tiposdeentrada;

import lombok.Getter;

@Getter
public class EntradaEmail extends Entrada{
    private String mail;

    @Override
    public void ingresarRespuesta(String mailIngresado) {
        this.mail = validarRespuesta(mailIngresado);
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
    public String obtenerRespuesta() {
        return this.mail;
    }
}
