package ua.axel.quiz.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class KeyboardService {

	public ReplyKeyboardMarkup getMainMenuKeyboard(String... buttonText) {
		final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
		replyKeyboardMarkup.setSelective(true);
		replyKeyboardMarkup.setResizeKeyboard(true);
		replyKeyboardMarkup.setOneTimeKeyboard(false);
		replyKeyboardMarkup.setKeyboard(List.of(
				new KeyboardRow(Arrays.stream(buttonText)
						.map(KeyboardButton::new)
						.collect(Collectors.toList()))));
		return replyKeyboardMarkup;
	}
}
