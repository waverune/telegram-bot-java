import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

public class eeeewwwwbot extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {
		czekFunctions myfn = new czekFunctions();
			// Set variables
			User.setUser(update);
		try {
			myfn.actionTaker(update);
		} catch (TelegramApiException | IOException e) {
			e.printStackTrace();
		}
	}

    	@Override
  	  	public String getBotUsername() {
        return "eeeewwwwbot";
    }

    	@Override
    	public String getBotToken() {
     	   return "<bot_token>";
   		}

	// terminal messenger NOT WORKING
/*		public void Xtreme(String x_txt){
			System.out.println("override text "+x_txt);
    		sendMsg(wave,x_txt);
	}*/

}
