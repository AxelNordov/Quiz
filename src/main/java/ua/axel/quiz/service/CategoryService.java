package ua.axel.quiz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.axel.quiz.entity.Category;
import ua.axel.quiz.repository.CategoryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
	@Autowired
	private UserService userService;
	@Autowired
	private CategoryRepository categoryRepository;

	public Category findById(Long id) {
		return categoryRepository.findById(id).orElseThrow();
	}

	public void save(Category quiz) {
		categoryRepository.save(quiz);
	}

	public Category findByTitle(String categoryTitle) {
		return categoryRepository.findByTitle(categoryTitle).orElseThrow();
	}

	public List<Category> findAll() {
		return categoryRepository.findAll();
	}

	public List<String> getAllCategoriesTitles() {
		return findAll().stream().map(Category::getTitle).collect(Collectors.toList());
	}

	public void setUserCategory(Long userId, String categoryTitle) {
		userService.setUserCategory(userId, findByTitle(categoryTitle));
	}
}
