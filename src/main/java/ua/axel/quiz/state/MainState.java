package ua.axel.quiz.state;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.axel.quiz.Constants;
import ua.axel.quiz.Facade;
import ua.axel.quiz.service.KeyboardService;
import ua.axel.quiz.service.SendMessageService;

import java.io.Serializable;
import java.util.Locale;
import java.util.Optional;

@Component
public class MainState implements State {

	@Autowired
	private SendMessageService sendMessageService;

	@Autowired
	private KeyboardService keyboardService;

	@Override
	public Optional<BotApiMethod<? extends Serializable>> start(Update update) {
		var message = update.getMessage();
		var chatId = message.getChatId().toString();
		SendMessage sendMessage = sendMessageService.getSendMessage(chatId, String.format("U R in %s now.", Constants.MAIN_BUTTON));
		sendMessage.enableMarkdown(true);
		sendMessage.setReplyMarkup(keyboardService.getMainMenuKeyboard(
				Constants.SETTINGS_BUTTON, Constants.GAME_BUTTON, Constants.MIRROR_BUTTON));
		return Optional.of(sendMessage);
	}

	@Override
	public Optional<BotApiMethod<? extends Serializable>> handle(Facade facade, Update update) {
		if (update.hasMessage() && update.getMessage().hasText()) {
			var message = update.getMessage();
			long userId = message.getFrom().getId();
			switch (message.getText().toLowerCase(Locale.ROOT)) {
				case Constants.START:
				case Constants.MAIN_STATE:
					return start(update);
				case Constants.SETTINGS_STATE:
					facade.setUserState(userId, Constants.SETTINGS_STATE);
					return facade.getState(Constants.SETTINGS_STATE).start(update);
				case Constants.GAME_STATE:
					facade.setUserState(userId, Constants.GAME_STATE);
					return facade.getState(Constants.GAME_STATE).start(update);
				case Constants.MIRROR_STATE:
					facade.setUserState(userId, Constants.MIRROR_STATE);
					return facade.getState(Constants.MIRROR_STATE).start(update);
			}
		}
		return Optional.empty();
	}

	@Override
	public Optional<BotApiMethod<? extends Serializable>> finish() {
		return Optional.empty();
	}
}
