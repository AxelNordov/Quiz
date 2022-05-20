package ua.axel.quiz.state;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import ua.axel.quiz.util.SendMessageUtil;

import java.util.List;
import java.util.Optional;

@Component
public class MainState extends State {

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
		var chatId = message.getChatId().toString();
		var text = message.getText();
		if (text.equals(localeMessageService.getMessage("menu.settings-button.name"))) {
			return changeState(userId, chatId, StateName.SETTINGS_STATE);
		} else if (text.equals(localeMessageService.getMessage("menu.game-button.name"))) {
			return changeState(userId, chatId, StateName.GAME_STATE);
		} else {
			return start(chatId);
		}
	}
}
