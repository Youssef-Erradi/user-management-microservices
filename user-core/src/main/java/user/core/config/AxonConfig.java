package user.core.config;

import org.axonframework.eventhandling.tokenstore.TokenStore;
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.extensions.mongo.DefaultMongoTemplate;
import org.axonframework.extensions.mongo.MongoTemplate;
import org.axonframework.extensions.mongo.eventsourcing.eventstore.MongoEventStorageEngine;
import org.axonframework.extensions.mongo.eventsourcing.eventstore.MongoFactory;
import org.axonframework.extensions.mongo.eventsourcing.tokenstore.MongoTokenStore;
import org.axonframework.serialization.Serializer;
import org.axonframework.spring.config.AxonConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;

@Configuration
public class AxonConfig {

	@Value("${spring.data.mongodb.host:localhost}")
	private String host;

	@Value("${spring.data.mongodb.port:27017}")
	private int port;

	@Value("${spring.data.mongodb.database:users}")
	private String database;

	@Bean
	public MongoClient mongoClient() {
		ConnectionString connectionString = new ConnectionString("mongodb://"+host+":"+port);
		MongoClientSettings clientSettings = MongoClientSettings.builder().applyConnectionString(connectionString).build();
		MongoFactory mongoFactory = new MongoFactory();
		mongoFactory.setMongoClientSettings(clientSettings);
		return mongoFactory.createMongo();
	}
	
	@Bean
	public MongoTemplate template() {
		return DefaultMongoTemplate.builder()
				.mongoDatabase(mongoClient(), database)
				.build();
	}
	
	@Bean
	public TokenStore store(Serializer serializer) {
		return MongoTokenStore.builder()
				.mongoTemplate(template())
				.serializer(serializer)
				.build();
	}
	
	@Bean
	public EventStorageEngine storageEngine(MongoClient client) {
		return MongoEventStorageEngine.builder()
				.mongoTemplate(DefaultMongoTemplate.builder()
						.mongoDatabase(client)
						.build())
				.build();
	}
	
	@Bean
	public EmbeddedEventStore eventStore(EventStorageEngine engine, AxonConfiguration configuration)  {
		return EmbeddedEventStore.builder()
				.storageEngine(engine)
				.messageMonitor(configuration.messageMonitor(EventStore.class, "eventStore"))
				.build();
	}

}
