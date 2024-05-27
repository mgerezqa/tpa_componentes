package domain.puntos;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class CatalogoPuntos {

    private static CatalogoPuntos instancia;

    @Getter public List<Oferta> catalogo;

    //Singleton
    public CatalogoPuntos() {
        this.catalogo = new ArrayList<>();
    }

    public static CatalogoPuntos obtenerInstancia() {
        if (instancia == null) {
            instancia = new CatalogoPuntos();
        }
        return instancia;
    }


    public void agregarOferta(Oferta oferta) {
        catalogo.add(oferta);
    }

    public void sacarOferta(Oferta oferta) {
        catalogo.remove(oferta);
    }

    public void canjearPuntos(int puntos, Oferta oferta){
        if (oferta.getCostoPuntos()<=puntos) {
            sacarOferta(oferta);
            // Se realizó el canje
        }else {
            throw new FalloCanjePuntos("Puntos insuficientes.");
        }
    }
}
