package ua.axel.quiz.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.axel.quiz.entity.Quiz;

@Repository
public interface QuizRepository extends CrudRepository<Quiz, Long> {

	@Query(value = "select *\n" +
			"from quiz\n" +
			"where quiz.author_id = ?\n" +
			"  and right_answer > 0\n" +
			"order by random()\n" +
			"limit 1;", nativeQuery = true)
	Quiz findByAuthorRandomQuizWithRightAnswer(long authorId);
}
