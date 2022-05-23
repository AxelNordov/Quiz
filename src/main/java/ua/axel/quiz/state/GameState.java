package ua.axel.quiz.state;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import ua.axel.quiz.service.QuizService;
import ua.axel.quiz.util.SendMessageUtil;
import ua.axel.quiz.util.SendPollUtil;

import java.util.ArrayList;
import java.util.List;

@Component
public class GameState extends State {
	@Autowired
	private QuizService quizService;

	@Override
	public List<BotApiMethod<Message>> start(Message message) {
		var chatId = message.getChatId().toString();
		var botApiMethods = new ArrayList<BotApiMethod<Message>>();
		botApiMethods.add(SendMessageUtil.getSendMessageWithMainMenuKeyboard(chatId,
				localeMessageService.getMessage("message.game.greetings"),
				List.of(localeMessageService.getMessage("menu.main-button.name"),
						localeMessageService.getMessage("menu.next-button.name"))));
		var userId = message.getFrom().getId();
		botApiMethods.add(SendPollUtil.getSendPollFromQuiz(chatId, quizService.getQuiz(userId)));
		return botApiMethods;
	}

	@Override
	public List<BotApiMethod<Message>> handle(Message message) {
		var botApiMethods = new ArrayList<BotApiMethod<Message>>();
		var userId = message.getFrom().getId();
		var chatId = message.getChatId().toString();
		var text = message.getText();
		if (text.equals(localeMessageService.getMessage("menu.main-button.name"))) {
			botApiMethods.addAll(changeState(message, States.Name.MAIN_STATE));
		} else if (text.equals(localeMessageService.getMessage("menu.next-button.name"))) {
			var quiz = quizService.getQuiz(userId);
			var sendPoll = SendPollUtil.getSendPollFromQuiz(chatId, quiz);
			botApiMethods.add(sendPoll);
		}
		return botApiMethods;
	}
}
