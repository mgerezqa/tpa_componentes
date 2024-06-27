package domain.incidentes;

import domain.heladera.Heladera.Heladera;
import domain.incidentes.Incidente;
import domain.tecnicos.Tecnico;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Visita {
    private Tecnico tecnico;
    private Heladera heladera;
    private LocalDateTime fechaVisita;
    private String comentario;
    private String foto;
    private boolean incidenteResuelto;

    public Visita(Tecnico tecnico, Heladera heladera, String comentario, String foto, boolean incidenteResuelto) {
        this.tecnico = tecnico;
        this.heladera = heladera;
        this.fechaVisita = LocalDateTime.now();
        this.comentario = comentario;
        this.foto = foto;
        this.incidenteResuelto = incidenteResuelto;
    }
    
}
