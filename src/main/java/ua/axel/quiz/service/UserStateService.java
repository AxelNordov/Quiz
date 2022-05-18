package ua.axel.quiz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.axel.quiz.entity.UserState;
import ua.axel.quiz.repository.UserStateRepository;
import ua.axel.quiz.state.StateName;

import java.util.Optional;

@Service
public class UserStateService {
	@Autowired
	private UserStateRepository userStateRepository;

	public Optional<UserState> findById(Long userId) {
		return userStateRepository.findById(userId);
	}

	public void save(Long userId, StateName state) {
		userStateRepository.save(UserState.builder().id(userId).state(state).build());
	}

	public void setUserState(Long userId, StateName stateName) {
		save(userId, stateName);
	}

	public void setDefaultUserState(Long userId) {
		setUserState(userId, StateName.MAIN_STATE);
	}
}
