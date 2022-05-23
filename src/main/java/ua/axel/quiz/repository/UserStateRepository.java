package ua.axel.quiz.repository;

import ua.axel.quiz.entity.UserState;
import ua.axel.quiz.state.States;

import java.util.Map;
import java.util.Optional;

public interface UserStateRepository {
	Optional<UserState> findById(Long id);

	boolean existsById(Long id);

	UserState save(UserState entity);

	void saveAll(Map<Long, States.Name> map);
}
