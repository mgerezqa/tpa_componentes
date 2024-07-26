package domain.visitas;
import domain.incidentes.Incidente;
import domain.usuarios.Tecnico;
import dtos.VisitaTecnicaDTO;
public class VisitaFactory {

    public static Visita crearVisita(VisitaTecnicaDTO visitaTecnicaDTO, Tecnico tecnico, Incidente incidente){

        Visita visita = new Visita();

            visita.setTecnico(tecnico);
            visita.setHeladera(incidente.getHeladera());
            visita.setComentario(visitaTecnicaDTO.getComentario());
            visita.setFoto(visita.getFoto());
            visita.setFechaVisita(visitaTecnicaDTO.getFechaYhora());
            visita.incidenteResuelto(visitaTecnicaDTO.isSolucionado());

        return visita;
    }

}
