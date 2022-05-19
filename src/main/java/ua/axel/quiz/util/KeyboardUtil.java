package ua.axel.quiz.util;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class KeyboardUtil {

	public static final int MAX_BUTTONS_NUMBER_IN_ROW = 3;

	private KeyboardUtil() {
		throw new IllegalStateException("Utility class");
	}

	public static ReplyKeyboardMarkup getMainMenuKeyboard(List<String> buttonText) {
		final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
		replyKeyboardMarkup.setSelective(true);
		replyKeyboardMarkup.setResizeKeyboard(true);
		replyKeyboardMarkup.setOneTimeKeyboard(false);
		List<KeyboardRow> keyboard = new ArrayList<>();
		List<KeyboardButton> buttons = buttonText.stream()
				.map(KeyboardButton::new)
				.collect(Collectors.toList());
		// Distribute the number of buttons relatively evenly.
		// Yes, I know it needs to be improved. But it works.
		int maxButtonsNumberInRow = MAX_BUTTONS_NUMBER_IN_ROW;
		for (int i = 0; i < buttons.size(); i += maxButtonsNumberInRow) {
			if (buttons.size() - i > maxButtonsNumberInRow && buttons.size() - i < maxButtonsNumberInRow * 1.5) {
				maxButtonsNumberInRow--;
			}
			keyboard.add(new KeyboardRow(
					buttons.subList(i, Math.min(i + maxButtonsNumberInRow, buttonText.size()))));
		}
		replyKeyboardMarkup.setKeyboard(keyboard);
		return replyKeyboardMarkup;
	}
}
