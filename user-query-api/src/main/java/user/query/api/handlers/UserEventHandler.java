package user.query.api.handlers;

import user.core.events.UserRegisteredEvent;
import user.core.events.UserRemovedEvent;
import user.core.events.UserUpdatedEvent;

public interface UserEventHandler {
	void on(UserRegisteredEvent event);
	void on(UserUpdatedEvent event);
	void on(UserRemovedEvent event);
}
