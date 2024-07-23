package domain.formulario;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class EntradaFechaNacimiento extends Entrada{
    private LocalDate fecha;

    @Override
    public void ingresarRespuesta(String fechaIngresada) {
        this.fecha = validarEntrada(LocalDate.parse(fechaIngresada));
    }

    private LocalDate validarEntrada(LocalDate entrada) {
        //la fecha de nacimiento no puede ser mayor a la fecha actual
        if(entrada.isAfter(LocalDate.now())){
            throw new IllegalArgumentException("La fecha no puede ser mayor a la fecha actual");
        }
        //la fecha de nacimiento no puede ser menor a 1900
        if(entrada.getYear() < 1900){
            throw new IllegalArgumentException("La fecha no puede ser menor a 1900");
        }

        return entrada;
    }

    @Override
    public String obtenerRespuesta() {
        return fecha.toString();
    }

}
