package ua.axel.quiz.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.axel.quiz.entity.User;

import javax.annotation.Nonnull;
import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	@Nonnull
	List<User> findAll();
}
