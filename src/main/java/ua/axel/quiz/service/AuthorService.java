package ua.axel.quiz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.axel.quiz.entity.Author;
import ua.axel.quiz.entity.Category;
import ua.axel.quiz.repository.AuthorRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorService {
	@Autowired
	private AuthorRepository authorRepository;

	public Author findByTitle(String authorTitle) {
		return authorRepository.findByTitle(authorTitle)
				.orElseThrow();
	}

	public List<String> getAllHasQuizzesWithRightAnswerTitlesByCategory(Category category) {
		return authorRepository.findAllHasQuizzesWithRightAnswerByCategory(category).stream()
				.map(Author::getTitle)
				.sorted()
				.collect(Collectors.toList());
	}

}
