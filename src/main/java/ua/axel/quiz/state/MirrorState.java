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
public class MirrorState implements State {

	@Autowired
	private SendMessageService sendMessageService;

	@Override
	public Optional<BotApiMethod<? extends Serializable>> handle(Facade facade, Update update) {
		if (update.hasMessage() && update.getMessage().hasText()) {
			var message = update.getMessage();
			var chatId = message.getChatId().toString();
			if ("main".equals(message.getText())) {
				var userId = message.getFrom().getId();
				facade.setUserState(userId, "mainState");
				return Optional.of(sendMessageService.getSendMessage(chatId, "[switching to Main State]"));
			}
			return Optional.of(sendMessageService.getSendMessage(chatId, "[Mirror] " + message.getText()));
		}
		return Optional.empty();
	}

}
