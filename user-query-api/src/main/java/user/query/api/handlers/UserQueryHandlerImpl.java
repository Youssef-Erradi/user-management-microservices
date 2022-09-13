package user.query.api.handlers;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import user.core.models.User;
import user.query.api.dto.UserLookupResponse;
import user.query.api.queries.FindAllUsersQuery;
import user.query.api.queries.GetUserByIdQuery;
import user.query.api.queries.SearchUsersQuery;
import user.query.api.repositories.UserRepository;

@Service
@AllArgsConstructor
public class UserQueryHandlerImpl implements UserQueryHandler {
	private final UserRepository userRepository;
	
	@QueryHandler
	@Override
	public UserLookupResponse getUserById(GetUserByIdQuery query) {
		var optional = userRepository.findById(query.getId());
		User user = optional.isPresent() ? optional.get() : null;
		return new UserLookupResponse( user );
	}

	@QueryHandler
	@Override
	public UserLookupResponse searchUser(SearchUsersQuery query) {
		var users = userRepository.searchByFilter(query.getFilter());
		return new UserLookupResponse(users);
	}

	@QueryHandler
	@Override
	public UserLookupResponse findAllUsers(FindAllUsersQuery query) {
		var users = userRepository.findAll();
		return new UserLookupResponse(users);
	}

}
