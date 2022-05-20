package ua.axel.quiz.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import ua.axel.quiz.service.UserStateService;
import ua.axel.quiz.state.State;
import ua.axel.quiz.state.States;

import java.util.Optional;

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
		return getUserState(userId).handle(message);
	}

	private State getUserState(Long userId) {
		if (!userStateService.existsById(userId)) {
			userStateService.setDefaultUserState(userId);
		}
		return States.getState(
				userStateService.findById(userId)
						.orElseThrow()
						.getState());
	}
}
