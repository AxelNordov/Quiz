package ua.axel.quiz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.axel.quiz.entity.User;
import ua.axel.quiz.repository.UserRepository;

import java.util.Map;

@Service
public class UserService {
	@Autowired
	UserRepository userRepository;

	public User findById(Long userId) {
		return userRepository.findById(userId).orElseThrow();
	}

	public void save(Long userId, Map<String, String> settings) {
		userRepository.save(User.builder().id(userId).settings(settings).build());
	}
}
