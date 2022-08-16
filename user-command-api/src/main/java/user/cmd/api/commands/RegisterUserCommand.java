package user.cmd.api.commands;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Builder;
import lombok.Data;
import user.core.models.User;

@Data @Builder
public class RegisterUserCommand {
	@TargetAggregateIdentifier
	private String id;
	@NotNull @Valid
	private User user;

}
