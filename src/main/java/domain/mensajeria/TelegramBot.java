package domain.mensajeria;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import domain.Config;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.HashSet;
import java.util.Set;

public class TelegramBot extends TelegramLongPollingBot {
    private static TelegramBot instance;
    private final Set<Long> knownChatIds = new HashSet<>();

    private TelegramBot() { //Constructor privado
            //Inicializo el bot
    }

    public static TelegramBot getInstance() {
                if (instance == null) {
                    instance = new TelegramBot();
                }
        return instance;
    }

    @Override
    public String getBotUsername() {
        try {
            return Config.getInstance().getProperty("TELEGRAM_BOT_USER_NAME");
        } catch (Exception e) {
            e.printStackTrace();
            return null; // o un valor por defecto
        }
    }

    @Override
    public String getBotToken() {
        try {
            return Config.getInstance().getProperty("TELEGRAM_TOKEN");
        } catch (Exception e) {
            e.printStackTrace();
            return null; // o un valor por defecto
        }
    }

    private String getChatId() {
        try {
            return Config.getInstance().getProperty("TELEGRAM_CHAT_ID");
        } catch (Exception e) {
            e.printStackTrace();
            return null; // o un valor por defecto
        }
    }


    public void notifyUsers(String notificationMessage) {
        SendMessage message = new SendMessage(getChatId(), notificationMessage);
        sendMessage(message);
    }

    @Override
    public void onUpdateReceived(Update update) {
        String message = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();

        if (!knownChatIds.contains(chatId)) {
            knownChatIds.add(chatId);
            sendWelcomeMessage();
        }
    }

    private void sendWelcomeMessage() {
        String welcomeText = "¡Bienvenido! Soy tu bot de ayuda. Aquí tienes algunas instrucciones para empezar...";
        sendMessage(new SendMessage(getChatId(), welcomeText));
    }

    private void sendMessage(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


}
