package ua.axel.quiz.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.axel.quiz.entity.Author;
import ua.axel.quiz.entity.Category;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {

	Optional<Author> findByTitle(String title);

	@Nonnull
	List<Author> findAll();

	@Query(value = "select distinct a from Author as a join fetch Quiz as q on a = q.author where q.rightAnswer > 0")
	List<Author> findAllHasQuizzesWithRightAnswerByCategory(Category category);
}
