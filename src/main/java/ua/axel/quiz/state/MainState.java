package ua.axel.quiz.state;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.axel.quiz.Constants;
import ua.axel.quiz.Facade;
import ua.axel.quiz.service.KeyboardService;
import ua.axel.quiz.service.LocaleMessageService;
import ua.axel.quiz.service.SendMessageService;
import ua.axel.quiz.service.UserStateService;

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
	public Optional<BotApiMethod<Message>> start(Update update) {
		var message = update.getMessage();
		var chatId = message.getChatId().toString();
		var sendMessage = sendMessageService.getSendMessage(chatId,
				String.format(localeMessageService.getMessage(
						"message.you-are-in", localeMessageService.getMessage("menu.main-button.name"))));
		sendMessage.enableMarkdown(true);
		sendMessage.setReplyMarkup(keyboardService.getMainMenuKeyboard(
				localeMessageService.getMessage("menu.settings-button.name"),
				localeMessageService.getMessage("menu.game-button.name")));
		return Optional.of(sendMessage);
	}

	@Override
	public Optional<BotApiMethod<Message>> handle(Facade facade, Update update) {
		if (update.hasMessage() && update.getMessage().hasText()) {
			var message = update.getMessage();
			long userId = message.getFrom().getId();
			var text = message.getText();
			if (text.equals(localeMessageService.getMessage("menu.main-button.name"))
					|| text.equals(Constants.START_COMMAND)) {
				return start(update);
			} else if (text.equals(localeMessageService.getMessage("menu.settings-button.name"))) {
				userStateService.setUserStateName(userId, States.SETTINGS_STATE);
				return facade.getState(States.SETTINGS_STATE).start(update);
			} else if (text.equals(localeMessageService.getMessage("menu.game-button.name"))) {
				userStateService.setUserStateName(userId, States.GAME_STATE);
				return facade.getState(States.GAME_STATE).start(update);
			}
		}
		return Optional.empty();
	}
}
