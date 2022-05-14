package ua.axel.quiz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.axel.quiz.entity.UserState;
import ua.axel.quiz.service.UserStateService;
import ua.axel.quiz.state.State;
import ua.axel.quiz.state.States;

import java.util.Optional;

@Component
public class Facade {
	@Autowired
	private UserStateService userStateService;
	@Autowired
	private States states;

	public Optional<BotApiMethod<Message>> handle(Update update) {
		var userId = update.getMessage().getFrom().getId();
		UserState userState = userStateService.findById(userId);
		if (userState == null) {
			userStateService.setUserStateName(userId, States.MAIN_STATE);
			return states.getState(States.MAIN_STATE).start(update);
		}
		var state = userState.getState();
		var currUserState = getState(state);
		return currUserState.handle(this, update);
	}

	public State getState(String state) {
		return states.getState(state);
	}
}
