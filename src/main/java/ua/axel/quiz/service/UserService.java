package ua.axel.quiz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.axel.quiz.entity.Author;
import ua.axel.quiz.entity.Category;
import ua.axel.quiz.entity.User;
import ua.axel.quiz.repository.UserRepository;
import ua.axel.quiz.state.States;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	public Optional<User> findById(Long id) {
		return userRepository.findById(id);
	}

	public void setCategory(Long userId, Category category) {
		var user = findByIdOrNew(userId);
		user.setCategory(category);
		userRepository.save(user);
	}

	public void toggleAuthor(Long userId, Author author) {
		var user = findByIdOrNew(userId);
		var authors = user.getAuthors();
		if (authors.contains(author)) {
			authors.remove(author);
		} else {
			user.getAuthors().add(author);
		}
		userRepository.save(user);
	}

	public void setStateName(Long userId, States.Name stateName) {
		var user = findByIdOrNew(userId);
		user.setStateName(stateName);
		userRepository.save(user);
	}

	private User findByIdOrNew(Long userId) {
		return userRepository.findById(userId)
				.orElseGet(() -> User.builder().id(userId).build());
	}

	public List<User> findAll() {
		return userRepository.findAll();
	}
}
