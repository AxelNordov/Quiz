package ua.axel.quiz.repository;

import ua.axel.quiz.entity.User;

import java.util.Optional;

public interface UserRepository {
	Optional<User> findById(Long id);

	User save(User entity);
}
