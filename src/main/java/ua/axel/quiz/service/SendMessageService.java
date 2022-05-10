package ua.axel.quiz.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
public class SendMessageService {
	public SendMessage getSendMessage(String chatId, String text) {
		return SendMessage.builder()
				.chatId(chatId)
				.text(text)
				.build();
	}
}
