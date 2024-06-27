package controladores;
import domain.geografia.calculadorDistancia.ICalculadorDistanciaKM;
import domain.heladera.Heladera.Heladera;
import domain.incidentes.Incidente;
import domain.tecnicos.Tecnico;
import repositorios.IRepositorioTecnicos;

import java.util.List;

public class ControladorAvisoATecnicos {

    private IRepositorioTecnicos iRepositorioTecnicos;
    private ICalculadorDistanciaKM iCalculadorDistanciaKM;

    public ControladorAvisoATecnicos(IRepositorioTecnicos repositorioTecnicos, ICalculadorDistanciaKM calculadorDistanciaKM) {
        this.iRepositorioTecnicos = repositorioTecnicos;
        this.iCalculadorDistanciaKM = calculadorDistanciaKM;
    }

    public Tecnico buscarTecnicoMasCercanoA(Heladera heladera){
        List<Tecnico> tecnicos = iRepositorioTecnicos.obtenerTodosLosTecnicos();
        Tecnico tecnicoMasCercano = null;
        double distanciaMinima = Double.MAX_VALUE;

        for (Tecnico tecnico : tecnicos) {
            if (tecnico.getActivo() && tecnico.getArea().estaEnElArea(heladera.getUbicacion())) {
                double distancia = iCalculadorDistanciaKM.calcularDistanciaKM(tecnico.getArea().getUbicacionPrincipal(), heladera.getUbicacion());
                if (distancia < distanciaMinima) {
                    distanciaMinima = distancia;
                    tecnicoMasCercano = tecnico;
                }
            }
        }

        return tecnicoMasCercano;
    }

    public void generarAvisoATecnico(Incidente incidente){
        Tecnico tecnicoMasCercano = buscarTecnicoMasCercanoA(incidente.getHeladera());

        // NOTIFICAR TECNICO ! ! !

    }

}
