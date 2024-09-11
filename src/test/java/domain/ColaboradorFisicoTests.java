package domain;

import domain.contacto.Email;
import domain.contacto.MedioDeContacto;
import domain.contacto.Telegram;
import domain.contacto.Whatsapp;
import domain.formulario.Campo;
import domain.formulario.Formulario;
import domain.formulario.eTipoCampo;
import domain.usuarios.ColaboradorFisico;
import org.junit.jupiter.api.*;
import utils.generadorDeUsuarioDesdeForm.FormularioColaboradorFisico;
import utils.generadorDeUsuarioDesdeForm.FormularioUtils;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut;
import static org.junit.jupiter.api.Assertions.*;

public class ColaboradorFisicoTests {
    private ColaboradorFisico lalo;
    private MedioDeContacto laloEmail;
    private MedioDeContacto laloTelefono;
    private MedioDeContacto laloWhatsapp;
    private Formulario formulario;


    @BeforeEach
    public void setUp() {
        //Medios de contacto
        this.laloEmail = new Email("NOT@gmail.com");
        this.laloTelefono = new Telegram("NOT");
        this.laloWhatsapp = new Whatsapp("+5491161964086");
        this.lalo = new ColaboradorFisico("Lalo", "Menz");
        this.formulario = new FormularioColaboradorFisico("Lalo", "Menz");

    }


        @Test
        @DisplayName("Tiene que tener al menos 1 medio de contacto")
        public  void testCrearColaboradorSinMediosDeContacto(){

            assertThrows(Exception.class, () -> FormularioUtils.crearColaboradorFisico(formulario));

        }


        @Test
        @DisplayName("Puede tener solo 2 medios de contacto ")
        public  void testCrearColaboradorConDosMediosDeContacto(){
            ColaboradorFisico lalo = new ColaboradorFisico("Lalo", "Menz");
            lalo.agregarMedioDeContacto(laloEmail);
            lalo.agregarMedioDeContacto(laloTelefono);

            assertEquals(2,lalo.getMediosDeContacto().size());
        }


    @Test
    @DisplayName("Tiene un nombre,apellido y al menos un medio de contacto ")
    public  void testCrearColaborador(){
        assertEquals("Lalo",lalo.getNombre());
        assertEquals("Menz",lalo.getApellido());
        lalo.agregarMedioDeContacto(laloTelefono);
        assertEquals(1,lalo.getMediosDeContacto().size());

    }

    @Test
    @DisplayName("Puede ser dado de baja ")
    public  void testBajaColaborador(){
        lalo.darDeBaja();
        assertEquals(false,lalo.getActivo());
    }

    @Test
    @DisplayName("Puede ser dado de alta ")
    public  void testAltaColaborador(){
        assertEquals(true,lalo.getActivo());
    }

    @Test
    @DisplayName("Puede ser dado de baja y luego de alta ")
    public  void testBajaYAltaColaborador(){
        lalo.darDeBaja();
        assertEquals(false,lalo.getActivo());
        lalo.darDeAlta();
        assertEquals(true,lalo.getActivo());
    }


    @Test
    @DisplayName("Puede modificarse su informacion")
    public void modificarColaborador(){
        lalo.setNombre("Lalo Modificado");
        lalo.setApellido("Menz Modificado");
        assertEquals("Lalo Modificado",lalo.getNombre());
        assertEquals("Menz Modificado",lalo.getApellido());
    }

}
