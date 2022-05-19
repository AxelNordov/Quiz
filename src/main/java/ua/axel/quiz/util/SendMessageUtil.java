package ua.axel.quiz.util;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

public class SendMessageUtil {
	private SendMessageUtil() {
		throw new IllegalStateException("Utility class");
	}

	public static SendMessage getSendMessage(String chatId, String text) {
		return SendMessage.builder()
				.chatId(chatId)
				.text(text)
				.build();
	}

	public static SendMessage getSendMessageWithMainMenuKeyboard(String chatId, String text, List<String> bottonList) {
		SendMessage sendMessage = getSendMessage(chatId, text);
		sendMessage.setReplyMarkup(KeyboardUtil.getMainMenuKeyboard(bottonList));
		return sendMessage;
	}
}
