package ua.axel.quiz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.axel.quiz.entity.Author;
import ua.axel.quiz.entity.Category;
import ua.axel.quiz.entity.User;
import ua.axel.quiz.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	public Optional<User> findById(Long id) {
		return userRepository.findById(id);
	}

	public void setCategory(Long userId, Category category) {
		User user = userRepository.findById(userId)
				.orElseGet(() -> User.builder().id(userId).build());
		user.setCategory(category);
		userRepository.save(user);
	}

	public void setAuthor(Long userId, Author author) {
		User user = userRepository.findById(userId)
				.orElseGet(() -> User.builder().id(userId).build());
		user.setAuthor(author);
		userRepository.save(user);
	}
}
