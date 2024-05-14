package Najavnist.java.bot.service.Logic;

import org.joda.time.DateTime;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public class Console {
    public void output(Update update) {

        DateTime dateTimeNowConsole = DateTime.now();
        // Формування рядка з дати та часу в форматі ISO 8601
        String dateTimeFormattedConsole = dateTimeNowConsole.toString("dd.MM.yy HH:mm:ss");
        // Формування рядка з дати та часу в налаштованому форматі

        //System.out.println(update.getMessage());//вся інфа

        if (update.hasCallbackQuery()) {//для виводу інлайн кнопок


            if (update.getCallbackQuery().getData().equals("yes")) {
                System.out.println(dateTimeFormattedConsole+" "+ "!Мені подобається бот " + "(" + update.getCallbackQuery().getData() + ")");
            } else if (update.getCallbackQuery().getData().equals("no")) {
                System.out.println(dateTimeFormattedConsole+" "+ "!Мені не подобається бот " + "(" + update.getCallbackQuery().getData() + ")");
            } else {
                System.out.println(dateTimeFormattedConsole+" "+ "!" + update.getCallbackQuery().getData());
            }

        }else {

            Message message = update.getMessage();//просто скоротив
            Chat messageChat = message.getChat();//тоже скоротив. можна замість message -> update.getMessage()
            Contact contact = update.getMessage().getContact();// Отримайте контакт користувача

            if (message.hasLocation()) {//якщо повідомлення містить локацію щоб не виводити в консоль null

                String longitude = String.valueOf(message.getLocation().getLongitude());//довгота
                String latitude = String.valueOf(message.getLocation().getLatitude());//широта

                String url = "https://www.google.com/maps/?q=" + latitude + "," + longitude + "&z=15";//посилання на гугл карту з довготи і широти
                if (message.getForwardFrom() != null) {//якщо переслав місцезнаходження
                    System.out.println(dateTimeFormattedConsole+" "+ "!!!!!" + url + " (" + messageChat.getFirstName() + " " + messageChat.getLastName() + " https://t.me/" + messageChat.getUserName() + " " + messageChat.getId() + " )");
                } else {//якщо не пересилав місцезнаходження
                    System.out.println(dateTimeFormattedConsole+" "+ url + " (" + messageChat.getFirstName() + " " + messageChat.getLastName() + " https://t.me/" + messageChat.getUserName() + " " + messageChat.getId() + " )");
                }
            } else if (message.hasContact()) {//якшо номер шоб не виводити null
                if (message.getForwardFrom() != null) {//якщо переслав
                    System.out.println(dateTimeFormattedConsole+" "+ "!!!!!" + contact.getPhoneNumber() + " (" + messageChat.getFirstName() + " " + messageChat.getLastName() + " https://t.me/" + messageChat.getUserName() + " " + messageChat.getId() + " )");
                } else {//якщо не переслав
                    //контакт перетворити в номер\/
                    System.out.println(dateTimeFormattedConsole+" "+ contact.getPhoneNumber() + " (" + messageChat.getFirstName() + " " + messageChat.getLastName() + " https://t.me/" + messageChat.getUserName() + " " + messageChat.getId() + " )");
                }
//
            } else if (message.hasText()) {//якщо текст
                if (message.getForwardFrom() != null) {//якщо переслав
                    System.out.println(dateTimeFormattedConsole+" "+ "!!!!!" + message.getText() + " (" + messageChat.getFirstName() + " " + messageChat.getLastName() + " https://t.me/" + messageChat.getUserName() + " " + messageChat.getId() + " )");
                } else {//якщо не переслав
                    System.out.println(dateTimeFormattedConsole+" "+ message.getText() + " (" + messageChat.getFirstName() + " " + messageChat.getLastName() + " https://t.me/" + messageChat.getUserName() + " " + messageChat.getId() + " )");
                }
            } else {//інші повідомлення без тексту (GIF, стікери, мультимедія)
                if (message.getForwardFrom() != null) {//якщо переслав
                    System.out.println(dateTimeFormattedConsole+" !!!!! ___ (" + messageChat.getFirstName() + " " + messageChat.getLastName() + " https://t.me/" + messageChat.getUserName() + " " + messageChat.getId() + " )");
                } else {//якщо не переслав
                    System.out.println(dateTimeFormattedConsole+" ___ (" + messageChat.getFirstName() + " " + messageChat.getLastName() + " https://t.me/" + messageChat.getUserName() + " " + messageChat.getId() + " )");
                }

            }
        }
        }
    }
