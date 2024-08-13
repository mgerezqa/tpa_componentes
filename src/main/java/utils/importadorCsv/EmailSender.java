package utils.importadorCsv;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.*;

/* requiere dependencia:
// PARA USAR ESTO DEBEN TENER ALGUN TIPO DE PUERTO ESCUCHA, BAJENSE "SMTPDummy" QUE FUNCIONA CON 1 CLICK
<dependency>
    <groupId>com.sun.mail</groupId>
    <artifactId>jakarta.mail</artifactId>
    <version>2.0.1</version>
</dependency>
 */
public class EmailSender {
    String senderEmail = "Nosotros@ONG.com";
    Properties properties;
    Session session;
    public EmailSender(){
        this.properties = System.getProperties();
        this.properties.setProperty("mail.smtp.host", "localhost");
        this.session = Session.getDefaultInstance(properties);
    }
    public void enviarMensaje(String destino, String asunto, String contenido){
        try {
            // Construct a default MimeMessage object
            MimeMessage emailMessage = new MimeMessage(session);

            // Assign 'From' header field of the header
            emailMessage.setFrom(new InternetAddress(senderEmail));

            // Assign 'To' header field of the header
            emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(destino));

            // Assign 'Subject' header field
            emailMessage.setSubject(asunto);

            // Assign the actual message
            emailMessage.setText(contenido);

            // Dispatch message
            Transport.send(emailMessage);
            System.out.println("Mensaje enviado!");
        } catch (MessagingException exception) {
            exception.printStackTrace();
        }
    }

    /* codigo web sin modificaciones:
    public static void main(String[] args) {
        // Defining recipient's email ID
        String recipientEmail = "pabloexample.com";

        // Defining sender's email ID
        String senderEmail = "Nosotros@ONG.com";

        // Assuming you are sending email from localhost
        String serverHost = "localhost";

        // Fetch system properties
        Properties properties = System.getProperties();

        // Set up mail server
        properties.setProperty("mail.smtp.host", serverHost);

        // Fetch the default Session object
        Session session = Session.getDefaultInstance(properties);

        try {
            // Construct a default MimeMessage object
            MimeMessage emailMessage = new MimeMessage(session);

            // Assign 'From' header field of the header
            emailMessage.setFrom(new InternetAddress(senderEmail));

            // Assign 'To' header field of the header
            emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));

            // Assign 'Subject' header field
            emailMessage.setSubject("Subject Line Here!");

            // Assign the actual message
            emailMessage.setText("Actual message goes here");

            // Dispatch message
            Transport.send(emailMessage);
            System.out.println("Message dispatched successfully...");
        } catch (MessagingException exception) {
            exception.printStackTrace();
        }
    }
     */

}