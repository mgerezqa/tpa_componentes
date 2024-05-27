package test_gral;

import domain.carga_masiva.EmailSender;
import org.junit.jupiter.api.Test;

public class TestMailSender {
    @Test
    public void testEnviaMensaje(){
        EmailSender enviador = new EmailSender();
        enviador.enviarMensaje("email1@test.com", "asunto 1", "aca va el contenido");
        enviador.enviarMensaje("email2@test2.com", "asunto 2", "probando si produce lag");
        enviador.enviarMensaje("email3@test3.com", "asunto 3", "probando si produce lag");
        enviador.enviarMensaje("email4@test4.com", "asunto 4", "parece que no");
    }
}
