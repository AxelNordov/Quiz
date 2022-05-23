package ua.axel.quiz.state;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import ua.axel.quiz.service.QuizService;
import ua.axel.quiz.util.SendMessageUtil;

import java.util.List;
import java.util.Optional;

@Component
public class GameState extends State {
	@Autowired
	private QuizService quizService;

	@Override
	public Optional<BotApiMethod<Message>> start(String chatId) {
		var sendMessage = SendMessageUtil.getSendMessageWithMainMenuKeyboard(chatId,
				localeMessageService.getMessage(
						"message.you-are-in", localeMessageService.getMessage("menu.game-button.name")),
				List.of(localeMessageService.getMessage("menu.main-button.name"),
						localeMessageService.getMessage("menu.next-button.name")));
		return Optional.of(sendMessage);
	}

	@Override
	public Optional<BotApiMethod<Message>> handle(Message message) {
		var userId = message.getFrom().getId();
		var chatId = message.getChatId().toString();
		var text = message.getText();
		Optional<BotApiMethod<Message>> sendMessage = Optional.empty();
		if (text.equals(localeMessageService.getMessage("menu.main-button.name"))) {
			sendMessage = changeState(userId, chatId, States.Name.MAIN_STATE);
		} else if (text.equals(localeMessageService.getMessage("menu.next-button.name"))) {
			sendMessage = quizService.getSendPool(message);
		}
		return sendMessage;
	}
}
