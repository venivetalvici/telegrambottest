import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {
    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new Bot());

        }catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(Message message, String text){
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);
        try {
            setButtons(sendMessage);
            sendMessage(sendMessage);
        }catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onUpdateReceived(Update update) {
       Model model =new Model();
        Message message = update.getMessage();
        if( message !=null && message.hasText()){
            switch (message.getText()){
                case "/help":
                    sendMsg(message, "Чем могу помочь?");
                            break;
                case"/settings":
                    sendMsg(message,"Что хотим пофиксить?");
                            break;
                case"привет":
                    sendMsg(message,"Здравствуй родная моя");
                    break;
                case"/Привет":
                    sendMsg(message,"Здорова");
                    break;
                default:
                    try{
                        sendMsg(message, Weather.getWeather(message.getText(),model));
                    } catch (IOException e){
                        sendMsg(message, "Город не найден");
                        break;
                    }
            }
        }
    }
    public void setButtons(SendMessage sendMessage){
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();

        keyboardFirstRow.add(new KeyboardButton("/help"));
        keyboardFirstRow.add(new KeyboardButton("/settings"));

        keyboardRowList.add(keyboardFirstRow);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
    }

    @Override
    public String getBotUsername() {
        return "Klubnichkarobot";
    }

    @Override
    public String getBotToken() {
        return "1604664218:AAFrXCdJLdvHce20HVBnzoq8Tnal5CnBF3U";
    }
}
