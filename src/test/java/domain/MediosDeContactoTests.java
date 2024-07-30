package domain;

import domain.contacto.Email;
import domain.contacto.MedioDeContacto;
import domain.contacto.Telegram;
import domain.contacto.Whatsapp;
import domain.usuarios.ColaboradorFisico;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class MediosDeContactoTests {

    private ColaboradorFisico lalo;
    private MedioDeContacto laloEmail;
    private MedioDeContacto laloTelefono;
    private MedioDeContacto laloWhatsapp;


    @BeforeEach
    public void setUp() {

        //Medios de contacto
        this.laloEmail = new Email("lalo@gmail.com");
        this.laloTelefono = new Telegram("+549116574460");
        this.laloWhatsapp = new Whatsapp("+549116574460");
        this.lalo = new ColaboradorFisico("Lalo", "Menz");

    }

    @Test
    @DisplayName("El correo no respeta el formato de un correo")
    public void testFormatoEmail() {
        assertThrows(Exception.class, () -> new Email("lalo.com"));
    }

    @Test
    @DisplayName("El correo no puede estar vacío")
    public void testEmailNoVacio() {
        assertThrows(Exception.class, () -> new Email(""));
    }

    @Test
    @DisplayName("El correo no puede ser solo números")
    public void testEmailSoloNumeros() {
        assertThrows(Exception.class, () -> new Email("2131"));
    }

    @Test
    @DisplayName("El número de teléfono no puede tener números negativos")
    public void testNumeroDeTelefonoValido() {
        assertThrows(Exception.class, () -> new Telegram("-51140009000"));
    }

    @Test
    @DisplayName("El número de teléfono no puede tener solo valores nulos")
    public void testNumeroDeTelefonoLongitud() {
        assertThrows(Exception.class, () -> new Telegram("0"));
    }

    @Test
    @DisplayName("El número de WhatsApp debe empezar con +")
    public void testWhatsappFormato() {
        assertThrows(Exception.class, () -> new Whatsapp("549116574460"));
    }

    @Test
    @DisplayName("El número de WhatsApp tiene una longitud incorrecta")
    public void testWhatsappLongitud() {
        assertThrows(Exception.class, () -> new Whatsapp("549116574460555"));
    }

}
