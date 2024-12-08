package main_tests;

import domain.formulario.documentos.Documento;
import domain.formulario.documentos.TipoDocumento;
import domain.geografia.Calle;
import domain.geografia.Localidad;
import domain.geografia.Provincia;
import domain.geografia.Ubicacion;
import domain.heladera.Heladera.Heladera;
import domain.heladera.Heladera.ModeloDeHeladera;
import domain.persona.Persona;
import domain.persona.PersonaVulnerable;
import domain.tarjeta.RegistroDeUso;
import domain.tarjeta.Tarjeta;
import domain.tarjeta.TarjetaVulnerable;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import repositorios.Repositorio;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
            Provincia provincia = new Provincia("Misiones");
            Localidad localidad = new Localidad("Andresito");
            Ubicacion ubicacion = new Ubicacion(15f,20f,calle);
            Documento documento = new Documento(TipoDocumento.DNI,"12345679");
            List<Ubicacion> ubicaciones = new ArrayList<>();
            ModeloDeHeladera modelo = new ModeloDeHeladera("XSS-283");
            modelo.setTemperaturaMaxima(22f);
            modelo.setTemperaturaMinima(-12f);
            Heladera heladeraA = new Heladera(modelo, "medrano", ubicacion);

            ubicaciones.add(ubicacion);
            TarjetaVulnerable tarjeta1 = new TarjetaVulnerable();
            tarjeta1.setVulnerable(persona);

            Persona menor = new Persona("miguel","martinez",LocalDate.of(2023,05,27));

            RegistroDeUso registroDeUso = new RegistroDeUso(LocalDateTime.now(),heladeraA,tarjeta1);

            tarjeta1.usoDeTarjeta(registroDeUso);
            ubicacion.setProvincia(provincia);
            ubicacion.setLocalidad(localidad);
            persona.agregarMenorACargo(menor);
            persona.setDomicilios(ubicaciones);
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

