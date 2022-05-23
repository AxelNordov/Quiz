package ua.axel.quiz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.axel.quiz.entity.Author;
import ua.axel.quiz.entity.Quiz;
import ua.axel.quiz.entity.User;
import ua.axel.quiz.repository.QuizRepository;
import ua.axel.quiz.util.Utils;

import java.util.List;

@Service
public class QuizService {
	@Autowired
	private QuizRepository quizRepository;
	@Autowired
	private UserService userService;

	public Quiz getQuiz(long userId) {
		return userService.findById(userId)
				.map(User::getAuthor)
				.flatMap(author -> quizRepository.findById(
						Utils.getRandom(getIdsWithRightAnswerByAuthors(List.of(author)))))
				.or(() -> quizRepository.findById(
						Utils.getRandom(getIdsWithRightAnswer())))
				.orElseThrow();
	}

	private List<Long> getIdsWithRightAnswer() {
		return quizRepository.findIdsByRightAnswerIsNotNull();
	}

	private List<Long> getIdsWithRightAnswerByAuthors(List<Author> authors) {
		return quizRepository.findIdsByRightAnswerIsNotNullAndAuthorIn(authors);
	}
}
