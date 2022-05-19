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

	private List<Author> findAllByCategory(Category category) {
		return authorRepository.findAllByCategory(category);
	}

	public List<String> getAllTitlesByCategory(Category category) {
		return findAllByCategory(category).stream()
				.map(Author::getTitle)
				.sorted()
				.collect(Collectors.toList());
	}

}
