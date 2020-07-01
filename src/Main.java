import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Main {
    public static void main(String[] args){
        //TODO  INITIALIZE API CONTEXT
        ApiContextInitializer.init();

        //TODO  INSTANTIATE TELEGRAM BOT API
        TelegramBotsApi newBot = new TelegramBotsApi();


        //TODO  REGISTER OUR BOT
        try {
            newBot.registerBot(new eeeewwwwbot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
