package ua.axel.quiz.state;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.axel.quiz.Facade;
import ua.axel.quiz.service.SendMessageService;

import java.io.Serializable;
import java.util.Optional;

@Component
public class MainState implements State {

	@Autowired
	private SendMessageService sendMessageService;

	@Override
	public Optional<BotApiMethod<? extends Serializable>> handle(Facade facade, Update update) {
		if (update.hasMessage() && update.getMessage().hasText()) {
			var message = update.getMessage();
			var chatId = message.getChatId().toString();
			if ("mirror".equals(message.getText())) {
				var userId = message.getFrom().getId();
				facade.setUserState(userId, "mirrorState");
				return Optional.of(sendMessageService.getSendMessage(chatId, "[switching to Mirror State]"));
			}
			return Optional.of(sendMessageService.getSendMessage(chatId, "[Main] " + message.getText()));
		}
		return Optional.empty();
	}
}
