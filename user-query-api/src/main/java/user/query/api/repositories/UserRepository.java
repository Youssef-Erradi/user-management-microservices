package user.query.api.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import user.core.models.User;

public interface UserRepository extends MongoRepository<User, String> {

}
