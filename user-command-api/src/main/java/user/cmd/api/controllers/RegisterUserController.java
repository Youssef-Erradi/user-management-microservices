package user.cmd.api.controllers;

import java.util.UUID;

import javax.validation.Valid;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import user.cmd.api.commands.RegisterUserCommand;
import user.cmd.api.dto.BaseResponse;

@RestController
@RequestMapping("/api/v1/registerUser")
@AllArgsConstructor
public class RegisterUserController {
	private final CommandGateway gateway;
	
	@PostMapping
	public ResponseEntity<BaseResponse> register(@Valid @RequestBody RegisterUserCommand command){
		command.setId(UUID.randomUUID().toString());
		try {
			gateway.sendAndWait(command);
			return new ResponseEntity<>(new BaseResponse(command.getId(), "User successully registered!"), HttpStatus.CREATED);
		} catch(Exception e) {
			String errorMessage = "Error while processing user register request for id="+command.getId();
			e.printStackTrace();
			
			return ResponseEntity.internalServerError().body(new BaseResponse(null, errorMessage));
		}
	}
}
