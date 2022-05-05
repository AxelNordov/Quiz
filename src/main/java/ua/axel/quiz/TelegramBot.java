package ua.axel.quiz;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@Getter
public class TelegramBot extends TelegramLongPollingBot {

	@Value("${telegram.bot-username}")
	private String botUsername;

	@Value("${telegram.bot-token}")
	private String botToken;

	@Override
	public void onUpdateReceived(Update update) {
		if (update.hasMessage() && update.getMessage().hasText()) {
			SendMessage message = new SendMessage();
			message.setChatId(update.getMessage().getChatId().toString());
			message.setText(update.getMessage().getText());

			try {
				execute(message);
			} catch (TelegramApiException e) {
				e.printStackTrace();
			}
		}
	}
}
