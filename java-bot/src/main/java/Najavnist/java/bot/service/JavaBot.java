package Najavnist.java.bot.service;

import Najavnist.java.bot.Buttons.ReplyKeyboardMarkupOll;

import Najavnist.java.bot.service.Logic.Console;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@PropertySource("tocen.properties")//додаєм файл з resources щоб створити private final tocen
//токен безпечно зберігається в папці ресурсів (git ігнорує папку і токен не пушить)

@Component//вкажи цю анотацію щоб 0 не було +версія spring-boot-starter-parent в POM
public class JavaBot extends TelegramLongPollingBot {
private final Environment tocen;//бере дані з resources (не забудь @PropertySource)

    boolean checkSurname = false;//для перевірки. текст рандомний чи прізвище
    boolean checkNumber  = false;
    boolean checkReplacement =false;//перевіряє чи мінялось прізвище
    // (щоб коли прізвище мінялось не запитував номер толефону)
    @Autowired
    public JavaBot(Environment tocen) {
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

        ReplyKeyboardMarkupOll replyKeyboardMarkupOll=new ReplyKeyboardMarkupOll();
        SendMessage sendMessage = new SendMessage();

            //System.out.println(update.getMessage());//вся інфа
            Message message = update.getMessage();//просто скоротив
            //Chat messageChat = message.getChat();//тоже скоротив. можна замість message -> update.getMessage()
            String text = message.getText();

            sendMessage.setChatId(String.valueOf(message.getChatId()));//ід чат
            Contact contact = update.getMessage().getContact();// Отримайте контакт користувача


            if (message.getForwardFrom() != null){//якшо переслав повідомлення (щоб уникнути фальсифікації гео даних,або контакт телефону)
                text = "Не роби так, бо розкажу офіцерові😠";
                sendMessage.setText(text);

                if (checkReplacement == false) {
                    sendMessage.enableMarkdown(true);//кнопки меню(Зміна прізвища,Вказати наявність)
                    ReplyKeyboardMarkup keyboardMarkup = replyKeyboardMarkupOll.getMenuKeyboardMenu();
                    sendMessage.setReplyMarkup(keyboardMarkup);
                }else {
                    sendMessage.enableMarkdown(true);//кнопки меню(Вказати наявність)
                    ReplyKeyboardMarkup keyboardMarkup = replyKeyboardMarkupOll.getMenuKeyboardMenu2();
                    sendMessage.setReplyMarkup(keyboardMarkup);
                }
            }else {//якщо не переслане повідомлення

                if (message.hasLocation()) {//локація, дата
                    String longitude = String.valueOf(message.getLocation().getLongitude());//Довгота
                    String latitude = String.valueOf(message.getLocation().getLatitude());  //Широта

                    DateTime dateTimeNow = DateTime.now();
                    // Формування рядка з дати та часу в форматі ISO 8601
                    String dateTimeFormatted = dateTimeNow.toString("dd-MM-yyyy HH:mm:ss");
                    // Формування рядка з дати та часу в налаштованому форматі

                    String url = "https://www.google.com/maps/?q=" + latitude + "," + longitude + "&z=15";
                    //створення посилання на гугл карту з довготи і широти

                    text = "Локацію отримано \n" + url + "\nДата та час: " + dateTimeFormatted;//створення повідомлення для виводу
                    text = text + "\nВиберіть пункт з наявних";

                    if (checkReplacement == false) {
                        sendMessage.enableMarkdown(true);//кнопки меню(Зміна прізвища,Вказати наявність)
                        ReplyKeyboardMarkup keyboardMarkup = replyKeyboardMarkupOll.getMenuKeyboardMenu();
                        sendMessage.setReplyMarkup(keyboardMarkup);
                    }else {
                        sendMessage.enableMarkdown(true);//кнопки меню(Вказати наявність)
                        ReplyKeyboardMarkup keyboardMarkup = replyKeyboardMarkupOll.getMenuKeyboardMenu2();
                        sendMessage.setReplyMarkup(keyboardMarkup);
                    }


                    sendMessage.setText(text);//відправка повідомлення
                }
                //}else if (checkNumber == true) {
                else if (checkNumber == true) {//номер телефону

                    checkNumber = false;
                    text = "Номер отримано " + contact.getPhoneNumber();
                    text = text + "\nВиберіть пункт з наявних";

                    sendMessage.setText(text);

                    sendMessage.enableMarkdown(true);//кнопки меню(Зміна прізвища,Вказати наявність)
                    ReplyKeyboardMarkup keyboardMarkup = replyKeyboardMarkupOll.getMenuKeyboardMenu();
                    sendMessage.setReplyMarkup(keyboardMarkup);
                    sendMessage.setText(text);
                    //елсе соут(помилка номеру)
                } else if (update.getMessage().getText().equals("/start")) {
                    text = "Привіт, я бот для перевірки наявності на НТП \nУведи своє прізвище та ініціали (Абрамов І.О.)";
                    checkSurname = true;//перевірка потрібна щоб наступне повідомлення користувача сприймалось як прізвище.

                    sendMessage.setText(text);

                } else {

                    if (update.getMessage().getText().equals("Змінити прізвище")) {
                        if (checkReplacement == false) {
                            text = "Введи правильне прізвище";
                            checkSurname = true;
                            checkReplacement=true;
                            sendMessage.setText(text);
                        }else {
                            text = "Ти вже міняв прізвище";
                            sendMessage.enableMarkdown(true);//кнопки меню(Вказати наявність)
                            ReplyKeyboardMarkup keyboardMarkup = replyKeyboardMarkupOll.getMenuKeyboardMenu2();
                            sendMessage.setReplyMarkup(keyboardMarkup);
                            sendMessage.setText(text);

                        }


                    } else if (update.getMessage().getText().equals("Вказати наявність")) {
                        text = "Виберіть пункт з наявних";
                        //кнопки наявності(наявний, за межами, відпустка, звільнення)
                        sendMessage.enableMarkdown(true);//кнопки меню(Зміна прізвища,Вказати наявність)
                        ReplyKeyboardMarkup keyboardMarkup = replyKeyboardMarkupOll.getMenuKeyboardPresence();
                        sendMessage.setReplyMarkup(keyboardMarkup);
                        sendMessage.setText(text);
                    } else if (update.getMessage().getText().equals("Наявний")) {
                        text = "Дай доступ до локації";

                            sendMessage.enableMarkdown(true);//кнопки меню(Зміна прізвища,Вказати наявність)
                            ReplyKeyboardMarkup keyboardMarkup = replyKeyboardMarkupOll.getMenuKeyboardLocation();
                            sendMessage.setReplyMarkup(keyboardMarkup);
                            sendMessage.setText(text);


                    } else if (update.getMessage().getText().equals("За межами")) {
                        text = "Дай доступ до локації";

                        sendMessage.enableMarkdown(true);//кнопки меню(Зміна прізвища,Вказати наявність)
                        ReplyKeyboardMarkup keyboardMarkup = replyKeyboardMarkupOll.getMenuKeyboardLocation();
                        sendMessage.setReplyMarkup(keyboardMarkup);
                        sendMessage.setText(text);


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

                    } else if (update.getMessage().getText().equals("Звільнення")) {
                        text = "Дай доступ до локації";

                        sendMessage.enableMarkdown(true);//кнопки меню(Зміна прізвища,Вказати наявність)
                        ReplyKeyboardMarkup keyboardMarkup = replyKeyboardMarkupOll.getMenuKeyboardLocation();
                        sendMessage.setReplyMarkup(keyboardMarkup);
                        sendMessage.setText(text);

                    } else if (message.hasText()) {
                        if (checkSurname == true) {
                            checkSurname = false;

                            if (checkReplacement==false){

                                text = "Прізвище отримано";
                                text = text + "\nНадайте доступ до контакту";

                                sendMessage.enableMarkdown(true);//кнопки з контактом(Поділитись контактом)
                                ReplyKeyboardMarkup keyboardMarkup = replyKeyboardMarkupOll.getMenuKeyboardPhone();
                                sendMessage.setReplyMarkup(keyboardMarkup);

                                sendMessage.setText(text);
                                checkNumber = true;
                            }else {
                                text = "Прізвище оновлено";
                                text = text + "\nВиберіть пункт з наявних";
                                sendMessage.enableMarkdown(true);//кнопки меню(Вказати наявність)
                                ReplyKeyboardMarkup keyboardMarkup = replyKeyboardMarkupOll.getMenuKeyboardMenu2();
                                sendMessage.setReplyMarkup(keyboardMarkup);
                                sendMessage.setText(text);
                            }
                        }else sendMessage.setText("я не розумію");

                    }
                }
            }
        try {
            execute(sendMessage);//ретурнить метод і відправляє повідомлення
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }}



