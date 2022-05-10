package ua.axel.quiz;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.Serializable;

@Component
@Getter
public class TelegramBot extends TelegramLongPollingBot {
	@Value("${telegram.bot-username}")
	private String botUsername;
	@Value("${telegram.bot-token}")
	private String botToken;
	@Autowired
	private Facade facade;

	@Override
	public void onUpdateReceived(Update update) {
		facade.handle(update).ifPresent(this::executeMethod);
	}

	private void executeMethod(BotApiMethod<? extends Serializable> method) {
		try {
			execute(method);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
}
