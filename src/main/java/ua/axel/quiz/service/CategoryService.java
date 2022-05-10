package ua.axel.quiz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.axel.quiz.entity.Category;
import ua.axel.quiz.repository.CategoryRepository;

import java.util.List;

@Service
public class CategoryService {
	@Autowired
	CategoryRepository categoryRepository;

	public Category findById(Long id) {
		return categoryRepository.findById(id).orElseThrow();
	}

	public void save(Category quiz) {
		categoryRepository.save(quiz);
	}

	public Category findByName(String category) {
		return categoryRepository.findByName(category).orElseThrow();
	}

	public List<Category> findAll() {
		return categoryRepository.findAll();
	}
}
