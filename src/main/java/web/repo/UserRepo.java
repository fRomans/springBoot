package web.repo;

import org.springframework.data.repository.CrudRepository;
import web.model.User;

public interface UserRepo extends CrudRepository<User, Integer> {
     User findUserById(Integer id);
}
