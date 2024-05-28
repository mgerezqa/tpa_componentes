package test_gral;

import domain.carga_masiva.EmailSender;
import org.junit.jupiter.api.Test;

public class TestMailSender {
    @Test
    public void testEnviaMensaje(){
        EmailSender enviador = new EmailSender();
        String mensajeDefault = "Buenas, le informamos que se le dio de alta en nuestro nuevo sistema de colaboradores.\nSus credenciales son su numero de documento como usuario y contraseña.\nLe solicitamos que ingrese y complete sus datos.";
        enviador.enviarMensaje("email1@test.com", "asunto 1", "aca va el contenido");
        enviador.enviarMensaje("email2@test2.com", "asunto 2", mensajeDefault);
        enviador.enviarMensaje("email3@test3.com", "asunto 3", mensajeDefault);
        enviador.enviarMensaje("email4@test4.com", "asunto 4", mensajeDefault);
    }
}
