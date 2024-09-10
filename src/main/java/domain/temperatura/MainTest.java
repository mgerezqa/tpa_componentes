package domain.temperatura;

import domain.heladera.Heladera.Heladera;
import repositorios.interfaces.IRepositorioHeladeras;
import repositorios.interfaces.IRepositorioIncidentes;
import repositorios.reposEnMemoria.RepositorioHeladeras;
import repositorios.reposEnMemoria.RepositorioIncidentes;

import java.util.List;

//public class MainTest {
//
//    public static void main(String[] args) {
//        IRepositorioIncidentes repositorioIncidentes = new RepositorioIncidentes();
//        List<Heladera> heladeras = new RepositorioHeladeras().obtenerTodasLasHeladeras();
//        VerificadorTemperaturas verificadorTemperaturas = new VerificadorTemperaturas(heladeras,repositorioIncidentes);
//        verificadorTemperaturas.verificarTemperaturas();
//    }
//}
