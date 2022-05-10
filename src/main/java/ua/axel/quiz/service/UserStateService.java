package ua.axel.quiz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.axel.quiz.entity.UserState;
import ua.axel.quiz.repository.UserStateRepository;
import ua.axel.quiz.state.States;

@Service
public class UserStateService {
	@Autowired
	UserStateRepository userStateRepository;

	public UserState findById(Long userId) {
		return userStateRepository.findById(userId)
				.orElseGet(() -> userStateRepository.save(
						UserState.builder().id(userId).state(States.MAIN_STATE).build()));
	}

	public void save(Long userId, String state) {
		userStateRepository.save(UserState.builder().id(userId).state(state).build());
	}
}
