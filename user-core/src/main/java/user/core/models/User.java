package user.core.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "users")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class User {
	@Id
	private String id;	
	private String firstName, lastName, email;
	private Account account ;

}
