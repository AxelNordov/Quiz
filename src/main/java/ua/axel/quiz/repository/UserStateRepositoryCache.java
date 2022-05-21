package ua.axel.quiz.repository;

import org.springframework.stereotype.Repository;
import ua.axel.quiz.entity.UserState;
import ua.axel.quiz.state.States;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserStateRepositoryCache implements UserStateRepository {
	private final Map<Long, States.Name> userStateMap = new HashMap<>();

	public Optional<UserState> findById(Long userId) {
		return Optional.ofNullable(userStateMap.get(userId))
				.map(state -> UserState.builder().id(userId).state(state).build());
	}

	@Override
	public boolean existsById(Long userId) {
		return userStateMap.containsKey(userId);
	}

	@Override
	public UserState save(UserState userState) {
		userStateMap.put(userState.getId(), userState.getState());
		return userState;
	}
}
