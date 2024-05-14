package Najavnist.java.bot.service.Data;

import org.telegram.telegrambots.meta.api.objects.Update;

 public class CheckChatId {
    public void checkChatId(Data data, Update update){


            data.setCheckSurname(false);
            data.setCheckNumber(false);
            data.setCheckLocation(false);
            data.setCheckReplacement(false);

            data.setCheckStart(false);
            data.setCheckForward(false);

            data.setChatId("");
            data.setSurname("");
            data.setNumber("");
            data.setLocation("");
            data.setPresent("");
            data.setDate("");
            //data.setTime("");
        }

}
