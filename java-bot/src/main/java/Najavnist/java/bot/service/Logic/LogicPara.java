package Najavnist.java.bot.service.Logic;

import Najavnist.java.bot.Buttons.ReplyKeyboardMarkupOll;
import Najavnist.java.bot.Buttons.ReplyKeyboardMarkupOll_Inline;
import Najavnist.java.bot.DataBase.Person;
import Najavnist.java.bot.DataBase.PersonDB;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.*;

public class LogicPara {
    SendMessage sendMessage = new SendMessage();
    public SendMessage logica(Update update, ReplyKeyboardMarkupOll_Inline replyKeyboardMarkupOll_inline) {
        //System.out.println(update.getMessage());//вся інфа
//        Message message = update.getMessage();//просто скоротив
//        Chat messageChat = update.getMessage().getChat();//тоже скоротив. можна замість message -> update.getMessage()
//        String text = update.getMessage().getText();

        //sendMessage.setText("прийняв повідомлення: "+message.getText());//вивід в чат(текст який приняв)
        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));//ід чат
        Contact contact = update.getMessage().getContact();// Отримайте контакт користувача

        if (update.hasMessage()){
            Message message = update.getMessage();
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            String chatID = String.valueOf(message.getChatId());
            sendMessage.setText("ughhiwsgiwsig");
            sendMessage.setReplyMarkup(replyKeyboardMarkupOll_inline.getInlineKeyboardMarkup());

        }else if(update.hasCallbackQuery()){
            CallbackQuery callbackQuery = update.getCallbackQuery();
            Message message = callbackQuery.getMessage();
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            if (callbackQuery.getData().equals("101")) {
                sendMessage.setText("Переадресація дзвінка до служби порятунку");
            } else if (callbackQuery.getData().equals("102")) {
                sendMessage.setText("Переадресація дзвінка до поліції");
            } else if (callbackQuery.getData().equals("103")) {
                sendMessage.setText("Відбувається переадресація дзвінка на швидку медичну допомогу");
            }
        }


        return sendMessage;
    }
    }
