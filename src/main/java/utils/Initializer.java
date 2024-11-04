package utils;


import config.ServiceLocator;

import domain.usuarios.ColaboradorFisico;
import domain.usuarios.Usuario;
import repositorios.repositoriosBDD.RepositorioColaboradores;

public class Initializer {

    public static void init() {
        Usuario usuario1 = new Usuario("Malcom", "1234");
        ColaboradorFisico colaboradorFisico1 = ColaboradorFisico
                .builder()
                .nombre("Malcom")
                .apellido("Braida")
                .build();
        colaboradorFisico1.setUsuario(usuario1);
        colaboradorFisico1.sumarPuntos(200);

        Usuario usuario2 = new Usuario("luis", "1234");

        ColaboradorFisico colaboradorFisico2 = ColaboradorFisico
                .builder()
                .nombre("Luis")
                .apellido("Suarez")
                .build();
        colaboradorFisico2.setUsuario(usuario2);
        colaboradorFisico2.sumarPuntos(185);

        RepositorioColaboradores repositorioColaboradores = ServiceLocator.instanceOf(RepositorioColaboradores.class);
        repositorioColaboradores.guardar(colaboradorFisico1);
        repositorioColaboradores.guardar(colaboradorFisico2);

        System.out.println("Initializer ejecutado correctamente :)");
    }


}