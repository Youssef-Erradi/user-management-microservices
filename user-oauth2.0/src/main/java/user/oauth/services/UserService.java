package user.oauth.services;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import user.core.models.Account;
import user.core.models.User;
import user.oauth.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService{
	
	private final UserRepository repository;
	
	public UserService(UserRepository repository) {
		this.repository = repository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		username = username.strip().toLowerCase();
		Optional<User> user = repository.findByUsername(username);
		
		if (user.isEmpty())
			throw new UsernameNotFoundException("Bad credentials");
		
		return userDetailsfromAccount(user.get().getAccount());
	}
	
	private UserDetails userDetailsfromAccount(final Account account){
		return org.springframework.security.core.userdetails.User
				.withUsername(account.getUsername())
				.password(account.getPassword())
				.authorities(account.getRoles())
				.accountExpired(false)
				.accountLocked(false)
				.credentialsExpired(false)
				.disabled(false)
				.build();
	}

} 
