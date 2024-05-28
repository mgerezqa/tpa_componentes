package domain;

import domain.contacto.Email;
import domain.contacto.MedioDeContacto;
import domain.contacto.Telefono;
import domain.contacto.Whatsapp;
import domain.usuarios.ColaboradorFisico;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ColaboradorJuridicoTests {
    private ColaboradorFisico lalo;
    private MedioDeContacto laloEmail;
    private MedioDeContacto laloTelefono;
    private MedioDeContacto laloWhatsapp;


    @BeforeEach
    public void setUp() {
        //Medios de contacto
        this.laloEmail = new Email("lalo@gmail.com");
        this.laloTelefono = new Telefono(54,11,400090000);
        this.laloWhatsapp = new Whatsapp("+549116574460");
        this.lalo = new ColaboradorFisico("Lalo", "Menz",laloEmail);
    }


        @Test
        @DisplayName("Tiene que tener al menos 1 medio de contacto")
        public  void testCrearColaboradorSinMediosDeContacto(){
            assertThrows(Exception.class, () -> new ColaboradorFisico("Lalo", "Menz",null));
        }

        @Test
        @DisplayName("No puede tener medios de contacto repetidos")
        public  void testCrearColaboradorConMediosDeContactoRepetidos(){
            ColaboradorFisico lalo = new ColaboradorFisico("Lalo", "Menz",laloEmail);
            lalo.agregarMedioDeContacto(laloEmail);
            assertEquals(1,lalo.getMediosDeContacto().size());
        }

        @Test
        @DisplayName("Puede tener solo 2 medios de contacto ")
        public  void testCrearColaboradorConDosMediosDeContacto(){
            ColaboradorFisico lalo = new ColaboradorFisico("Lalo", "Menz",laloWhatsapp);
            lalo.agregarMedioDeContacto(laloEmail);
            assertEquals(2,lalo.getMediosDeContacto().size());
        }

        @Test
        @DisplayName("No puede tener mas de 3 medios de contacto ")
        public  void testCrearColaboradorConMasDeTresMediosDeContacto(){
            Whatsapp otroWhatsapp = new Whatsapp("+549114444666");
            Email otroEmail = new Email("otro@gmail.com");
            ColaboradorFisico lalo = new ColaboradorFisico("Lalo", "Menz",laloWhatsapp);
            lalo.agregarMedioDeContacto(laloEmail);
            lalo.agregarMedioDeContacto(otroWhatsapp);
            assertThrows(Exception.class, () -> lalo.agregarMedioDeContacto(otroEmail));
        }

    @Test
    @DisplayName("Tiene un nombre,apellido y al menos un medio de contacto ")
    public  void testCrearColaborador(){
        assertEquals("Lalo",lalo.getNombre());
        assertEquals("Menz",lalo.getApellido());
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

}
