package ua.axel.quiz.repository;

import org.springframework.stereotype.Repository;
import ua.axel.quiz.entity.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserRepositoryCache implements UserRepository {
	private final Map<Long, Map<String, String>> userMap = new HashMap<>();

	public Optional<User> findById(Long id) {
		return Optional.ofNullable(userMap.get(id))
				.map(settings -> User.builder().id(id).settings(settings).build());
	}

	@Override
	public User save(User user) {
		userMap.put(user.getId(), user.getSettings());
		return user;
	}
}
