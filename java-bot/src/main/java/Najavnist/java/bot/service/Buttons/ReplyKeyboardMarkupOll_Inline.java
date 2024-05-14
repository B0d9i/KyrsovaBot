package Najavnist.java.bot.service.Buttons;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReplyKeyboardMarkupOll_Inline {
    private InlineKeyboardMarkup inlineKeyboardMarkup(){
        InlineKeyboardMarkup inlineKeyboardMarkup=new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard=new ArrayList<>();

        keyboard.add(Collections.singletonList(InlineKeyboardButton.builder().text("Так").callbackData("yes").build()));//Радий, що сподобався
        keyboard.add(Collections.singletonList(InlineKeyboardButton.builder().text("Ні").callbackData("no").build()));//Guten Tag

        inlineKeyboardMarkup.setKeyboard(keyboard);
        return inlineKeyboardMarkup;
    }
    public InlineKeyboardMarkup getInlineKeyboardMarkup() {
        return inlineKeyboardMarkup();
    }
}
