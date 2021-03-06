package ua.axel.quiz.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.axel.quiz.entity.Category;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {
	Optional<Category> findByTitle(String title);

	@Nonnull
	List<Category> findAll();
}
