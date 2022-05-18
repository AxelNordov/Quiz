package ua.axel.quiz.state;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import ua.axel.quiz.service.KeyboardService;
import ua.axel.quiz.service.LocaleMessageService;
import ua.axel.quiz.service.SendMessageService;
import ua.axel.quiz.service.UserStateService;

import java.util.List;
import java.util.Optional;

@Component
public class MainState implements State {
	@Autowired
	private UserStateService userStateService;
	@Autowired
	private SendMessageService sendMessageService;
	@Autowired
	private KeyboardService keyboardService;
	@Autowired
	private LocaleMessageService localeMessageService;

	@Override
	public Optional<BotApiMethod<Message>> start(Message message) {
		var chatId = message.getChatId().toString();
		var sendMessage = sendMessageService.getSendMessage(chatId,
				String.format(localeMessageService.getMessage(
						"message.you-are-in", localeMessageService.getMessage("menu.main-button.name"))));
		sendMessage.setReplyMarkup(keyboardService.getMainMenuKeyboard(List.of(
				localeMessageService.getMessage("menu.settings-button.name"),
				localeMessageService.getMessage("menu.game-button.name"))));
		return Optional.of(sendMessage);
	}

	@Override
	public Optional<BotApiMethod<Message>> handle(Message message) {
		long userId = message.getFrom().getId();
		var text = message.getText();
		if (text.equals(localeMessageService.getMessage("menu.settings-button.name"))) {
			userStateService.setUserState(userId, StateName.SETTINGS_STATE);
			return States.getState(StateName.SETTINGS_STATE).start(message);
		} else if (text.equals(localeMessageService.getMessage("menu.game-button.name"))) {
			userStateService.setUserState(userId, StateName.GAME_STATE);
			return States.getState(StateName.GAME_STATE).start(message);
		} else {
			return start(message);
		}
	}
}
