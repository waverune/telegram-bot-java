import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class czekFunctions extends eeeewwwwbot {

	static String wave = "xxxxxxxx";
	// read image file_id from our database file and store in a  arrayList of file ids  file_id.txt
	static ArrayList<String> fidList = new ArrayList<>();
	static Boolean ctr = false;
	static Boolean addPermission = false;

   void actionTaker(Update update) throws TelegramApiException, IOException {

    // initialises fid list just once
    if (ctr.equals(false)) {
      try {
		  readFromFile(fidList);
		  ctr = true;

      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    int fidList_size = fidList.size();
    // check if update has a msg and msg has text
    if (update.hasMessage() && update.getMessage().hasText()) {
      // the text received is put in msgText
      String msgText = update.getMessage().getText();

      log(User.user_first_name, User.user_last_name, User.chatID, msgText, "base");

      // START Display welcome message
      if (msgText.equals("/start")) {

        String welcome_msg =
            "Hi! " + User.user_first_name + " I'm AngelBot\nI am glad that you're here,\n \n";

        String wm0 = "Getting started:- \n\n";
        String wmx = "Tap on these links\n\n";
        String wm2 = "/sos : Helpline for Animals in Distress\n\n";
        String wm3 = "/fetch : xxxxxxxxxxxxxxxx \n\n ";
        String wm4 = "/visit : Visit me at instagram \n";
        String wm1 = "Send me Doggo pics right here, anytime!!!\nYou might as well be featured \n";

        sendText(User.chatID, welcome_msg + wm0 + wmx + wm2 + wm3 + wm4);
        sendText(User.chatID, wm1);

      }
      //  Instagram page link button
      else if (msgText.equals("/visit")) {
        // add hyperlink
        sendText(User.chatID, "Visit me at");
        String AngelInstaURL = "instagram.com/speechless.angels/";
        sendText(User.chatID, "\n\n" + AngelInstaURL + "\n\nI'll be waiting");
      }
      // Fetch random image from file_id.txt
      else if (msgText.equals("/fetch")) {
        int rand;
        rand = (int) (Math.random() * fidList_size);
        sendPic(User.chatID, fidList.get(rand));
        sendText(User.chatID, "use /visit for more ");
        sendMsg(User.chatID, "I'm sure you can send me some pics like these");
      }
      // SOS Emergency contacts
      else if (msgText.equals("/sos")) {
        // some emergency contacts
        sendMsg(User.chatID, "DO SHARE\n\n");
        String helpNos = "Helpline Numbers\n\n";
        String SGACC = "SGACC,Delhi 011-25447751 \n\n";
        String WFR = "Wildlife Rescue :+919810129698\n\n";
        String Friendicoes = "Friendicoes :+919873302580";

       sendText(User.chatID, helpNos + SGACC + WFR + Friendicoes);
      }

      // initiates image addition via app (admin)
      else if (msgText.equals("/addIMG") && User.chatID.equals(wave)) {
        addPermission = true;
        sendMsg(wave, "image addition mode DON'T FORGET TO send /close");
      } else if (msgText.equals("/close")) {
        addPermission = false;
      }

      // log(user_first_name, user_last_name,user_id, msgText, answer);
    }
    // DEAL with received images
    else if (update.hasMessage() && update.getMessage().hasPhoto()) {
      List<PhotoSize> photos = update.getMessage().getPhoto();
      String f_id =
          photos.stream().findFirst().orElse(null).getFileId(); // file id of received image
      String uploadReply = "Thank you so much!!!\nKeep sharing amazing things like these";
      sendText(User.chatID, uploadReply);
      // log the received photo @rec_img_file_id.txt
      try {
        createReceivedImgLog(User.user_first_name, User.user_last_name, User.chatID, f_id);
      } catch (IOException e) {
        e.printStackTrace();
      }
      // image addition (admin only)
      if (User.chatID.equals(wave) && addPermission.equals(true)) {
        try {
          BufferedWriter writeri = new BufferedWriter(new FileWriter("src/file_id.txt", true));
          writeri.append(f_id);
          writeri.append("\n");
          writeri.close();
          sendMsg(wave, "Successfully added\n /close");
          ctr = false;
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
}

//Functionalities
	// SEND message
 	void sendText(String chatId, String s) throws TelegramApiException {
	SendMessage sendMessage = new SendMessage();
	sendMessage.enableMarkdown(true);
	sendMessage.setChatId(chatId);
	sendMessage.setText(s);

		execute(sendMessage);

}


	//SEND Picture
	void sendPic(String chatID, String fId) {
		SendPhoto pic = new SendPhoto().setChatId(chatID).setPhoto(fId);
		try {
			execute(pic);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	//Read input from file line by line and store in array
	static void readFromFile(ArrayList<String> arrayL) throws IOException {
		BufferedReader bufReader = new BufferedReader(new FileReader("src/file_id.txt"));
		String line = bufReader.readLine();
		while (line != null) {
			arrayL.add(line);
			line = bufReader.readLine();
		}
	}

	//Received image log
	static void createReceivedImgLog(String fName, String lName, String user_id, String file_id) throws IOException {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		BufferedWriter writer = new BufferedWriter(new FileWriter("src/rec_img_file_id.txt", true));
		writer.append("\n*********************************************\n");
		writer.append(date + "\nUserID: " + user_id + "\nName: " + fName + " " + lName + "\n" + file_id);
		writer.close();
	}

	//something to do with keyboard button idk
	void sendMsg(String chatId, String s) {

		SendMessage sendMessage = new SendMessage();
		sendMessage.enableMarkdown(true);
		sendMessage.setChatId(chatId);
		sendMessage.setText(s);
		setButtons(sendMessage);
		try {
			execute(sendMessage);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	// keyboard buttons
	static void setButtons(SendMessage sendMessage) {
		// Create a keyboard
		ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
		sendMessage.setReplyMarkup(replyKeyboardMarkup);
		replyKeyboardMarkup.setSelective(true);
		replyKeyboardMarkup.setResizeKeyboard(true);
		replyKeyboardMarkup.setOneTimeKeyboard(false);

		// Create a list of keyboard rows
		List<KeyboardRow> keyboard = new ArrayList<>();

		// First keyboard row
		KeyboardRow keyboardFirstRow = new KeyboardRow();
		KeyboardRow keyboardSecondRow = new KeyboardRow();
		KeyboardRow xRow = new KeyboardRow();
		KeyboardRow yRow = new KeyboardRow();
		// Add buttons to the first keyboard row
		keyboardFirstRow.add(new KeyboardButton("/start"));
		keyboardSecondRow.add(new KeyboardButton("/fetch"));
		xRow.add(new KeyboardButton("/sos"));
		yRow.add(new KeyboardButton("/visit"));


		// Add all of the keyboard rows to the list
		keyboard.add(keyboardFirstRow);
		keyboard.add(keyboardSecondRow);
		keyboard.add(xRow);
		keyboard.add(yRow);

		// and assign this list to our keyboard
		replyKeyboardMarkup.setKeyboard(keyboard);
	}

	// Terminal log
	static void log(String first_name, String last_name, String user_id, String txt, String bot_answer) {
		System.out.println("\n ----------------------------");
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		System.out.println(dateFormat.format(date));
		System.out.println("Message from " + first_name + " " + last_name + ". (id = " + user_id + ") \n Text - " + txt);
		System.out.println("Bot answer: \n Text - " + bot_answer);
	}




}
