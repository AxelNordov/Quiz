package ua.axel.quiz.state;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import ua.axel.quiz.util.SendMessageUtil;

import java.util.ArrayList;
import java.util.List;

@Component
public class MainState extends State {

	@Override
	public List<BotApiMethod<Message>> start(String chatId) {
		var sendMessage = SendMessageUtil.getSendMessageWithMainMenuKeyboard(chatId,
				localeMessageService.getMessage(
						"message.you-are-in", localeMessageService.getMessage("menu.main-button.name")),
				List.of(localeMessageService.getMessage("menu.settings-button.name"),
						localeMessageService.getMessage("menu.game-button.name")));
		return List.of(sendMessage);
	}

	@Override
	public List<BotApiMethod<Message>> handle(Message message) {
		var userId = message.getFrom().getId();
		var chatId = message.getChatId().toString();
		var text = message.getText();
		var sendMessages = new ArrayList<BotApiMethod<Message>>();
		if (text.equals(localeMessageService.getMessage("menu.settings-button.name"))) {
			sendMessages.addAll(changeState(userId, chatId, States.Name.SETTINGS_STATE));
		} else if (text.equals(localeMessageService.getMessage("menu.game-button.name"))) {
			sendMessages.addAll(changeState(userId, chatId, States.Name.GAME_STATE));
		} else {
			sendMessages.addAll(start(chatId));
		}
		return sendMessages;
	}
}
