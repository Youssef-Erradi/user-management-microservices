package user.core.models;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Account {
	@NotBlank @Size(min = 6)
	private String username;
	
	@NotBlank @Size(min = 8)
	private String password;
	
	@NotNull
	private List<Role> roles;

}
