package Najavnist.java.bot.service;

import Najavnist.java.bot.Buttons.ReplyKeyboardMarkupOll;
import Najavnist.java.bot.Buttons.ReplyKeyboardMarkupOll_Inline;
import Najavnist.java.bot.DataBase.Person;
import Najavnist.java.bot.DataBase.PersonDB;
import Najavnist.java.bot.service.Logic.Console;
import Najavnist.java.bot.service.Logic.Logic;
import Najavnist.java.bot.service.Logic.LogicPara;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@PropertySource("tocen.properties")//додаєм файл з resourcos щоб створити private final tocen

@PropertySource("classpath:hibernate.properties")//шоб бд бачило
@EnableTransactionManagement //спрінг буде управляти транзакціями (відкривати,закривати)

@Component//вкажи цю анотацію щоб 0 не було +pom
public class JavaBot extends TelegramLongPollingBot {
private final Environment tocen;//бере дані з resources (не забудь @PropertySource)



    private final Environment env;

    @Autowired
    public JavaBot(Environment env,Environment tocen) {
        this.env = env;
        this.tocen=tocen;
    }
    @Override
    public String getBotUsername() {return tocen.getRequiredProperty("BotUsername");}//privat final tocen який бере дані з resourses

    @Override
    public String getBotToken() {
        return tocen.getRequiredProperty("BotToken");
    }



    @Override
    public void onUpdateReceived(Update update) {
    //System.out.println(update.getMessage());//вся інфа
    new Console().output(update);//вивід в консоль що отримав від бота
        //JavaBot javaBot=new JavaBot();//щоб змінні з цього класу брати


        Logic logic=new Logic();
        ReplyKeyboardMarkupOll replyKeyboardMarkupOll=new ReplyKeyboardMarkupOll();
        Person person=new Person();
        PersonDB personDB=new PersonDB();
//try {
    logic.logica(update, replyKeyboardMarkupOll, person, personDB);//клас де вся логіка.
    // зроби приватним і гетер.
//}catch (NullPointerException e){
//    System.out.println("null(");
//}
        try {
            execute(logic.logica(update,replyKeyboardMarkupOll,person,personDB));//ретурнить метод і відправляє повідомлення, яке сформоване в Logic
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

//        //пара
//        System.out.println(update.getMessage().getText());
//        LogicPara logicPara=new LogicPara();
//        ReplyKeyboardMarkupOll_Inline replyKeyboardMarkupOll_inline=new ReplyKeyboardMarkupOll_Inline();
//        logicPara.logica(update,replyKeyboardMarkupOll_inline);//клас де вся логіка.
//        try {
//            execute(logicPara.logica(update,replyKeyboardMarkupOll_inline));//ретурнить метод і відправляє повідомлення, яке сформоване в Logic
//        } catch (TelegramApiException e) {
//            throw new RuntimeException(e);
//        }

    }
    public Environment getTocen() {
        return tocen;
    }

    public Environment getEnv() {
        return env;
    }
}
