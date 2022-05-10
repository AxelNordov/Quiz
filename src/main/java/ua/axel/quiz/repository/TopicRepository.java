package ua.axel.quiz.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.axel.quiz.entity.Topic;

@Repository
public interface TopicRepository extends CrudRepository<Topic, Long> {
}
