package repositorios.interfaces;

import domain.heladera.Heladera.Heladera;
import domain.incidentes.Incidente;

import java.util.List;
import java.util.Optional;

public interface IRepositorioHeladeras {
    Optional<Heladera> obtenerHeladeraPorID(Integer id);
    void darDeAlta(Heladera heladera);
    void darDeBaja(Heladera heladera);
    List<Heladera> obtenerTodasLasHeladeras();
    Heladera obtenerHeladeraPorNombre(String nombreIdentificador);
}
