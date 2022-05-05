package ua.axel.quiz;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.Serializable;
import java.util.Optional;

@Component
@Getter
public class TelegramBot extends TelegramLongPollingBot {

	@Value("${telegram.bot-username}")
	private String botUsername;

	@Value("${telegram.bot-token}")
	private String botToken;

	@Override
	public void onUpdateReceived(Update update) {
		getMethod(update).ifPresent(this::executeMethod);
	}

	private Optional<BotApiMethod<? extends Serializable>> getMethod(Update update) {
		if (update.hasMessage() && update.getMessage().hasText()) {
			SendMessage message = new SendMessage();
			message.setChatId(update.getMessage().getChatId().toString());
			message.setText(update.getMessage().getText());
			return Optional.of(message);
		}
		return Optional.empty();
	}

	private void executeMethod(BotApiMethod<? extends Serializable> method) {
		try {
			execute(method);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
}
