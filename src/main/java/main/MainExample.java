package main;

import domain.persona.Persona;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import repositorios.Repositorio;

import java.util.List;
import java.util.Optional;

public class MainExample implements WithSimplePersistenceUnit {
    private Repositorio repositorio;

    public static void main(String[] args) {
        MainExample instance = new MainExample();
        instance.repositorio = new Repositorio();

        //instance.guardarPersonas();
        instance.recuperarPersonas();
        instance.recuperarPersonaPorId(2L);
    }

    private void guardarPersonas() {
        withTransaction(() -> {
            Persona persona1 = new Persona("nahuel",15);
            Persona persona2 = new Persona("miguel",15);
            repositorio.guardar(persona1);
            repositorio.guardar(persona2);
        });
    }

    private void recuperarPersonas() {
        List<Object> personas = this.repositorio.buscarTodos(Persona.class);
        System.out.println(personas);
    }
    private void recuperarPersonaPorId(Long id) {
        Optional<Object> personaGuardada = repositorio.buscarPorID(Persona.class,id);
        if(personaGuardada.isPresent()){
            Persona personaEncontrada = (Persona)  personaGuardada.get();
            System.out.println(personaEncontrada.getNombre());
            System.out.println(personaEncontrada.getEdad());
        }else{
            System.out.println("No se encontro la persona con id:"+id);
        }
    }

}
