package domain.mensajeria;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import domain.Config;

import java.util.Properties;

public class EmailSender {

    private static EmailSender instance;
    private final String EMAIL_FROM;
    private final String APP_PASSWORD;
    private final String EMAIL_TO;

    private EmailSender() {
        EMAIL_FROM = Config.getInstance().getProperty("EMAIL_FROM");
        APP_PASSWORD = Config.getInstance().getProperty("APP_PASSWORD");
        EMAIL_TO = Config.getInstance().getProperty("EMAIL_TO");
    }

    public static EmailSender getInstance() {
        if (instance == null) {
            synchronized (EmailSender.class) {
                if (instance == null) {
                    instance = new EmailSender();
                }
            }
        }
        return instance;
    }

    public void sendEmail(String asunto, String cuerpo, String receptor) throws MessagingException {
        Message message = new MimeMessage(getEmailSession());
        message.setFrom(new InternetAddress(EMAIL_FROM));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receptor));
        message.setSubject(asunto);
        message.setText(cuerpo);
        Transport.send(message);
    }

    public void sendHtmlEmail(String asunto, String cuerpoHtml, String receptor) throws MessagingException {
        Message message = new MimeMessage(getEmailSession());
        message.setFrom(new InternetAddress(EMAIL_FROM));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receptor));
        message.setSubject(asunto);
        message.setContent(cuerpoHtml, "text/html; charset=utf-8");
        Transport.send(message);
    }

    private Session getEmailSession() {
        return Session.getInstance(getGmailProperties(), new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_FROM, APP_PASSWORD);
            }
        });
    }

    private Properties getGmailProperties() {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        return prop;
    }
}
