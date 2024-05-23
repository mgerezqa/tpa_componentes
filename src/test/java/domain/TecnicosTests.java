package domain;
import domain.contacto.Documentos.Cuil;
import domain.contacto.Documentos.Dni;
import domain.contacto.Documentos.Documento;
import domain.contacto.MedioDeContacto;
import domain.contacto.Telefono;
import domain.contacto.Whatsapp;
import domain.geografia.AreaDeCobertura;
import domain.geografia.Ciudad;
import domain.usuarios.Tecnico;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TecnicosTests {

    private Tecnico tecnico;
    private Documento documento;
    private Cuil cuil;
    private List<MedioDeContacto> mediosDeContacto;
    private AreaDeCobertura area;
    private Ciudad ciudadA;
    private Ciudad ciudadB;
    private Ciudad ciudadC;

    @BeforeEach
    public void setUp(){
        documento = new Dni("44531865");
        cuil = new Cuil("20-44531865-8");
        mediosDeContacto = new ArrayList<>(Arrays.asList(new Telefono(54, 11, 43560072)));
        area = new AreaDeCobertura(Arrays.asList(ciudadA,ciudadB,ciudadC));

        ciudadA = new Ciudad("Palermo");
        ciudadB = new Ciudad("El palomar");
        ciudadC = new Ciudad("Lujan");

        tecnico = new Tecnico("Max", "Verstappen", documento, cuil, mediosDeContacto, area);
    }

    @Test
    public void tecnicoDadoDeAlta(){
        Assertions.assertTrue(tecnico.estaActivo());
    }

    @Test
    public void modificarTecnico(){
        Whatsapp whatsapp = new Whatsapp("+541161964087");
        tecnico.agregarMedioDeContacto(whatsapp);
        Assertions.assertEquals(2,(tecnico.getMediosDeContacto().size()));

    }

    @Test
    public void darDeBajaUnTecnico(){
        tecnico.darDeBaja();
        Assertions.assertFalse(tecnico.estaActivo());
    }

}
