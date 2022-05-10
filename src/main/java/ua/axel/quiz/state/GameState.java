package ua.axel.quiz.state;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.axel.quiz.Facade;
import ua.axel.quiz.service.KeyboardService;
import ua.axel.quiz.service.LocaleMessageService;
import ua.axel.quiz.service.SendMessageService;

import java.util.Optional;

@Component
public class GameState implements State {
	@Autowired
	private SendMessageService sendMessageService;
	@Autowired
	private KeyboardService keyboardService;
	@Autowired
	private LocaleMessageService localeMessageService;

	@Override
	public Optional<BotApiMethod<Message>> start(Update update) {
		var chatId = update.getMessage().getChatId().toString();
		var sendMessage = sendMessageService.getSendMessage(chatId,
				String.format(localeMessageService.getMessage(
						"message.you-are-in", localeMessageService.getMessage("menu.game-button.name"))));
		sendMessage.enableMarkdown(true);
		sendMessage.setReplyMarkup(keyboardService.getMainMenuKeyboard(localeMessageService.getMessage("menu.main-button.name")));
		return Optional.of(sendMessage);
	}

	@Override
	public Optional<BotApiMethod<Message>> handle(Facade facade, Update update) {
		var message = update.getMessage();
		long userId = message.getFrom().getId();
		if (message.hasText() && message.getText().equals(localeMessageService.getMessage("menu.main-button.name"))) {
			facade.setUserState(userId, States.MAIN_STATE);
			return facade.getState(States.MAIN_STATE).start(update);
		}
		return Optional.empty();
	}
}
