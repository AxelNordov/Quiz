package ua.axel.quiz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.axel.quiz.entity.Quiz;
import ua.axel.quiz.repository.QuizRepository;

@Service
public class QuizService {
	@Autowired
	private QuizRepository quizRepository;

	public Quiz findById(Long id) {
		return quizRepository.findById(id).orElseThrow();
	}

	public void save(Quiz quiz) {
		quizRepository.save(quiz);
	}
}
