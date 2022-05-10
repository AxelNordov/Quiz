package ua.axel.quiz.repository;

import org.springframework.stereotype.Repository;
import ua.axel.quiz.entity.UserState;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserStateRepositoryCache implements UserStateRepository {
	private final Map<Long, String> userStateMap = new HashMap<>();

	public Optional<UserState> findById(Long id) {
		return Optional.ofNullable(userStateMap.get(id))
				.map(state -> UserState.builder().id(id).state(state).build());
	}

	@Override
	public UserState save(UserState userState) {
		userStateMap.put(userState.getId(), userState.getState());
		return userState;
	}
}
