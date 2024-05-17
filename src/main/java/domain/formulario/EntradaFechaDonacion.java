package domain.formulario;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class EntradaFechaDonacion extends Entrada{
    private LocalDate fecha;

    @Override
    public void cargarEntrada(String entrada) {
        this.fecha = validarEntrada(LocalDate.parse(entrada));
    }

    private LocalDate validarEntrada(LocalDate entrada) {
        //la fecha no puede ser mayor a la fecha actual
        if(entrada.isAfter(LocalDate.now())){
            throw new IllegalArgumentException("La fecha no puede ser mayor a la fecha actual");
        }
        //la fecha no puede ser menor al año actual
        if(entrada.getYear() < LocalDate.now().getYear()){
            throw new IllegalArgumentException("La fecha no puede ser menor al año actual");
        }
        // no respeta el formato de la fecha
        if(entrada.toString().length() != 10){
            throw new IllegalArgumentException("La fecha no respeta el formato de fecha");
        }

        return entrada;
    }

    @Override
    public String mostrarEntrada() {
        return fecha.toString();
    }

    @Override
    public LocalDate mostrarFecha(){
        return this.fecha;
    }
}
