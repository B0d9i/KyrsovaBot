package Najavnist.java.bot.service.Logic;

import Najavnist.java.bot.Buttons.ReplyKeyboardMarkupOll;

import Najavnist.java.bot.DataBase.Person;
import Najavnist.java.bot.DataBase.PersonDB;
import Najavnist.java.bot.service.JavaBot;
import org.joda.time.DateTime;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

public class Logic {

    SendMessage sendMessage = new SendMessage();
    public boolean checkSurname = false;//в бд
    public boolean checkNumber  = false;//в бд
    public SendMessage logica(Update update, ReplyKeyboardMarkupOll replyKeyboardMarkupOll, Person person, PersonDB personDBz){
        //System.out.println(update.getMessage());//вся інфа
        Message message = update.getMessage();//просто скоротив
        Chat messageChat = message.getChat();//тоже скоротив. можна замість message -> update.getMessage()
        String text = message.getText();

        //sendMessage.setText("прийняв повідомлення: "+message.getText());//вивід в чат(текст який приняв)
        sendMessage.setChatId(String.valueOf(message.getChatId()));//ід чат
        Contact contact = update.getMessage().getContact();// Отримайте контакт користувача


        if (message.getForwardFrom() != null){
            text = "Розробник розумніший, і продумав це 😋";
            sendMessage.setText(text);
        }else {

        if (message.hasLocation()) {
            String longitude= String.valueOf(message.getLocation().getLongitude());
            String latitude= String.valueOf(message.getLocation().getLatitude());

            DateTime dateTimeNow = DateTime.now();
            // Формування рядка з дати та часу в форматі ISO 8601
            String dateTimeAsString = dateTimeNow.toString();
            // Формування рядка з дати та часу в налаштованому форматі
            String dateTimeFormatted = dateTimeNow.toString("dd-MM-yyyy HH:mm:ss");

            String url = "https://www.google.com/maps/?q=" + latitude + "," + longitude + "&z=15";
            //text = "Локацію отримано "+message.getLocation();
            text = "Локацію отримано \n"+url+"\nДата та час: "+dateTimeFormatted;
            text = text + "\nВиберіть пункт з наявних";
            sendMessage.enableMarkdown(true);//кнопки меню(Зміна прізвища,Вказати наявність)
            ReplyKeyboardMarkup keyboardMarkup = replyKeyboardMarkupOll.getMenuKeyboardMenu();
            sendMessage.setReplyMarkup(keyboardMarkup);

            sendMessage.setText(text);
        }//}else if (checkNumber == true) {
        else if (message.hasContact()) {
            //if повідомлення містить контакт,присвоїти змінній та записати в бд
            //message.getText();
            checkNumber = false;
            text = "Номер отримано " + contact.getPhoneNumber();
            text = text + "\nВиберіть пункт з наявних";

            sendMessage.setText(text);

            sendMessage.enableMarkdown(true);//кнопки меню(Зміна прізвища,Вказати наявність)
            ReplyKeyboardMarkup keyboardMarkup = replyKeyboardMarkupOll.getMenuKeyboardMenu();
            sendMessage.setReplyMarkup(keyboardMarkup);
            sendMessage.setText(text);
            //елсе соут(помилка номеру)
        }else if (update.getMessage().getText().equals("/start")) {
            text = "Привіт, я бот для перевірки наявності на НТП \nУведи своє прізвище та ініціали (Коваль І.О.)";

            //personDB.saveCheckSurname(person,update);//збереження змінної перевірки в бд
            sendMessage.setText(text);
        }//}else if (checkSurname==true){
        else if (message.getText().equals("t1")) {//якщо checkSurname==true (уведені дані записує як прізвище)

            //if (в бд є дані){//якщо є дані обновити
            if (update.getMessage().getText().equals("t2")) {//просто приклад. удали
            // обновити даніьмт
            checkSurname = false;
            text ="Прізвище оновлено";
            text =text+"\nВиберіть пункт з наявних";
            sendMessage.enableMarkdown(true);//кнопки меню(Зміна прізвища,Вказати наявність)
            ReplyKeyboardMarkup keyboardMarkup = replyKeyboardMarkupOll.getMenuKeyboardMenu();
            sendMessage.setReplyMarkup(keyboardMarkup);
            sendMessage.setText(text);
            }
            //}else{//якщо нема в бд. записати
            //message.getText();//записати прізвище в БД
            else if (update.getMessage().getText().equals("t1")) {

            checkSurname = false;
            text ="Прізвище отримано";
            text =text+"\nНадайте доступ до контакту";

            sendMessage.enableMarkdown(true);//кнопки з контактом(Поділитись контактом)
            ReplyKeyboardMarkup keyboardMarkup = replyKeyboardMarkupOll.getMenuKeyboardPhone();
            sendMessage.setReplyMarkup(keyboardMarkup);

            sendMessage.setText(text);
            checkNumber = true;}
        }else {
//            if (update.getMessage().getText().equals("/t")) {
//                text = ")";
//                //checkSurname=true;
//                //checkNumber
//                sendMessage.setText(text);
//            }
            if (update.getMessage().getText().equals("Змінити прізвище")) {
                text = "Введи правильне прізвище";
                checkSurname = true;
                sendMessage.setText(text);

            } else if (update.getMessage().getText().equals("Вказати наявність")) {
                text = "Виберіть пункт з наявних";
                //кнопки наявності(наявний, за межами, відпустка, звільнення)
                sendMessage.enableMarkdown(true);//кнопки меню(Зміна прізвища,Вказати наявність)
                ReplyKeyboardMarkup keyboardMarkup = replyKeyboardMarkupOll.getMenuKeyboardPresence();
                sendMessage.setReplyMarkup(keyboardMarkup);
                sendMessage.setText(text);
            } else if (update.getMessage().getText().equals("Наявний")) {
                text = "Дай доступ до локації";
                //if (є в бд){
                //оновити дані (наявності,локація,дата,час)
                // text="\nДані оновлено"+text;

                // }else {
                // ввести дані
                //text="\nДані додано"+text;
                // }
                sendMessage.enableMarkdown(true);//кнопки меню(Зміна прізвища,Вказати наявність)
                ReplyKeyboardMarkup keyboardMarkup = replyKeyboardMarkupOll.getMenuKeyboardLocation();
                sendMessage.setReplyMarkup(keyboardMarkup);
                sendMessage.setText(text);
                //потім дороби ше один іф
//                sendMessage.enableMarkdown(true);//кнопки меню(Зміна прізвища,Вказати наявність)
//                ReplyKeyboardMarkup keyboardMarkup = replyKeyboardMarkupOll.getMenuKeyboardMenu();
//                sendMessage.setReplyMarkup(keyboardMarkup);
//                sendMessage.setText(text);

            } else if (update.getMessage().getText().equals("За межами")) {
                text = "Дай доступ до локації";
                //if (є в бд){
                //оновити дані (наявності,локація,дата,час)
                // text="\nДані оновлено"+text;

                // }else {
                // ввести дані
                //text="\nДані додано"+text;
                // }
                sendMessage.enableMarkdown(true);//кнопки меню(Зміна прізвища,Вказати наявність)
                ReplyKeyboardMarkup keyboardMarkup = replyKeyboardMarkupOll.getMenuKeyboardLocation();
                sendMessage.setReplyMarkup(keyboardMarkup);
                sendMessage.setText(text);

                //потім дороби ше один іф
//                sendMessage.enableMarkdown(true);//кнопки меню(Зміна прізвища,Вказати наявність)
//                ReplyKeyboardMarkup keyboardMarkup = replyKeyboardMarkupOll.getMenuKeyboardMenu();
//                sendMessage.setReplyMarkup(keyboardMarkup);
//                sendMessage.setText(text);
            } else if (update.getMessage().getText().equals("Відпустка")) {
                text = "Дай доступ до локації";
                //if (є в бд){
                //оновити дані (наявності,локація,дата,час)
                // text="\nДані оновлено"+text;

                // }else {
                // ввести дані
                //text="\nДані додано"+text;
                // }
                sendMessage.enableMarkdown(true);//кнопки меню(Зміна прізвища,Вказати наявність)
                ReplyKeyboardMarkup keyboardMarkup = replyKeyboardMarkupOll.getMenuKeyboardLocation();
                sendMessage.setReplyMarkup(keyboardMarkup);
                sendMessage.setText(text);

                //потім дороби ше один іф
//                sendMessage.enableMarkdown(true);//кнопки меню(Зміна прізвища,Вказати наявність)
//                ReplyKeyboardMarkup keyboardMarkup = replyKeyboardMarkupOll.getMenuKeyboardMenu();
//                sendMessage.setReplyMarkup(keyboardMarkup);
//                sendMessage.setText(text);

            } else if (update.getMessage().getText().equals("Звільнення")) {
                text = "Дай доступ до локації";
                //if (є в бд){
                //оновити дані (наявності,локація,дата,час)
                // text="\nДані оновлено"+text;

                // }else {
                // ввести дані
                //text="\nДані додано"+text;
                // }
                sendMessage.enableMarkdown(true);//кнопки меню(Зміна прізвища,Вказати наявність)
                ReplyKeyboardMarkup keyboardMarkup = replyKeyboardMarkupOll.getMenuKeyboardLocation();
                sendMessage.setReplyMarkup(keyboardMarkup);
                sendMessage.setText(text);

                //потім дороби ше один іф
//                sendMessage.enableMarkdown(true);//кнопки меню(Зміна прізвища,Вказати наявність)
//                ReplyKeyboardMarkup keyboardMarkup = replyKeyboardMarkupOll.getMenuKeyboardMenu();
//                sendMessage.setReplyMarkup(keyboardMarkup);
//                sendMessage.setText(text);
            } else if (message.getText().equals("дам")) {//

            } else sendMessage.setText("я не розумію");

        }
        }return sendMessage;
}



}



