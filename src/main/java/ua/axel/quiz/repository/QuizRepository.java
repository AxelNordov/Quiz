package ua.axel.quiz.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.axel.quiz.entity.Author;
import ua.axel.quiz.entity.Quiz;

import java.util.List;

@Repository
public interface QuizRepository extends CrudRepository<Quiz, Long> {
	@Query("select q.id from Quiz q where q.rightAnswer is not null")
	List<Long> findIdsByRightAnswerIsNotNull();

	@Query("select q.id from Quiz q where q.rightAnswer is not null and q.author in ?1")
	List<Long> findIdsByRightAnswerIsNotNullAndAuthorIn(List<Author> authors);
}
