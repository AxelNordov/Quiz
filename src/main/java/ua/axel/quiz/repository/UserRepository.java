package ua.axel.quiz.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.axel.quiz.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}
