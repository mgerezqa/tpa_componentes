package domain.mensajeria;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class WhatsAppMessageSender{
    // Find your Account Sid and Token at twilio.com/console
    //comento para pusheo.
    public static final String ACCOUNT_SID = "AC122d945b1c0c4a9a9d471447a3520322";
    public static final String AUTH_TOKEN = "adbf30fefeff07f70588a5e32e88d74d";

    public static void main(String[] args) {
        
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new PhoneNumber("whatsapp:+5491165974084"), // Nro asociado a sandbox
                new PhoneNumber("whatsapp:+14155238886"), // Twilio phone number
                "Mensaje de prueba desde Java")
                        .create();
        System.out.println(message.getSid());

        message = Message.creator(
                new PhoneNumber("whatsapp:+5491144063523"),
                message.getFrom(),
                message.getBody()).create();

    System.out.println("Mensaje enviado exitosamente"+message.getSid());
    }


}