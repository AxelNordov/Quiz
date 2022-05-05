package ua.axel.quiz;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;
import java.util.Optional;

@Component
public class Facade {

	public Optional<BotApiMethod<? extends Serializable>> getMethod(Update update) {
		if (update.hasMessage() && update.getMessage().hasText()) {
			SendMessage message = new SendMessage();
			message.setChatId(update.getMessage().getChatId().toString());
			message.setText(update.getMessage().getText());
			return Optional.of(message);
		}
		return Optional.empty();
	}
}
