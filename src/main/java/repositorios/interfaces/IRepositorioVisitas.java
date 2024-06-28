package repositorios.interfaces;
import domain.visitas.Visita;

public interface IRepositorioVisitas {
    void agregarVisita(Visita visita);
    Visita buscarVisita(Visita visita);
}
