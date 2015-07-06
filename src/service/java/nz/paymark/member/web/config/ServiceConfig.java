package nz.paymark.member.web.config;


import nz.paymark.shared.database.config.DatabaseProperties;
import nz.paymark.member.api.MemberService;
import nz.paymark.member.service.MemberServiceImpl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:member.webservice.properties")
public class ServiceConfig {
	protected final static String MEMBER_SCHEMA = "member";
	protected final static String MEMBER_PACKAGE = "nz.paymark.member.model";
	
	
	@Value("${member.ds.dialect}")
	private String hibernateDialect;
	
	@Value("${member.ds.driver}")
	private String dsDriver;
	@Value("${member.ds.url}")
	private String dsUrl;
	@Value("${member.ds.user}")
	private String dsUser;
	@Value("${member.ds.password}")
	private String dsPassword;
	@Value("${member.ds.initalpoolsize}")
	private Integer dsInitialPoolSize;
	@Value("${member.ds.minpoolsize}")
	private Integer dsMinPoolSize;
	@Value("${member.ds.maxpoolsize}")
	private Integer dsMaxPoolSize;
	
	@Bean 
	public DatabaseProperties databaseProperties() {
		DatabaseProperties props = new DatabaseProperties();
		props.setUrl(dsUrl);
		props.setUser(dsUser);
		props.setPassword(dsPassword);
		props.setInitialPoolSize(dsInitialPoolSize);
		props.setMinPoolSize(dsMinPoolSize);
		props.setMaxPoolSize(dsMaxPoolSize);
		props.setDialect(hibernateDialect);
		props.setDriver(dsDriver);
		props.setSchema(MEMBER_SCHEMA);		
		props.setPackageToScan(MEMBER_PACKAGE);
		return props;
	}
}
