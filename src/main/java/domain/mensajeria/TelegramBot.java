package domain.mensajeria;
import lombok.Getter;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import domain.Config;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TelegramBot extends TelegramLongPollingBot {

    private static TelegramBot instance;
    private final Set<Long> knownChatIds = new HashSet<>();
    // Archivo para almacenar los chat IDs
    private static final String CHAT_ID_FILE = "src/main/resources/usuariosTelegram.txt";
    // Almacena los chat IDs de los usuarios registrados
    private static Map<String, Long> userChatIds = new HashMap<>();

    public TelegramBot() {
        loadChatIds(); // Cargar los chat IDs al iniciar el bot
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

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Long chatId = update.getMessage().getChatId(); // Obtener el chat ID
            String message = update.getMessage().getText(); // Obtener el mensaje
            String userName = update.getMessage().getFrom().getUserName(); // Obtener el nombre de usuario de telegram

            // Registrar el chat ID del usuario
            if (!userChatIds.containsKey(userName)) {
                userChatIds.put(userName, chatId);
                saveChatIds();
                sendWelcomeMessage(chatId);
            }
            System.out.println("Message received from " + userName + ": " + message);
        } else {
            System.out.println("Update received but no text message found.");
        }
    }

    private void sendWelcomeMessage(Long chatId) {
        String welcomeText = "¡Bienvenido! Ahora estás suscrito a las notificaciones.";
        sendMessage(chatId, welcomeText);
    }


    private void sendMessage(Long chatId, String message) {
        SendMessage sendMessage = new SendMessage(chatId.toString(), message);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void notifyUsers(String userName, String alertMessage) {
        Long chatId = userChatIds.get(userName);
        if (chatId != null) {
            sendMessage(chatId, alertMessage);
        } else {
            System.out.println("Usuario no encontrado o no registrado: " + userName);
        }
    }

    public void sendGroupMessage(String groupMessage) {
        for (Long chatId : userChatIds.values()) {
            sendMessage(chatId, groupMessage);
        }
    }
    private void saveChatIds() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CHAT_ID_FILE))) {
            for (Map.Entry<String, Long> entry : userChatIds.entrySet()) {
                writer.write(entry.getKey() + ":" + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadChatIds() {
        try (BufferedReader reader = new BufferedReader(new FileReader(CHAT_ID_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String userName = parts[0];
                    Long chatId = Long.parseLong(parts[1]);
                    userChatIds.put(userName, chatId);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
