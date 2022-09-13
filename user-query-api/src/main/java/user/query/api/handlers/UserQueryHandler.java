package user.query.api.handlers;

import user.query.api.dto.UserLookupResponse;
import user.query.api.queries.FindAllUsersQuery;
import user.query.api.queries.GetUserByIdQuery;
import user.query.api.queries.SearchUsersQuery;

public interface UserQueryHandler {
	UserLookupResponse getUserById(GetUserByIdQuery query);
	UserLookupResponse searchUser(SearchUsersQuery query);
	UserLookupResponse findAllUsers(FindAllUsersQuery query);
}
