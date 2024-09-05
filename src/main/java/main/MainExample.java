package main;

import domain.persona.Persona;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import repositorios.Repositorio;

import java.util.List;

public class MainExample implements WithSimplePersistenceUnit {
    private Repositorio repositorio;

    public static void main(String[] args) {
        MainExample instance = new MainExample();
        instance.repositorio = new Repositorio();

        instance.guardarServicios();
        //instance.recuperarServicios();
        //instance.agregarTareasAServicios();

        instance.recuperarServicios2();
        //instance.actualizarNombreDeServicios();
    }

    private void guardarServicios() {
        /*Servicio servicio1 = new Servicio();
        servicio1.setNombre("Abogacia");

        Servicio servicio2 = new Servicio();
        servicio2.setNombre("Ingenieria en Sistemas");

        beginTransaction();
        repositorioDeServicios.guardar(servicio1);
        repositorioDeServicios.guardar(servicio2);
        commitTransaction();*/

        withTransaction(() -> {
            Persona persona1 = new Persona("nahuel",15);
            Persona persona2 = new Persona("miguel",15);
            repositorio.guardar(persona1);
            repositorio.guardar(persona2);
        });
    }

    private void recuperarServicios2() {
        List<Persona> personas = this.repositorio.buscarTodos();
        System.out.println(personas);
    }

}
