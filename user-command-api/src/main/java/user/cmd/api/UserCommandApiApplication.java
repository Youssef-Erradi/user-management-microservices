package user.cmd.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import user.core.config.AxonConfig;

@SpringBootApplication
@Import({AxonConfig.class})
public class UserCommandApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserCommandApiApplication.class, args);
	}

}
