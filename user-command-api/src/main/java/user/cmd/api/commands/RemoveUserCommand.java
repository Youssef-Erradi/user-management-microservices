package user.cmd.api.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RemoveUserCommand {
	@TargetAggregateIdentifier
	private String id;

}
