package user.cmd.api.controllers;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import user.cmd.api.commands.RemoveUserCommand;
import user.cmd.api.dto.BaseResponse;

@RestController
@RequestMapping("/api/v1/removeUser")
@AllArgsConstructor
public class RemoveUserController {
	private final CommandGateway gateway;
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> removeUser(@PathVariable String id){
		try {
			gateway.sendAndWait(new RemoveUserCommand(id));
			return ResponseEntity.ok(new BaseResponse(id, "User successully removed!"));
		} catch (Exception e) {
			e.printStackTrace();
			String errorMessage = "Error while processing remove user request";
			return ResponseEntity.internalServerError().body(new BaseResponse(id, errorMessage));
		}
	}
}
