import org.telegram.telegrambots.meta.api.objects.Update;

class User {
	//user first name
	static String user_first_name;
	static String user_last_name;
	static String user_ID;
	static String chatID;
	static String username;
	static void setUser(Update update){
		user_first_name = update.getMessage().getChat().getFirstName();
		username = update.getMessage().getChat().getUserName();
		user_last_name = update.getMessage().getChat().getLastName();
		user_ID = update.getMessage().getChat().getId().toString();
		chatID = update.getMessage().getChatId().toString();
	}

}
