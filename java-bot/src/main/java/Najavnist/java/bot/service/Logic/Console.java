package Najavnist.java.bot.service.Logic;

import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public class Console {
    public void output(Update update) {


        //System.out.println(update.getMessage());//вся інфа
        Message message = update.getMessage();//просто скоротив
        Chat messageChat = message.getChat();//тоже скоротив. можна замість message -> update.getMessage()
        Contact contact = update.getMessage().getContact();// Отримайте контакт користувача


         if (message.hasLocation()) {

            String longitude= String.valueOf(message.getLocation().getLongitude());
            String latitude= String.valueOf(message.getLocation().getLatitude());

            String url = "https://www.google.com/maps/?q=" + latitude + "," + longitude + "&z=15";
             if (message.getForwardFrom() != null){
                 System.out.println("!!!!!"+url + " (" + messageChat.getFirstName() + " " + messageChat.getLastName() + " @" + messageChat.getUserName() + " " + messageChat.getId() + " )");

             }else System.out.println(url+" ("+ messageChat.getFirstName() + " " + messageChat.getLastName() + " @" + messageChat.getUserName() + " " + messageChat.getId() + " )");

        }else if (message.hasContact()) {//якшо номер шоб не виводити null
             if (message.getForwardFrom() != null){
                 System.out.println("!!!!!"+contact.getPhoneNumber()+ " (" + messageChat.getFirstName() + " " + messageChat.getLastName() + " @" + messageChat.getUserName() + " " + messageChat.getId() + " )");
             }else {
                 //контакт перетворити в номер\/
                 System.out.println(contact.getPhoneNumber() + " (" + messageChat.getFirstName() + " " + messageChat.getLastName() + " @" + messageChat.getUserName() + " " + messageChat.getId() + " )");
             }   //вивід консоль /\
        } else {
             if (message.getForwardFrom() != null){
                 System.out.println("!!!!!"+message.getText()+ " (" + messageChat.getFirstName() + " " + messageChat.getLastName() + " @" + messageChat.getUserName() + " " + messageChat.getId() + " )");
             }else {
                 System.out.println(message.getText() + " (" + messageChat.getFirstName() + " " + messageChat.getLastName() + " @" + messageChat.getUserName() + " " + messageChat.getId() + " )");
             }//вивід консоль /\

        }

    }
}
