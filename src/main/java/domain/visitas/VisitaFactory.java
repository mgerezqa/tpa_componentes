package domain.visitas;
import domain.heladera.Heladera.Heladera;
import domain.incidentes.Incidente;
import domain.usuarios.Tecnico;
import dtos.VisitaTecnicaDTO;

import java.time.LocalDateTime;
import java.util.List;

public class VisitaFactory {

    public static Visita crearVisita(VisitaTecnicaDTO visitaTecnicaDTO, Tecnico tecnico, Incidente incidente){

        Visita visita = new Visita();

            visita.setTecnico(tecnico);
            visita.setHeladera(incidente.getHeladera());
            visita.setComentario(visitaTecnicaDTO.getComentario());
            visita.setFoto(visitaTecnicaDTO.getFoto());
            visita.setFechaVisita(visitaTecnicaDTO.getFechaYhora());
            visita.incidenteResuelto(visitaTecnicaDTO.isSolucionado());

        return visita;
    }
    public static Visita crearVisita(Tecnico tecnico, Heladera heladera, String comentario,String foto,Boolean resuelto){
        List<Incidente> incidentes = heladera.getIncidentes();
        if (incidentes.isEmpty()) {
            throw new RuntimeException("La heladera no tiene incidentes vinculados");
        }
        Incidente ultimoIncidente = incidentes.get(incidentes.size() - 1);

        Visita visita = Visita.builder()
                .tecnico(tecnico)
                .incidente(ultimoIncidente)
                .heladera(heladera)
                .comentario(comentario)
                .fechaVisita(LocalDateTime.now())
                .resuelto(resuelto)
                .foto(foto)
                .build();
        visita.incidenteResuelto(resuelto);
        return visita;
    }

}
