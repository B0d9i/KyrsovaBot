package Najavnist.java.bot.service.Buttons;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReplyKeyboardMarkupOll {
    private ReplyKeyboardMarkup MenuKeyboardMenu() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        List<KeyboardRow> keyboardRowsMenu = new ArrayList<>();
        KeyboardRow keyboardRowMenu = new KeyboardRow();
        keyboardRowMenu.add("Змінити прізвище");
        keyboardRowMenu.add("Вказати наявність");
        keyboardRowsMenu.add(keyboardRowMenu);

        replyKeyboardMarkup.setKeyboard(keyboardRowsMenu);//створили кнопки меню
        return replyKeyboardMarkup;
    }
    private ReplyKeyboardMarkup MenuKeyboardMenu2() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        List<KeyboardRow> keyboardRowsMenu = new ArrayList<>();
        KeyboardRow keyboardRowMenu = new KeyboardRow();
        keyboardRowMenu.add("Вказати наявність");
        keyboardRowsMenu.add(keyboardRowMenu);

        replyKeyboardMarkup.setKeyboard(keyboardRowsMenu);//створили кнопки меню
        return replyKeyboardMarkup;
    }
    private ReplyKeyboardMarkup MenuKeyboardPresence() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        List<KeyboardRow> keyboardRowsPresence = new ArrayList<>();
        KeyboardRow keyboardRowPresence1 = new KeyboardRow();
         KeyboardRow keyboardRowPresence2 = new KeyboardRow();
        keyboardRowPresence1.add("Наявний");
        keyboardRowPresence1.add("За межами");
         keyboardRowPresence2.add("Відпустка");
         keyboardRowPresence2.add("Звільнення");

        keyboardRowsPresence.add(keyboardRowPresence1);
         keyboardRowsPresence.add(keyboardRowPresence2);

        replyKeyboardMarkup.setKeyboard(keyboardRowsPresence);//створили кнопки
        return replyKeyboardMarkup;
    }
    private ReplyKeyboardMarkup MenuKeyboardPhone() {//номер
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);


        KeyboardRow row = new KeyboardRow();// Створюємо новий рядок клавіатури
        row.add(KeyboardButton.builder()// Додаємо кнопку, яка запитує контакт користувача
                .text("Поділитися своїм номером")//текст на кнопці
                .requestContact(true)
                .build());

        replyKeyboardMarkup.setKeyboard(Collections.singletonList(row));//створили кнопки
        return replyKeyboardMarkup;
    }
    private ReplyKeyboardMarkup MenuKeyboardLocation() {//номер
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);


        KeyboardRow row = new KeyboardRow();// Створюємо новий рядок клавіатури
        row.add(KeyboardButton.builder()// Додаємо кнопку, яка запитує контакт користувача
                .text("Поділитися локацією")//текст на кнопці
                .requestLocation(true)
                .build());

        replyKeyboardMarkup.setKeyboard(Collections.singletonList(row));//створили кнопки
        return replyKeyboardMarkup;
    }

    private ReplyKeyboardMarkup MenuKeyboardNULL() {//пусті кнопки
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        List<KeyboardRow> keyboardRowsPresence = new ArrayList<>();
        KeyboardRow keyboardRowPresence1 = new KeyboardRow();
        keyboardRowPresence1.add(" ");

        keyboardRowsPresence.add(keyboardRowPresence1);
        replyKeyboardMarkup.setKeyboard(keyboardRowsPresence);

        return replyKeyboardMarkup;

    }

    public ReplyKeyboardMarkup getMenuKeyboardNULL() {return MenuKeyboardNULL();}
    public ReplyKeyboardMarkup getMenuKeyboardMenu() {return MenuKeyboardMenu();}
    public ReplyKeyboardMarkup getMenuKeyboardMenu2() {return MenuKeyboardMenu2();}
    public ReplyKeyboardMarkup getMenuKeyboardPresence() {return MenuKeyboardPresence();}
    public ReplyKeyboardMarkup getMenuKeyboardPhone() {
        return MenuKeyboardPhone();
    }
    public ReplyKeyboardMarkup getMenuKeyboardLocation() {
        return MenuKeyboardLocation();
    }


}
