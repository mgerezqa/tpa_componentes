package main_tests;

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
        //instance.recuperarOfertaPorId(2L);
        //instance.actualizarOferta(2L);
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

    private void recuperarOfertas() {
        List<Object> ofertas = this.repositorio.buscarTodos(Oferta.class);
        System.out.println(ofertas);
    }
    private void recuperarOfertaPorId(Long id) {
        Optional<Object> ofertaGuardada = repositorio.buscarPorID(Oferta.class,id);
        if(ofertaGuardada.isPresent()){
            Oferta ofertaEncontrada = (Oferta)  ofertaGuardada.get();
            System.out.println(ofertaEncontrada);
        }else{
            System.out.println("No se encontro la persona con id:"+id);
        }
    }
    private void actualizarOferta(Long id){
        Optional<Object> ofertaGuardada = repositorio.buscarPorID(Oferta.class,id);
        if(ofertaGuardada.isPresent()){
            Oferta ofertaEncontrada = (Oferta)  ofertaGuardada.get();
            ofertaEncontrada.setCostoPuntos(3000);
            withTransaction(()->{
                repositorio.actualizar(ofertaEncontrada);
            });
            System.out.println(ofertaEncontrada);
        }else{
            System.out.println("No se encontro la persona con id:"+id);
        }
    }
}