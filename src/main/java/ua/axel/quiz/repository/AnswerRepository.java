package ua.axel.quiz.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.axel.quiz.entity.Answer;

@Repository
public interface AnswerRepository extends CrudRepository<Answer, Long> {
}
