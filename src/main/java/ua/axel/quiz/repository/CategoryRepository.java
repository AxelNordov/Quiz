package ua.axel.quiz.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.axel.quiz.entity.Category;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {
	Optional<Category> findByName(String name);

	List<Category> findAll();
}
