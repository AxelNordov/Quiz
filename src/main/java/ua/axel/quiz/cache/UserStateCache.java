package ua.axel.quiz.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.axel.quiz.state.State;
import ua.axel.quiz.state.States;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserStateCache {

	@Autowired
	private States states;

	private final Map<Long, State> userStateMap = new HashMap<>();

	public State getUserState(long userId) {
		if (!userStateMap.containsKey(userId) || userStateMap.get(userId) == null) {
			setUserState(userId, "mainState");
		}
		return userStateMap.get(userId);
	}

	public void setUserState(long userId, String state) {
		userStateMap.put(userId, states.getState(state));
	}
}
