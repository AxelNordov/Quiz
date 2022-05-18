package ua.axel.quiz.state;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import ua.axel.quiz.service.*;

import java.util.List;
import java.util.Optional;

@Component
public class GameState implements State {
	@Autowired
	private UserStateService userStateService;
	@Autowired
	private SendMessageService sendMessageService;
	@Autowired
	private KeyboardService keyboardService;
	@Autowired
	private LocaleMessageService localeMessageService;
	@Autowired
	private QuizService quizService;

	@Override
	public Optional<BotApiMethod<Message>> start(Message message) {
		var chatId = message.getChatId().toString();
		var sendMessage = sendMessageService.getSendMessage(chatId,
				String.format(localeMessageService.getMessage(
						"message.you-are-in", localeMessageService.getMessage("menu.game-button.name"))));
		sendMessage.setReplyMarkup(keyboardService.getMainMenuKeyboard(List.of(
				localeMessageService.getMessage("menu.main-button.name"),
				localeMessageService.getMessage("menu.next-button.name"))));
		return Optional.of(sendMessage);
	}

	@Override
	public Optional<BotApiMethod<Message>> handle(Message message) {
		long userId = message.getFrom().getId();
		if (message.hasText() && message.getText().equals(localeMessageService.getMessage("menu.main-button.name"))) {
			userStateService.setUserState(userId, StateName.MAIN_STATE);
			return States.getState(StateName.MAIN_STATE).start(message);
		} else if (message.hasText() && message.getText().equals(localeMessageService.getMessage("menu.next-button.name"))) {
			return quizService.getSendPool(message);
		}
		return Optional.empty();
	}
}
