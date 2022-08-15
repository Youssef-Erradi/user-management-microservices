package user.query.api.handlers;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import user.core.events.UserRegisteredEvent;
import user.core.events.UserRemovedEvent;
import user.core.events.UserUpdatedEvent;
import user.query.api.repositories.UserRepository;

@Service
@ProcessingGroup("user-group")
public class UserEventHandlerImpl implements UserEventHandler{
	
	private final UserRepository repository;
	
	@Autowired
	public UserEventHandlerImpl(UserRepository userRepository) {
		repository = userRepository;
	}
	
	@EventHandler
	@Override
	public void on(UserRegisteredEvent event) {
		repository.save(event.getUser());
	}

	@EventHandler
	@Override
	public void on(UserUpdatedEvent event) {
		repository.save(event.getUser());
	}

	@EventHandler
	@Override
	public void on(UserRemovedEvent event) {
		repository.deleteById(event.getId());
	}
	
}
