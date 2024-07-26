package repositorios.interfaces;

import domain.heladera.Heladera.Heladera;
import domain.incidentes.Incidente;

import java.util.List;

public interface IRepositorioHeladeras {
    void darDeAlta(Heladera heladera);
    void darDeBaja(Heladera heladera);
    List<Heladera> obtenerTodasLasHeladeras();
    Heladera obtenerHeladeraPorNombre(String nombreIdentificador);
}
