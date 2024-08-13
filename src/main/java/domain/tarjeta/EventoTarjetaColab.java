package domain.tarjeta;

import domain.heladera.Heladera.Heladera;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class EventoTarjetaColab {
    private String evento;
    private LocalDateTime fecha;
    private Heladera heladeraInvolucrada;

    public static EventoTarjetaColab generar(String nuevoEvento, Heladera unaHeladera){
        return EventoTarjetaColab
                .builder()
                .evento(nuevoEvento)
                .fecha(LocalDateTime.now())
                .heladeraInvolucrada(unaHeladera)
                .build();
    }
    public static EventoTarjetaColab generar(String nuevoEvento){
        return EventoTarjetaColab
                .builder()
                .evento(nuevoEvento)
                .fecha(LocalDateTime.now())
                .build();
    }

    public String mostrarEvento(){
        return " - Fecha evento: "
                +fecha
                +" / Descripcion: "
                +evento
                +((heladeraInvolucrada!=null)?"Heladera involucrada: "+heladeraInvolucrada:"")
                +"\n";
    }
}
