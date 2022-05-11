package ua.axel.quiz.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.axel.quiz.entity.Author;

@Repository
public interface SourceRepository extends CrudRepository<Author, Long> {
}
