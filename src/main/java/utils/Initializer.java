package utils;


import config.ServiceLocator;
import domain.heladera.Heladera.EstadoHeladera;
import domain.heladera.Heladera.Heladera;
import domain.usuarios.ColaboradorFisico;
import repositorios.repositoriosBDD.RepositorioColaboradores;

import java.time.LocalDate;

public class Initializer {

    public static void init() {

        ColaboradorFisico colaboradorFisico1 = ColaboradorFisico
                .builder()
                .nombre("Malcom")
                .apellido("Braida")
                .build();
        colaboradorFisico1.sumarPuntos(200);


        ColaboradorFisico colaboradorFisico2 = ColaboradorFisico
                .builder()
                .nombre("Luis")
                .apellido("Suarez")
                .build();
        colaboradorFisico2.sumarPuntos(185);

        RepositorioColaboradores repositorioColaboradores = ServiceLocator.instanceOf(RepositorioColaboradores.class);
        repositorioColaboradores.guardar(colaboradorFisico1);
        repositorioColaboradores.guardar(colaboradorFisico2);

        System.out.println("Initializer ejecutado correctamente :)");
    }


}