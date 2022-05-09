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
public class MirrorState implements State {

	@Autowired
	private SendMessageService sendMessageService;

	@Autowired
	private KeyboardService keyboardService;

	@Override
	public Optional<BotApiMethod<? extends Serializable>> start(Update update) {
		var chatId = update.getMessage().getChatId().toString();
		SendMessage sendMessage = sendMessageService.getSendMessage(chatId, String.format("U R in %s now.", Constants.MIRROR_BUTTON));
		sendMessage.enableMarkdown(true);
		sendMessage.setReplyMarkup(keyboardService.getMainMenuKeyboard(Constants.MAIN_BUTTON));
		return Optional.of(sendMessage);
	}

	@Override
	public Optional<BotApiMethod<? extends Serializable>> handle(Facade facade, Update update) {
		var message = update.getMessage();
		long userId = message.getFrom().getId();
		if (Constants.MAIN_STATE.equals(message.getText().toLowerCase(Locale.ROOT))) {
			facade.setUserState(userId, Constants.MAIN_STATE);
			return facade.getState(Constants.MAIN_STATE).start(update);
		}
		var chatId = message.getChatId().toString();
		return Optional.of(sendMessageService.getSendMessage(chatId, String.format("[%s] ", Constants.MAIN_STATE) + message.getText()));
	}

	@Override
	public Optional<BotApiMethod<? extends Serializable>> finish() {
		return Optional.empty();
	}
}
