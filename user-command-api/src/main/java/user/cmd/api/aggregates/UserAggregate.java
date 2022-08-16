package user.cmd.api.aggregates;

import java.util.UUID;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import user.cmd.api.commands.RegisterUserCommand;
import user.cmd.api.commands.RemoveUserCommand;
import user.cmd.api.commands.UpdateUserCommand;
import user.core.events.UserRegisteredEvent;
import user.core.events.UserRemovedEvent;
import user.core.events.UserUpdatedEvent;
import user.core.models.User;

@Aggregate
public class UserAggregate {
	@AggregateIdentifier
	private String id;
	private User user;
	
	// TODO - create bean of type PasswordEncoder instead
	private final PasswordEncoder encoder = new BCryptPasswordEncoder(12);
	
	@CommandHandler
	public UserAggregate(RegisterUserCommand command) {
		User user = command.getUser();
		user.setId(command.getId());
		
		String password = encoder.encode(user.getAccount().getPassword());
		user.getAccount().setPassword(password);
		
		UserRegisteredEvent event = UserRegisteredEvent.builder()
				.id(command.getId())
				.user(user)
				.build();
		
		AggregateLifecycle.apply(event);
	}
	
	@CommandHandler
	public void handle(UpdateUserCommand command) {
		User user = command.getUser();
		user.setId(command.getId());
		
		String password = encoder.encode(user.getAccount().getPassword());
		user.getAccount().setPassword(password);
		
		UserUpdatedEvent event = UserUpdatedEvent.builder()
				.id(UUID.randomUUID().toString())
				.user(user)
				.build();
		
		AggregateLifecycle.apply(event);
	}
	
	@CommandHandler
	public void handle(RemoveUserCommand command) {
		UserRemovedEvent event = new UserRemovedEvent();
		event.setId(command.getId());
		
		AggregateLifecycle.apply(event);
	}
	
	@EventSourcingHandler
	public void on(UserRegisteredEvent event) {
		id = event.getId();
		user = event.getUser();
	}
	
	@EventSourcingHandler
 	public void on(UserUpdatedEvent event) {
		user = event.getUser();
	}
	
	@EventSourcingHandler
	public void on(UserRemovedEvent event) {
		AggregateLifecycle.markDeleted();
	}

}
