package domain.mensajeria;

import domain.Config;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.io.IOException;


public class EmailSender {

    private static  String EMAIL_FROM ;   //change it
    private static  String APP_PASSWORD ;   //change it
    private static  String EMAIL_TO ; // correo del destinatario


    static {
        try {
            Config.init();
            EMAIL_FROM = Config.getProperty("EMAIL_FROM");   //change it
            APP_PASSWORD = Config.getProperty("APP_PASSWORD");   //change it
            EMAIL_TO = Config.getProperty("EMAIL_TO"); // correo del destinatario
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Fallo al cargar la configuración de email");
        }


    }

    public static void sendEmail(String asunto, String cuerpo,String Receptor) throws MessagingException {
        Message message = new MimeMessage(getEmailSession());
        message.setFrom(new InternetAddress(EMAIL_FROM));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(Receptor));
        message.setSubject(asunto);
        message.setText(cuerpo);
        Transport.send(message);
    }

    private static Session getEmailSession() {
        return Session.getInstance(Config.getGmailProperties(), new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_FROM, APP_PASSWORD);
            }
        });
    }


}