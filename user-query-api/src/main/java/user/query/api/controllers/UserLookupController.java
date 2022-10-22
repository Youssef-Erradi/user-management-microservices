package user.query.api.controllers;

import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import user.query.api.dto.UserLookupResponse;
import user.query.api.queries.FindAllUsersQuery;
import user.query.api.queries.GetUserByIdQuery;
import user.query.api.queries.SearchUsersQuery;

@RestController
@RequestMapping("/api/v1/userLookup")
@AllArgsConstructor
public class UserLookupController {
	private final QueryGateway gateway;

	@GetMapping
	@PreAuthorize("hasAuthority('READ')")
	public ResponseEntity<?> findAllUsers(){
		try {
			var response = gateway.query(new FindAllUsersQuery(), ResponseTypes.instanceOf(UserLookupResponse.class)).join();
			if (response == null || CollectionUtils.isEmpty(response.getUsers()))
				return ResponseEntity.noContent().build();
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			e.printStackTrace();
			var message = "Unable to handle find all users query"; 
			return ResponseEntity.internalServerError().body(message);
		}
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('READ')")
	public ResponseEntity<?> getUserById(@PathVariable String id){
		try {
			var response = gateway.query(new GetUserByIdQuery(id), ResponseTypes.instanceOf(UserLookupResponse.class)).join();
			if (response == null || CollectionUtils.isEmpty(response.getUsers()))
				return ResponseEntity.noContent().build();
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			e.printStackTrace();
			var message = "Unable to find user with id %s".formatted(id); 
			return ResponseEntity.internalServerError().body(message);
		}
	}

	@GetMapping("/filter/{filter}")
	@PreAuthorize("hasAuthority('READ')")
	public ResponseEntity<?> searchUsersByFilter(@PathVariable String filter){
		try {
			var response = gateway.query(new SearchUsersQuery(filter), ResponseTypes.instanceOf(UserLookupResponse.class)).join();
			if (response == null || CollectionUtils.isEmpty(response.getUsers()))
				return ResponseEntity.noContent().build();
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			e.printStackTrace();
			var message = "Unable to find users with the specified filter"; 
			return ResponseEntity.internalServerError().body(message);
		}
	}
}
