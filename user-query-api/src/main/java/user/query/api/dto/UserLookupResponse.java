package user.query.api.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import user.core.models.User;

@Data
@AllArgsConstructor
public class UserLookupResponse {
	private List<User> users;
	
	public UserLookupResponse(User user) {
		users = new ArrayList<>();
		if(user != null)
			users.add(user);
	}

}
