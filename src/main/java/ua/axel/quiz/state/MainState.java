package ua.axel.quiz.state;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import ua.axel.quiz.service.LocaleMessageService;
import ua.axel.quiz.service.UserStateService;
import ua.axel.quiz.util.SendMessageUtil;

import java.util.List;
import java.util.Optional;

@Component
public class MainState implements State {
	@Autowired
	private UserStateService userStateService;
	@Autowired
	private LocaleMessageService localeMessageService;

	@Override
	public Optional<BotApiMethod<Message>> start(String chatId) {
		var sendMessage = SendMessageUtil.getSendMessageWithMainMenuKeyboard(chatId,
				localeMessageService.getMessage(
						"message.you-are-in", localeMessageService.getMessage("menu.main-button.name")),
				List.of(localeMessageService.getMessage("menu.settings-button.name"),
						localeMessageService.getMessage("menu.game-button.name")));
		return Optional.of(sendMessage);
	}

	@Override
	public Optional<BotApiMethod<Message>> handle(Message message) {
		var userId = message.getFrom().getId();
		var text = message.getText();
		var chatId = message.getChatId().toString();
		if (text.equals(localeMessageService.getMessage("menu.settings-button.name"))) {
			var userState = userStateService.setUserState(userId, StateName.SETTINGS_STATE);
			return States.getState(userState.getState()).start(chatId);
		} else if (text.equals(localeMessageService.getMessage("menu.game-button.name"))) {
			var userState = userStateService.setUserState(userId, StateName.GAME_STATE);
			return States.getState(userState.getState()).start(chatId);
		} else {
			return start(chatId);
		}
	}
}
