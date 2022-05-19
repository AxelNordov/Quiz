package ua.axel.quiz.util;

import org.telegram.telegrambots.meta.api.methods.polls.SendPoll;
import ua.axel.quiz.entity.Answer;
import ua.axel.quiz.entity.Quiz;

import java.util.Comparator;
import java.util.stream.Collectors;

public class SendPollUtil {
	private static final String QUIZ_TYPE = "quiz";

	private SendPollUtil() {
		throw new IllegalStateException("Utility class");
	}

	public static SendPoll getSendPollFromQuiz(String chatId, Quiz quiz) {
		var options = quiz.getAnswers().stream()
				.sorted(Comparator.comparingInt(Answer::getOrderNumber))
				.map(Answer::getBody)
				.collect(Collectors.toList());
		return SendPoll.builder()
				.chatId(chatId)
				.question(quiz.getQuestion())
				.options(options)
				.correctOptionId(getCorrectOptionId(quiz.getRightAnswer()))
				.type(QUIZ_TYPE)
				.isAnonymous(true)
				.build();
	}

	private static int getCorrectOptionId(Byte rightAnswer) {
		return rightAnswer - 1;
	}
}
