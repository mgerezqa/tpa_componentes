package utils.generadores;

import domain.contribucionNuevo.*;
import domain.usuariosNuevo.Colaborador;
import utils.importadorCsv.RegistroCSV;

public interface GeneradorContribucion {
    static Contribucion generar(TipoContribucion unTipo){
        switch (unTipo){
            case DINERO -> {return new ContribucionDinero();}
            case DISTRIBUCION -> {return new ContribucionDistribucion();}
            case MANTENIMIENTO -> {return new ContribucionMantenimientoHeladera();}
            case REGISTRO_P_V -> {return new ContribucionRegistroPersona();}
            case VIANDA -> {return new ContribucionVianda();}
            default -> throw new RuntimeException("Tipo de contribucion Invalida!");
        }
    }

    static Contribucion generar(RegistroCSV lecturaRegistro, Colaborador unColab){
        Contribucion aux = generar(lecturaRegistro.getTipoColab());
        aux.registrarFechaDeContribucion(lecturaRegistro.getFechaColab());
        aux.registrarColaborador(unColab);
        return aux;
    }
}
