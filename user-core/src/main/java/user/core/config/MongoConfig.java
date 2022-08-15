package user.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

@Configuration
public class MongoConfig {
	@Autowired
	private MongoDatabaseFactory factory;
	
	@Autowired
	private MongoMappingContext mappingContext;
	
	@Bean
	public MappingMongoConverter mongoConverter() {
		DbRefResolver resolver = new DefaultDbRefResolver(factory);
		MappingMongoConverter converter = new MappingMongoConverter(resolver, mappingContext);
		converter.setTypeMapper(new DefaultMongoTypeMapper(null));
		return converter;
	}

}
