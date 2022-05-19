package ua.axel.quiz.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import ua.axel.quiz.service.UserStateService;

import java.util.Optional;

import static ua.axel.quiz.state.States.getState;

@Component
public class UpdateContentMessage implements UpdateContent {
	@Autowired
	private UserStateService userStateService;
	private Message message;

	@Override
	public void setContent(BotApiObject botApiObject) {
		this.message = (Message) botApiObject;
	}

	@Override
	public Optional<BotApiMethod<Message>> handle() {
		var userId = message.getFrom().getId();
		if (!userStateService.existsById(userId)) {
			userStateService.setDefaultUserState(userId);
		}
		var currUserState = getState(userStateService.findById(userId).orElseThrow().getState());
		return currUserState.handle(message);
	}
}
