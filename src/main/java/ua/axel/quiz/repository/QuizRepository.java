package ua.axel.quiz.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.axel.quiz.entity.Quiz;

@Repository
public interface QuizRepository extends CrudRepository<Quiz, Long> {

	@Query(value = "" +
			"SELECT * " +
			"FROM quiz " +
			"WHERE quiz.author_id = ? AND right_answer > 0 " +
			"ORDER BY random() " +
			"LIMIT 1;", nativeQuery = true)
	Quiz findByAuthorRandomQuizWithRightAnswer(long authorId);

}
