package ua.axel.quiz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.axel.quiz.entity.UserState;
import ua.axel.quiz.repository.UserStateRepository;
import ua.axel.quiz.state.States;

import java.util.Optional;

@Service
public class UserStateService {
	@Autowired
	private UserStateRepository userStateRepository;

	public Optional<UserState> findById(Long userId) {
		return userStateRepository.findById(userId);
	}

	public boolean existsById(Long userId) {
		return userStateRepository.existsById(userId);
	}

	public UserState save(Long userId, States.Name state) {
		return userStateRepository.save(
				UserState.builder()
						.id(userId)
						.state(state)
						.build());
	}

	public UserState setUserState(Long userId, States.Name name) {
		return save(userId, name);
	}

	public void setDefaultUserState(Long userId) {
		setUserState(userId, States.Name.MAIN_STATE);
	}
}
