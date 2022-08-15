package user.query.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import user.core.config.AxonConfig;
import user.core.config.MongoConfig;

@SpringBootApplication
@Import({AxonConfig.class, MongoConfig.class})
public class UserQueryApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserQueryApiApplication.class, args);
	}

}
