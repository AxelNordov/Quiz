package ua.axel.quiz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import ua.axel.quiz.repository.QuizRepository;
import ua.axel.quiz.util.SendPollUtil;

import java.util.Optional;

@Service
public class QuizService {
	@Autowired
	private QuizRepository quizRepository;
	@Autowired
	private UserService userService;

	public Optional<BotApiMethod<Message>> getSendPool(Message message) {
		var chatId = message.getChatId().toString();
		long userId = message.getFrom().getId();
		var quiz = quizRepository.findByAuthorRandomQuizWithRightAnswer(
				userService.findById(userId).getAuthor().getId());
		var sendPoll = SendPollUtil.getSendPollFromQuiz(chatId, quiz);
		return Optional.of(sendPoll);
	}

}
