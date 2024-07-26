package domain.formulario.tiposdeentrada;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class EntradaFecha extends Entrada{
    private LocalDate fecha;

    @Override
    public void ingresarRespuesta(String fechaIngresada) {
        this.fecha = LocalDate.parse(fechaIngresada);
    }

    @Override
    public String obtenerRespuesta() {
        return fecha.toString();
    }

}
