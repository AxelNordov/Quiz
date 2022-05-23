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
	public List<BotApiMethod<Message>> start(Message message) {
		var chatId = message.getChatId().toString();
		var sendMessage = SendMessageUtil.getSendMessageWithMainMenuKeyboard(chatId,
				localeMessageService.getMessage(
						"message.you-are-in", localeMessageService.getMessage("menu.main-button.name")),
				List.of(localeMessageService.getMessage("menu.settings-button.name"),
						localeMessageService.getMessage("menu.game-button.name")));
		return List.of(sendMessage);
	}

	@Override
	public List<BotApiMethod<Message>> handle(Message message) {
		var botApiMethods = new ArrayList<BotApiMethod<Message>>();
		var text = message.getText();
		if (text.equals(localeMessageService.getMessage("menu.settings-button.name"))) {
			botApiMethods.addAll(changeState(message, States.Name.SETTINGS_STATE));
		} else if (text.equals(localeMessageService.getMessage("menu.game-button.name"))) {
			botApiMethods.addAll(changeState(message, States.Name.GAME_STATE));
		} else {
			botApiMethods.addAll(start(message));
		}
		return botApiMethods;
	}
}
