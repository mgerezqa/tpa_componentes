package Suscripciones;

import models.entities.contacto.Email;
import models.entities.contacto.MedioDeContacto;
import models.entities.contacto.Telefono;
import models.entities.contacto.Whatsapp;
import models.entities.formulario.Campo;
import models.entities.formulario.Formulario;
import models.entities.formulario.TipoCampo;
import models.entities.geografia.AreaDeCobertura;
import models.entities.geografia.Ciudad;
import models.entities.geografia.Ubicacion;
import models.entities.usuarios.ColaboradorFisico;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Set;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut;
import static org.junit.jupiter.api.Assertions.*;

public class ColaboradorFisicoTests {
    private ColaboradorFisico lalo;
    private MedioDeContacto laloEmail;
    private MedioDeContacto laloTelefono;
    private MedioDeContacto laloWhatsapp;
    private Formulario formulario;
    private AreaDeCobertura zonaNorte;
    private AreaDeCobertura zonaSur;


    @BeforeEach
    public void setUp() {
        //Medios de contacto
        this.laloEmail = new Email("lalo@gmail.com");
        this.laloTelefono = new Telefono(54,11,400090000);
        this.laloWhatsapp = new Whatsapp("+549116574460");
        this.lalo = new ColaboradorFisico("Lalo", "Menz",laloEmail);
        this.formulario = new Formulario();
        this.zonaNorte = new AreaDeCobertura("Zona norte",new ArrayList<>());
        this.zonaSur = new AreaDeCobertura("Zona sur",new ArrayList<>());
    }

    @Test
    @DisplayName("Un colaborador puede agregar zonas que frecuenta")
    public void testAgregarZonaQueFrecuenta(){
        lalo.agregarZonaDeCobertura(zonaNorte);
        lalo.agregarCiudadEnZonaDeCobertura(zonaNorte,new Ciudad("Palermo"));
        lalo.agregarCiudadEnZonaDeCobertura(zonaNorte,new Ciudad("Belgrano"));
        assertEquals(2,lalo.listaDeCiudadesPorZonaDeCobertura(zonaNorte).size());
    }

