package ua.axel.quiz.repository;

import ua.axel.quiz.entity.UserState;

import java.util.Optional;

public interface UserStateRepository {
	Optional<UserState> findById(Long id);

	boolean existsById(Long id);

	UserState save(UserState entity);
}
