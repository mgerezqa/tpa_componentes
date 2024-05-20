package domain.formulario;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class EntradaFecha extends Entrada{
    private LocalDate fecha;

    @Override
    public void cargarEntrada(String entrada) {
        this.fecha = LocalDate.parse(entrada);
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
