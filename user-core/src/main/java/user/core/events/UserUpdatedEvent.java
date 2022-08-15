package user.core.events;

import lombok.Builder;
import lombok.Data;
import user.core.models.User;

@Data @Builder
public class UserUpdatedEvent {
	private String id;
	private User user;

}
