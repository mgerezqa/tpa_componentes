package models;
import models.entities.formulario.Cuil;
import models.entities.formulario.Documento;
import models.entities.formulario.TipoDocumento;
import models.entities.contacto.MedioDeContacto;
import models.entities.contacto.Whatsapp;
import models.entities.geografia.AreaDeCobertura;
import models.entities.geografia.Ciudad;
import models.entities.usuarios.Tecnico;

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
        documento = new Documento(TipoDocumento.DNI,"44567823");
        cuil = new Cuil("20",documento.getNumeroDeDocumento(),"4");
        area = new AreaDeCobertura("Zona norte",Arrays.asList(ciudadA,ciudadB,ciudadC));

        ciudadA = new Ciudad("Palermo");
        ciudadB = new Ciudad("Belgrano");
        ciudadC = new Ciudad("Vicente lopez");

        tecnico = new Tecnico("Max", "Verstappen", documento, cuil);
    }

    @Test
    public void tecnicoDadoDeAlta(){
        Assertions.assertTrue(tecnico.estaActivo());
    }

    @Test
    public void modificarTecnico(){
        Whatsapp whatsapp = new Whatsapp("+541161964087");
        tecnico.agregarMedioDeContacto(whatsapp);
        Assertions.assertEquals(1,(tecnico.getMediosDeContacto().size()));

    }

    @Test
    public void darDeBajaUnTecnico(){
        tecnico.darDeBaja();
        Assertions.assertFalse(tecnico.estaActivo());
    }

}