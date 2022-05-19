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
	private CategoryRepository categoryRepository;

	public Category findByTitle(String title) {
		return categoryRepository.findByTitle(title)
				.orElseThrow();
	}

	public List<Category> findAll() {
		return categoryRepository.findAll();
	}

	public List<String> getAllTitles() {
		return findAll().stream()
				.map(Category::getTitle)
				.collect(Collectors.toList());
	}

}
