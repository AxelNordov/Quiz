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
	private UserService userService;
	@Autowired
	private AuthorRepository authorRepository;

	public Author findById(Long id) {
		return authorRepository.findById(id).orElseThrow();
	}

	public void save(Author quiz) {
		authorRepository.save(quiz);
	}

	public Author findByTitle(String authorTitle) {
		return authorRepository.findByTitle(authorTitle).orElseThrow();
	}

	public List<Author> findAll() {
		return authorRepository.findAll();
	}

	public List<Author> findAllByCategory(Category category) {
		return authorRepository.findAllByCategory(category);
	}

	public List<String> getAllAuthorsTitlesByCategory(Category category) {
		return findAllByCategory(category).stream().map(Author::getTitle).sorted().collect(Collectors.toList());
	}

	public void setUserCategory(Long userId, String authorTitle) {
		userService.setAuthor(userId, findByTitle(authorTitle));
	}

}
