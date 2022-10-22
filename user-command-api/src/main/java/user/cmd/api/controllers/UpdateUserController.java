package user.cmd.api.controllers;

import javax.validation.Valid;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import user.cmd.api.commands.UpdateUserCommand;
import user.cmd.api.dto.BaseResponse;

@RestController
@RequestMapping("/api/v1/updateUser")
@AllArgsConstructor
public class UpdateUserController {
	private final CommandGateway gateway;
	
	@PutMapping("/{id}")
	@PreAuthorize("hasAuthority('WRITE')")
	public ResponseEntity<?> update(@PathVariable String id, @Valid @RequestBody UpdateUserCommand command){
		try {
			command.setId(id);
			gateway.sendAndWait(command);
			return ResponseEntity.ok(new BaseResponse(command.getId(), "User successully Updated!"));
		} catch(Exception e) {
			e.printStackTrace();
			String errorMessage = "Error while processing update user request for id="+command.getId();
			return ResponseEntity.internalServerError().body(new BaseResponse(null, errorMessage));
		}
	}
	
}
