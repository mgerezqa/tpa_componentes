package main;

import domain.persona.Persona;
import domain.puntos.CategoriaOferta;
import domain.puntos.Oferta;
import domain.usuarios.ColaboradorJuridico;
import domain.usuarios.Rubro;
import domain.usuarios.TipoRazonSocial;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import repositorios.Repositorio;

import java.util.List;
import java.util.Optional;

public class OfertasTest implements WithSimplePersistenceUnit {
    private Repositorio repositorio;

    public static void main(String[] args) {
        OfertasTest instance = new OfertasTest();
        instance.repositorio = new Repositorio();

        instance.guardarOfertas();
        //instance.recuperarOfertas();
        //instance.recuperarOfertasPorId(2L);
    }

    private void guardarOfertas() {
        withTransaction(() -> {
            ColaboradorJuridico restaurant = new ColaboradorJuridico("Restaurant", TipoRazonSocial.EMPRESA, Rubro.SERVICIOS);
            Oferta ofertaAlmohada = new Oferta("Almohada Coinor", "Almohada Suave", CategoriaOferta.ARTICULOS_HOGAR, restaurant, 2000);
            Oferta ofertaAlmohada2 = new Oferta("Almohada Coinor", "Almohada Suave", CategoriaOferta.ARTICULOS_HOGAR, restaurant, 2000);
            repositorio.guardar(ofertaAlmohada);
            repositorio.guardar(ofertaAlmohada2);
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