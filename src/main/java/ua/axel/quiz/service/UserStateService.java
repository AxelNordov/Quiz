package ua.axel.quiz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.axel.quiz.entity.UserState;
import ua.axel.quiz.repository.UserStateRepository;

@Service
public class UserStateService {
	@Autowired
	private UserService userService;
	@Autowired
	private UserStateRepository userStateRepository;

	public UserState findById(Long userId) {
		return userStateRepository.findById(userId).orElse(null);
	}

	public void save(Long userId, String state) {
		userStateRepository.save(UserState.builder().id(userId).state(state).build());
	}

	public void setUserStateName(Long userId, String stateName) {
		userService.saveState(userId, stateName);
		save(userId, stateName);
	}
}
