package Najavnist.java.bot.service.Logic;

import Najavnist.java.bot.service.Buttons.ReplyKeyboardMarkupOll;

import Najavnist.java.bot.service.Buttons.ReplyKeyboardMarkupOll_Inline;
import Najavnist.java.bot.service.Data.CheckChatId;
import Najavnist.java.bot.service.Data.Data;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@PropertySource("tocen.properties")//додаєм файл з resources щоб створити private final tocen
//токен безпечно зберігається в папці ресурсів (git ігнорує папку і токен не пушить)

@Component//вкажи цю анотацію щоб 0 не було +версія spring-boot-starter-parent 2.7.12 в POM
public class JavaBot extends TelegramLongPollingBot {
private final Environment tocen;//бере дані з resources (не забудь @PropertySource)
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

    Data data=new Data();//об'єкт класу щоб брати дані


    @Override
    public void onUpdateReceived(Update update) {

        SendMessage sendMessage = new SendMessage();
        //System.out.println(update.getMessage());//вся інфа
        Message message = update.getMessage();//просто скоротив

        ReplyKeyboardMarkupOll replyKeyboardMarkupOll = new ReplyKeyboardMarkupOll();
        ReplyKeyboardMarkupOll_Inline replyKeyboardMarkupOll_inline = new ReplyKeyboardMarkupOll_Inline();

        new Console().output(update);//вивід в консоль що отримав від бота


        sendMessage.enableMarkdown(true);//пусті кнопки
        ReplyKeyboardMarkup keyboardMarkup = replyKeyboardMarkupOll.getMenuKeyboardNULL();
        sendMessage.setReplyMarkup(keyboardMarkup);




        if (update.hasCallbackQuery()) {

            CallbackQuery callbackQuery = update.getCallbackQuery();
            message = callbackQuery.getMessage();
            sendMessage.setChatId(String.valueOf(message.getChatId()));//ід чат для відправки повідомлення

            if (callbackQuery.getData().equals("yes")) {
                sendMessage.setText("Радий, що сподобався😊\n\n( Для продовження /start )");
            } else if (callbackQuery.getData().equals("no")) {
                sendMessage.setText("Guten Tag😋\n\n( Для продовження /start )");
            }
        }else {
            if ((String.valueOf(update.getMessage().getChatId())).equals(data.getChatId())) {
            } else {//початковий, або попередній id не збігається з теперешнім
                System.out.println("                  ⬆️НОВИЙ КОРИСТУВАЧ⬆️");
                new CheckChatId().checkChatId(data, update);//обнульовує всю інформацію якщо написав користувач з іншим id чату
            }



            if (data.getSurname() != "" && data.getDate() != "" && data.getLocation() != "" && data.getNumber() != "") {
                data.setCheckStart(true);
            }

            String text = message.getText();

            sendMessage.setChatId(String.valueOf(message.getChatId()));//ід чат для відправлення повідомленя
            Contact contact = update.getMessage().getContact();// Отримайте контакт користувача

            if (message.getForwardFrom() != null) {//якщо переслав повідомлення (щоб уникнути фальсифікації гео даних, або контакт телефону)
                text = "Не пересилай повідомлення, бо розкажу офіцерові😠(/start)";

                sendMessage.setText(text);
                data.setCheckForward(true);//то для відображення що він не чесний

            } else if (update.getMessage().hasText()) {//якщо повідомлення містить текст

                if (update.getMessage().getText().equals("/start")) {

                    //на випадок, якщо захоче написати /start підчас вводу даних
                    data.setCheckSurname(false);
                    data.setCheckNumber(false);
                    data.setCheckLocation(false);

                    if (data.getCheckStart() == false) {//якщо це перший запуск, або не вказав основні дані(прізвище, номер, локацію)

                        if (data.getSurname() != "") {//якщо прізвище вказав (залишилось, номер і локацію)
                            if (data.getNumber() != "") {// якщо номер вказав (залишилось і локацію)
                                text="Ти не повністю ввів дані\n\nВкажи наявність:";

                                sendMessage.enableMarkdown(true);//кнопки наявності(наявний, за межами, відпустка, звільнення)
                                keyboardMarkup = replyKeyboardMarkupOll.getMenuKeyboardPresence();
                                sendMessage.setReplyMarkup(keyboardMarkup);
                                sendMessage.setText(text);
                            }else { //не вказав номер (data.getNumber() == "")
                                text = "Ти не повністю ввів дані\n\nНадайте доступ до контакту:";

                                sendMessage.enableMarkdown(true);//кнопки з контактом(Поділитись контактом)
                                keyboardMarkup = replyKeyboardMarkupOll.getMenuKeyboardPhone();
                                sendMessage.setReplyMarkup(keyboardMarkup);

                                sendMessage.setText(text);
                                data.setCheckNumber(true);
                            }
                        }else {//не вказав прізвище
                            text = "Привіт, я бот для перевірки наявності на НТП \n\nСпочатку уведи дані які попрошу (натискай на кнопки або вводь з клавіатури), " +
                                    "а потім зможеш відправляти свою наявність" +
                                    "\n\nУведи своє прізвище та ініціали (Абрамов І.О.)";
                            data.setCheckSurname(true);//перевірка потрібна, щоб наступне повідомлення користувача сприймалось як прізвище.


                        }

                    } else {// якщо бот запускають не в перше(збережені дані прізвища, номер, локація)
                        text = "З поверненням " + data.getSurname() + "\n\nВиберіть пункт з наявних:";
                        if (data.getCheckReplacement() == false) {//якщо міняв прізвище
                            sendMessage.enableMarkdown(true);//кнопки меню(Зміна прізвища, вказати наявність)
                            keyboardMarkup = replyKeyboardMarkupOll.getMenuKeyboardMenu();
                            sendMessage.setReplyMarkup(keyboardMarkup);
                        } else {//якщо замінив прізвище
                            sendMessage.enableMarkdown(true);//кнопки меню(Вказати наявність)
                            keyboardMarkup = replyKeyboardMarkupOll.getMenuKeyboardMenu2();
                            sendMessage.setReplyMarkup(keyboardMarkup);
                        }
                    }

                    sendMessage.setText(text);
                } else if (update.getMessage().getText().equals("/data")) {
                    text = "Інформація яку я запам'ятав: "
                            + "\nПрізвище: " + data.getSurname()
                            + "\nНомер телефону: " + data.getNumber()
                            + "\nЧесний: " + !data.getCheckForward()//якщо не пересилав, значить чесний
                            + "\n\nНаявність: " + data.getPresent()
                            + "\nЛокація: " + data.getLocation()
                            + "\nДата та час локації: " + data.getDate()
                            + "\n\nТобі сподобався мій бот?";

                    //на випадок, якщо захоче написати /data підчас вводу даних
                    data.setCheckSurname(false);
                    data.setCheckNumber(false);
                    data.setCheckLocation(false);

                    //////////////////////////
//                    sendMessage.enableMarkdown(true);//кнопки ()
//                    ReplyKeyboardMarkup keyboardMarkup2 = replyKeyboardMarkupOll.getMenuKeyboardNULL();//пусті кнопки
//                    sendMessage.setReplyMarkup(keyboardMarkup2);

                    sendMessage.enableMarkdown(true);//Сподобався бот?(Так Ні)
                    InlineKeyboardMarkup keyboardMarkup2 = replyKeyboardMarkupOll_inline.getInlineKeyboardMarkup();
                    sendMessage.setReplyMarkup(keyboardMarkup2);
                    sendMessage.setText(text);



                } else if (update.getMessage().getText().equals("Змінити прізвище")) {
                    if (data.getCheckReplacement() == false) {//якщо не міняв прізвище
                        text = "Введи правильне прізвище";

                        data.setCheckSurname(true);//наступне повідомлення сприйметься як прізвище
                        data.setCheckReplacement(true);//прізвище змінено
                        sendMessage.setText(text);


                    } else {//якщо вже міняв прізвище, і вручну пропише(кнопка не доступна коли змінив прізвище)
                        text = "Ти вже міняв прізвище";

                        sendMessage.enableMarkdown(true);//кнопки меню(Вказати наявність)
                        keyboardMarkup = replyKeyboardMarkupOll.getMenuKeyboardMenu2();
                        sendMessage.setReplyMarkup(keyboardMarkup);
                        sendMessage.setText(text);
                    }

                } else if (update.getMessage().getText().equals("Вказати наявність")) {
                    text = "Виберіть пункт з наявних";

                    sendMessage.enableMarkdown(true);//кнопки наявності(наявний, за межами, відпустка, звільнення)
                    keyboardMarkup = replyKeyboardMarkupOll.getMenuKeyboardPresence();
                    sendMessage.setReplyMarkup(keyboardMarkup);
                    sendMessage.setText(text);
                } else if (update.getMessage().getText().equals("Наявний")) {
                    text = "Дай доступ до локації";

                    sendMessage.enableMarkdown(true);//кнопка (Поділитися локацією)
                    keyboardMarkup = replyKeyboardMarkupOll.getMenuKeyboardLocation();
                    sendMessage.setReplyMarkup(keyboardMarkup);
                    sendMessage.setText(text);
                    data.setCheckLocation(true);
                    data.setPresent("Наявний");

                } else if (update.getMessage().getText().equals("За межами")) {
                    text = "Дай доступ до локації";

                    sendMessage.enableMarkdown(true);//кнопка (Поділитися локацією)
                    keyboardMarkup = replyKeyboardMarkupOll.getMenuKeyboardLocation();
                    sendMessage.setReplyMarkup(keyboardMarkup);
                    sendMessage.setText(text);

                    data.setCheckLocation(true);
                    data.setPresent("За межами");

                } else if (update.getMessage().getText().equals("Відпустка")) {
                    text = "Дай доступ до локації";

                    sendMessage.enableMarkdown(true);//кнопка (Поділитися локацією)
                    keyboardMarkup = replyKeyboardMarkupOll.getMenuKeyboardLocation();
                    sendMessage.setReplyMarkup(keyboardMarkup);
                    sendMessage.setText(text);

                    data.setCheckLocation(true);
                    data.setPresent("Відпустка");

                } else if (update.getMessage().getText().equals("Звільнення")) {
                    text = "Дай доступ до локації";

                    sendMessage.enableMarkdown(true);//кнопка (Поділитися локацією)
                    keyboardMarkup = replyKeyboardMarkupOll.getMenuKeyboardLocation();
                    sendMessage.setReplyMarkup(keyboardMarkup);
                    sendMessage.setText(text);

                    data.setCheckLocation(true);
                    data.setPresent("Звільнення");

                } else {//довільний текст
                    if (data.getCheckSurname() == true) {//якщо прізвище
                        data.setCheckSurname(false);

                        if (data.getCheckReplacement() == false) {//якщо не міняв прізвище

                            text = "Прізвище отримано\n";
                            text = text + "\nНадайте доступ до контакту";

                            sendMessage.enableMarkdown(true);//кнопки з контактом(Поділитись контактом)
                            keyboardMarkup = replyKeyboardMarkupOll.getMenuKeyboardPhone();
                            sendMessage.setReplyMarkup(keyboardMarkup);

                            sendMessage.setText(text);
                            data.setCheckNumber(true);
                            data.setSurname(message.getText());//запам'ятовує ім'я
                        } else {//якщо міняв прізвище
                            text = "Прізвище оновлено\n";
                            text = text + "\nВиберіть пункт з наявних";

                            sendMessage.enableMarkdown(true);//кнопки меню(Вказати наявність)
                            keyboardMarkup = replyKeyboardMarkupOll.getMenuKeyboardMenu2();
                            sendMessage.setReplyMarkup(keyboardMarkup);

                            sendMessage.setText(text);
                            data.setSurname(message.getText());//перезаписує ім'я
                        }
                    } else {//остальний текст
                        sendMessage.setText("я не розумію 😟(/start)");

                    }
                }

            } else {//якщо не переслане повідомлення і не текст

                if (data.getCheckLocation()) {//локація, дата //message.hasLocation()
                    if (message.hasLocation()) {
                        data.setCheckLocation(false);
                        String longitude = String.valueOf(message.getLocation().getLongitude());//Довгота
                        String latitude = String.valueOf(message.getLocation().getLatitude());  //Широта

                        DateTime dateTimeNow = DateTime.now();
                        // Формування рядка з дати та часу в форматі ISO 8601
                        String dateTimeFormatted = dateTimeNow.toString("dd-MM-yyyy HH:mm");
                        // Формування рядка з дати та часу в налаштованому форматі

                        String url = "https://www.google.com/maps/?q=" + latitude + "," + longitude + "&z=15";
                        //створення посилання на гугл карту з довготи та широти

                        text = "Локацію отримано \n"; //+ url + "\nДата та час: " + dateTimeFormatted;//створення повідомлення для виводу
                        text = text + "\nВибери пункт з наявних";

                        if (data.getCheckReplacement() == false) {//якщо прізвище не міняв
                            sendMessage.enableMarkdown(true);//кнопки меню(Зміна прізвища, вказати наявність)
                            keyboardMarkup = replyKeyboardMarkupOll.getMenuKeyboardMenu();
                            sendMessage.setReplyMarkup(keyboardMarkup);
                        } else {//якщо міняв прізвище
                            sendMessage.enableMarkdown(true);//кнопки меню(Вказати наявність)
                            keyboardMarkup = replyKeyboardMarkupOll.getMenuKeyboardMenu2();
                            sendMessage.setReplyMarkup(keyboardMarkup);
                        }
                        sendMessage.setText(text);
                        data.setLocation(url);
                        data.setDate(dateTimeFormatted);
                    }else {
                        text = "Мені треба твоя локація, а не щось інше!(/start)";
                        sendMessage.setText(text);

                    }
                } else if (data.getCheckNumber()) {//номер телефону
                    if (message.hasContact()) {
                        data.setCheckNumber(false);
                        text = "Номер отримано\n";
                        text = text + "\nВиберіть пункт з наявних";

                        if (data.getCheckReplacement() == false) {//якщо прізвище не міняв
                            sendMessage.enableMarkdown(true);//кнопки меню(Зміна прізвища, вказати наявність)
                            keyboardMarkup = replyKeyboardMarkupOll.getMenuKeyboardMenu();
                            sendMessage.setReplyMarkup(keyboardMarkup);
                        } else {//якщо міняв прізвище
                            sendMessage.enableMarkdown(true);//кнопки меню(Вказати наявність)
                            keyboardMarkup = replyKeyboardMarkupOll.getMenuKeyboardMenu2();
                            sendMessage.setReplyMarkup(keyboardMarkup);
                        }

                        sendMessage.setText(text);
                        data.setNumber(contact.getPhoneNumber());
                    }else {
                        text = "Мені потрібен твій номер, а не щось інше!(/start)";
                        sendMessage.setText(text);

                    }
                } else {
                    int a = (int) (Math.random() * 4);//рандомне число [0;4)

                    if (a == 1) {
                        text = "Це якась GIF чи стікер? \nНажаль я не можу їх побачити 🫤(/start)";
                    } else if (a == 2) {
                        text = "Для чого записуєш голосове? \nЯ не маю вух.😅 Чи це не голосове?🤨(/start)";
                    } else {
                        text = "ВАУ! Гарне відео повідомлення.😍 \nЖаль що я його не можу побачити😅(/start)";
                    }

                    sendMessage.setText(text);
                }

            }
            data.setChatId(String.valueOf(update.getMessage().getChatId()));////запам'ятовує id користувача
            // щоб далі перевіряти пише він чи інший користувач

        } try {
            execute(sendMessage);//ретурнить метод і відправляє повідомлення

        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }


    }
}



