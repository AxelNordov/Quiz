package ua.axel.quiz.state;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import ua.axel.quiz.service.LocaleMessageService;
import ua.axel.quiz.service.QuizService;
import ua.axel.quiz.service.UserStateService;
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
		if (text.equals(localeMessageService.getMessage("menu.main-button.name"))) {
			return changeState(userId, chatId, StateName.MAIN_STATE);
		} else if (text.equals(localeMessageService.getMessage("menu.next-button.name"))) {
			return quizService.getSendPool(message);
		}
		return Optional.empty();
	}
}
