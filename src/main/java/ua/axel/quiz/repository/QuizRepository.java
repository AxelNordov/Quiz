package ua.axel.quiz.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.axel.quiz.entity.Quiz;

@Repository
public interface QuizRepository extends CrudRepository<Quiz, Long> {
}