    @Test
    @DisplayName("Un colaborador no se puede suscribir a zonas que no frecuenta")
    public void lanzarExcepciónALsuscribirseAZonaNoFrecuentada(){
        lalo.agregarCiudadEnZonaDeCobertura(zonaNorte,new Ciudad("Tigre"));
        assertThrows(Exception.class, () -> lalo.suscribirse(zonaSur));
    }
//
//        @Test
//        @DisplayName("Tiene que tener al menos 1 medio de contacto")
//        public  void testCrearColaboradorSinMediosDeContacto(){
//            assertThrows(Exception.class, () -> new ColaboradorFisico("Lalo", "Menz",null));
//        }
//
//        @Test
//        @DisplayName("No puede tener medios de contacto repetidos")
//        public  void testCrearColaboradorConMediosDeContactoRepetidos(){
//            ColaboradorFisico lalo = new ColaboradorFisico("Lalo", "Menz",laloEmail);
//            lalo.agregarMedioDeContacto(laloEmail);
//            assertEquals(1,lalo.getMediosDeContacto().size());
//        }
//
//        @Test
//        @DisplayName("Puede tener solo 2 medios de contacto ")
//        public  void testCrearColaboradorConDosMediosDeContacto(){
//            ColaboradorFisico lalo = new ColaboradorFisico("Lalo", "Menz",laloWhatsapp);
//            lalo.agregarMedioDeContacto(laloEmail);
//            assertEquals(2,lalo.getMediosDeContacto().size());
//        }
//
//        @Test
//        @DisplayName("No puede tener mas de 3 medios de contacto ")
//        public  void testCrearColaboradorConMasDeTresMediosDeContacto(){
//            Whatsapp otroWhatsapp = new Whatsapp("+549114444666");
//            Email otroEmail = new Email("otro@gmail.com");
//            ColaboradorFisico lalo = new ColaboradorFisico("Lalo", "Menz",laloWhatsapp);
//            lalo.agregarMedioDeContacto(laloEmail);
//            lalo.agregarMedioDeContacto(otroWhatsapp);
//            assertThrows(Exception.class, () -> lalo.agregarMedioDeContacto(otroEmail));
//        }
//
//    @Test
//    @DisplayName("Tiene un nombre,apellido y al menos un medio de contacto ")
//    public  void testCrearColaborador(){
//        assertEquals("Lalo",lalo.getNombre());
//        assertEquals("Menz",lalo.getApellido());
//        assertEquals(1,lalo.getMediosDeContacto().size());
//
//    }
//
//    @Test
//    @DisplayName("Puede ser dado de baja ")
//    public  void testBajaColaborador(){
//        lalo.darDeBaja();
//        assertEquals(false,lalo.getActivo());
//    }
//
//    @Test
//    @DisplayName("Puede ser dado de alta ")
//    public  void testAltaColaborador(){
//        assertEquals(true,lalo.getActivo());
//    }
//
//    @Test
//    @DisplayName("Puede ser dado de baja y luego de alta ")
//    public  void testBajaYAltaColaborador(){
//        lalo.darDeBaja();
//        assertEquals(false,lalo.getActivo());
//        lalo.darDeAlta();
//        assertEquals(true,lalo.getActivo());
//    }
//
//    @Test
//    @DisplayName("Al dar de alta un colaborador se autocompleta su formulario")
//    public void testAutocompletarFormularioAlDarDeAlta() throws Exception {
//
//        // Captura la salida del System.out
//        String output = tapSystemOut(() -> {lalo.leerFormulario();});
//        assertTrue(output.contains("Estos son los campos cargados:"));
//        assertTrue(output.contains("Lalo"));
//        assertTrue(output.contains("Menz"));
//        lalo.leerFormulario();
//
//    }
//    @Test
//    @DisplayName("Al dar de alta un colaborador y se modifica alguna respuesta también lo hace en el formulario")
//    public void testAutocompletarFormularioAlDarDeAltaYModificarRespuesta() throws Exception {
//        lalo.darDeAlta();
//        lalo.leerFormulario();
//        System.out.println("Modificando respuesta...");
//        lalo.modificarRespuesta("Nombre", "Marito");
//        lalo.modificarRespuesta("Apellido", "Goretz");
//        lalo.modificarRespuesta("Medio de contacto", "Marito");
//        String output = tapSystemOut(() -> {lalo.leerFormulario();});
//        lalo.leerFormulario();
//        assertTrue(output.contains("Marito"));
//        assertTrue(output.contains("Goretz"));
//
//    }
//
//    @Test
//    @DisplayName("Al dar de alta un colaborador y se agrega alguna respuesta también lo hace en el formulario")
//    public void testDarDeAltaYAgregarAlgunaPreguntaAlFormulario() throws Exception {
//        Formulario formularioPerzonalizado = new Formulario();
//        formularioPerzonalizado.agregarCampo("Idiomas", new Campo(TipoCampo.CAMPO_MULTIPLE));
//        lalo.completarFormulario(formularioPerzonalizado);
//        lalo.agregarRespuesta("Idiomas","ingles");
//        lalo.agregarRespuesta("Idiomas","alemán");
//        lalo.agregarRespuesta("Idiomas","español");
//        String output = tapSystemOut(() -> {lalo.leerFormulario();});
//        lalo.leerFormulario();
//    }
//
//    @Test
//    @DisplayName("Puede modificarse su informacion")
//    public void modificarColaborador(){
//        lalo.setNombre("Lalo Modificado");
//        lalo.setApellido("Menz Modificado");
//        assertEquals("Lalo Modificado",lalo.getNombre());
//        assertEquals("Menz Modificado",lalo.getApellido());
//    }
//


}
