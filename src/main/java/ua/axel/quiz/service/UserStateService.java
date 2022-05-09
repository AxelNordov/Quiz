package ua.axel.quiz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.axel.quiz.Constants;
import ua.axel.quiz.entity.UserState;
import ua.axel.quiz.repository.UserStateRepository;

@Service
public class UserStateService {

	@Autowired
	UserStateRepository userStateRepository;

	public UserState findById(Long userId) {
		return userStateRepository.findById(userId)
				.orElseGet(() -> userStateRepository.save(
						UserState.builder().id(userId).state(Constants.MAIN_STATE).build()));
	}

	public void save(Long userId, String state) {
		userStateRepository.save(UserState.builder().id(userId).state(state).build());
	}
}
