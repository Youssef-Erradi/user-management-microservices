package user.oauth.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import user.core.models.User;

public interface UserRepository extends MongoRepository<User, String>{
	
	@Query(" { 'account.username' : ?0 } ")
	Optional<User> findByUsername(String username);

}
