package domain.mensajeria;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashSet;
import java.util.Set;

public class TelegramBot extends TelegramLongPollingBot {

    private final Set<Long> knownChatIds = new HashSet<>();

    @Override
    public String getBotUsername() {
        return "grupo17_viandasdisponibles_bot";
    }

    @Override
    public String getBotToken() {
        return "7241398935:AAFDRcuMtt9CjlVeujpxO9woHfawy5SbHTM";
    }

    //we handle the received update and capture the text and id of the conversation
    @Override
    public void onUpdateReceived(Update update) {
        String message = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();
        System.out.println("Message Received: " + message);
        int length = message.length();
        System.out.println("The Message have " + length + " characters");
        sendMessage(generateSendMessage(chatId, length));

        // Check if this is the first interaction with the user
        if (!knownChatIds.contains(chatId)) {
            knownChatIds.add(chatId);
            sendWelcomeMessage(chatId);
        }
    }

    //we create a SendMessage with the text we want to send to the chat
    private SendMessage generateSendMessage(Long chatId, int characterCount) {
        return new SendMessage(chatId.toString(), "The Message have " + characterCount + " characters");
    }

    private void sendWelcomeMessage(Long chatId) {
        String welcomeText = "¡Bienvenido! Soy tu bot de ayuda. Aquí tienes algunas instrucciones para empezar...";
        sendMessage(new SendMessage(chatId.toString(), welcomeText));
    }

    //send the message to Telegram API
    private void sendMessage(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
