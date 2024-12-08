package domain.mensajeria;

import domain.Config;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;


//Necesario para levantar una sesión de escucha del bot
public class mainRegisterUserTelegram {
    public static void main(String[] args) throws TelegramApiException {

        try {
            Config config = Config.getInstance();
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            TelegramBot bot = TelegramBot.getInstance();
            botsApi.registerBot(bot);
            System.out.println("Bot is up and running!");
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }
}
