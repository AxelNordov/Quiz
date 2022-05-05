package ua.axel.quiz;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.axel.quiz.state.MainState;
import ua.axel.quiz.state.State;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class Facade {

	private final Map<Long, State> userStates = new HashMap<>();

	public Optional<BotApiMethod<? extends Serializable>> handle(Update update) {
		long userId = update.getMessage().getFrom().getId();

		if (!userStates.containsKey(userId)) {
			setUserState(userId, new MainState(this));
		}
		var currUserState = getUserState(userId);

		return currUserState.handle(update);
	}

	public void setUserState(long userId, State state) {
		userStates.put(userId, state);
	}

	public State getUserState(long userId) {
		return userStates.get(userId);
	}
}
