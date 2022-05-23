package ua.axel.quiz.state;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import ua.axel.quiz.service.LocaleMessageService;
import ua.axel.quiz.service.UserService;
import ua.axel.quiz.service.UserStateService;

import java.util.Optional;

public abstract class State {
	@Autowired
	protected LocaleMessageService localeMessageService;
	@Autowired
	private UserStateService userStateService;
	@Autowired
	private UserService userService;

	public abstract Optional<BotApiMethod<Message>> start(String chatId);

	public abstract Optional<BotApiMethod<Message>> handle(Message message);

	protected Optional<BotApiMethod<Message>> changeState(Long userId, String chatId, States.Name stateName) {
		userService.setStateName(userId, stateName);
		var userState = userStateService.setUserState(userId, stateName);
		return States.getState(userState.getState()).start(chatId);
	}
}
