package domain;

import domain.contacto.MedioDeContacto;
import domain.contacto.Whatsapp;
import domain.formulario.documentos.Cuil;
import domain.formulario.documentos.Documento;
import domain.formulario.documentos.TipoDocumento;
import domain.geografia.Ubicacion;
import domain.geografia.area.AreaDeCobertura;
import domain.geografia.area.TamanioArea;
import domain.usuarios.Tecnico;

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
    private Ubicacion ubicacion;

    @BeforeEach
    public void setUp(){
        documento = new Documento(TipoDocumento.DNI,"44567823");
        cuil = new Cuil("20",documento.getNumeroDeDocumento(),"4");
        area = new AreaDeCobertura(ubicacion, TamanioArea.MEDIANA);

        tecnico = new Tecnico("Max", "Verstappen", documento, cuil);
    }

    @Test
    public void tecnicoDadoDeAlta(){
        Assertions.assertTrue(tecnico.estaActivo());
    }

    @Test
    public void modificarTecnico(){
        Whatsapp whatsapp = new Whatsapp("+5491161964086");
        tecnico.agregarMedioDeContacto(whatsapp);
        Assertions.assertEquals(1,(tecnico.getMediosDeContacto().size()));

    }

    @Test
    public void darDeBajaUnTecnico(){
        tecnico.setActivo(false);
        Assertions.assertFalse(tecnico.estaActivo());
    }

}
