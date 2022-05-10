package ua.axel.quiz.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.axel.quiz.entity.Source;

@Repository
public interface SourceRepository extends CrudRepository<Source, Long> {
}
