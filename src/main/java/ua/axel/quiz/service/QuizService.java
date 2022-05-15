package ua.axel.quiz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.polls.SendPoll;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.axel.quiz.entity.Answer;
import ua.axel.quiz.entity.Quiz;
import ua.axel.quiz.repository.QuizRepository;

import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuizService {
	private static final String QUIZ_TYPE = "quiz";
	@Autowired
	private QuizRepository quizRepository;

	public Quiz findById(Long id) {
		return quizRepository.findById(id).orElseThrow();
	}

	public void save(Quiz quiz) {
		quizRepository.save(quiz);
	}

	public Optional<BotApiMethod<Message>> getSendPool(Update update) {
		var message = update.getMessage();
		var chatId = message.getChatId().toString();
		var quiz = quizRepository.findRandomQuizWithRightAnswer();
		var sendPoll = getSendPollFromQuiz(chatId, quiz);
		return Optional.of(sendPoll);
	}

	private SendPoll getSendPollFromQuiz(String chatId, Quiz quiz) {
		var options = quiz.getAnswers().stream()
				.sorted(Comparator.comparingInt(Answer::getOrderNumber))
				.map(Answer::getBody)
				.collect(Collectors.toList());
		return SendPoll.builder()
				.chatId(chatId)
				.options(options)
				.question(quiz.getQuestion())
				.type(QUIZ_TYPE)
				.correctOptionId(getCorrectOptionId(quiz.getRightAnswer()))
				.build();
	}

	private int getCorrectOptionId(Byte rightAnswer) {
		return rightAnswer - 1;
	}
}
