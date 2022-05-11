package ua.axel.quiz.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.axel.quiz.entity.QuizDetails;

@Repository
public interface QuizDetailsRepository extends CrudRepository<QuizDetails, Long> {
}
