package ua.axel.quiz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.axel.quiz.cache.UserStateCache;
import ua.axel.quiz.state.State;
import ua.axel.quiz.state.States;

import java.io.Serializable;
import java.util.Optional;

@Component
public class Facade {

	@Autowired
	private States states;

	@Autowired
	private UserStateCache userStateCache;

	public Optional<BotApiMethod<? extends Serializable>> handle(Update update) {
		var userId = update.getMessage().getFrom().getId();
		var currUserState = userStateCache.getUserState(userId);
		return currUserState.handle(this, update);
	}

	public void setUserState(Long userId, String state) {
		userStateCache.setUserState(userId, state);
	}

	public State getState(String state) {
		return states.getState(state);
	}
}
