package user.cmd.api.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Builder;
import lombok.Data;
import user.core.models.User;

@Data @Builder
public class UpdateUserCommand {
	@TargetAggregateIdentifier
	private String id;
	private User user;

}
