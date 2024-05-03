package Najavnist.java.bot.Buttons;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReplyKeyboardMarkupOll_Inline {
    private InlineKeyboardMarkup inlineKeyboardMarkup(){
        InlineKeyboardMarkup inlineKeyboardMarkup=new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard=new ArrayList<>();

        keyboard.add(Collections.singletonList(InlineKeyboardButton.builder().text("Служба порятунку ").callbackData("102").build()));
        keyboard.add(Collections.singletonList(InlineKeyboardButton.builder().text("Поліція").callbackData("102").build()));
        keyboard.add(Collections.singletonList(InlineKeyboardButton.builder().text("Швидка медична допомога").callbackData("103").build()));

        inlineKeyboardMarkup.setKeyboard(keyboard);
        return inlineKeyboardMarkup;
    }
    public InlineKeyboardMarkup getInlineKeyboardMarkup() {
        return inlineKeyboardMarkup();
    }
}
