package user.query.api.handlers;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import user.core.events.UserRegisteredEvent;
import user.core.events.UserRemovedEvent;
import user.core.events.UserUpdatedEvent;
import user.query.api.repositories.UserRepository;

@Service
@AllArgsConstructor
@ProcessingGroup("user-group")
public class UserEventHandlerImpl implements UserEventHandler{
	private final UserRepository userRepository;

	@EventHandler
	@Override
	public void on(UserRegisteredEvent event) {
		userRepository.save(event.getUser());
	}

	@EventHandler
	@Override
	public void on(UserUpdatedEvent event) {
		userRepository.save(event.getUser());
	}

	@EventHandler
	@Override
	public void on(UserRemovedEvent event) {
		userRepository.deleteById(event.getId());
	}
	
}
