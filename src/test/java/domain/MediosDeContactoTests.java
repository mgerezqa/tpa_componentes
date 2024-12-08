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
    private MedioDeContacto laloTelegram;
    private MedioDeContacto laloWhatsapp;


    @BeforeEach
    public void setUp() {

        //Medios de contacto
        this.laloEmail = new Email("NOT@gmail.com");
        this.laloTelegram = new Telegram("melli11_ok");
        this.laloWhatsapp = new Whatsapp("+5491161964086");
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
    @DisplayName("El usuario de Telegram no puede estar vacio")
    public void testUserNameTelegram() {
        assertThrows(Exception.class, () -> new Telegram(""));
    }

    @Test
    @DisplayName("El número de WhatsApp no puede tener números negativos")
    public void testNumeroDeTelefonoValido() {
        assertThrows(Exception.class, () -> new Whatsapp("-51140009000"));
    }

    @Test
    @DisplayName("El número de WhatsApp no puede tener solo valores nulos")
    public void testNumeroDeTelefonoLongitud() {
        assertThrows(Exception.class, () -> new Whatsapp("0"));
    }

    @Test
    @DisplayName("El número de WhatsApp debe empezar con +")
    public void testWhatsappFormato() {
        assertThrows(Exception.class, () -> new Whatsapp("5491161964086"));
    }

    @Test
    @DisplayName("El número de WhatsApp tiene una longitud incorrecta")
    public void testWhatsappLongitud() {
        assertThrows(Exception.class, () -> new Whatsapp("5491161964086"));
    }

}
