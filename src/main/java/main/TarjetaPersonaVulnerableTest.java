package main;

import domain.formulario.documentos.Documento;
import domain.formulario.documentos.TipoDocumento;
import domain.geografia.Calle;
import domain.geografia.Ubicacion;
import domain.persona.Persona;
import domain.persona.PersonaVulnerable;
import domain.tarjeta.RegistroDeUso;
import domain.tarjeta.Tarjeta;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import repositorios.Repositorio;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class TarjetaPersonaVulnerableTest implements WithSimplePersistenceUnit {
    private Repositorio repositorio;

    public static void main(String[] args) {
        TarjetaPersonaVulnerableTest instance = new TarjetaPersonaVulnerableTest();
        instance.repositorio = new Repositorio();

        instance.guardarTarjetas();

    }

    private void guardarTarjetas() {
        withTransaction(() -> {
            LocalDate fechaNacimiento = LocalDate.of(1999,05,27);
            PersonaVulnerable persona = new PersonaVulnerable("daniel",fechaNacimiento);
            Calle calle = new Calle("Calle1","7623");
            Ubicacion ubicacion = new Ubicacion(15f,20f,calle);
            Documento documento = new Documento(TipoDocumento.DNI,"12345679");
            Tarjeta tarjeta1 = new Tarjeta(persona);
            Persona menor = new Persona("miguel",4);
            RegistroDeUso registroDeUso = new RegistroDeUso();
            tarjeta1.usoDeTarjeta(registroDeUso);
            persona.agregarMenorACargo(menor);
            persona.setDomicilio(ubicacion);
            persona.setDocumento(documento);

            repositorio.guardar(tarjeta1);
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

