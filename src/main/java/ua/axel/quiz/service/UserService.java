package ua.axel.quiz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.axel.quiz.entity.Category;
import ua.axel.quiz.entity.User;
import ua.axel.quiz.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	public User findById(Long id) {
		return userRepository.findById(id).orElseThrow();
	}

	public void saveCategory(Long id, Category category) {
		User user = userRepository.findById(id)
				.orElseGet(() -> User.builder().id(id).build());
		user.setCategory(category);
		userRepository.save(user);
	}

	public void saveState(Long id, String stateName) {
		User user = userRepository.findById(id)
				.orElseGet(() -> User.builder().id(id).build());
		user.setStateName(stateName);
		userRepository.save(user);
	}

	public void setUserCategory(Long userId, Category category) {
		saveCategory(userId, category);
	}
}
