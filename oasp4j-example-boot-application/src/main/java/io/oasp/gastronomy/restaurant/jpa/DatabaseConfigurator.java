package io.oasp.gastronomy.restaurant.jpa;

import java.sql.SQLException;

import org.h2.jdbcx.JdbcDataSource;
import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile({"!integrationTest"})
public class DatabaseConfigurator {

	@Value(value = "${database.h2.server.port}")
	private String port;

	@Value(value = "${database.url}")
	private String url;
	
	@Value(value = "${database.user.login}")
	private String user;
	
	@Value(value = "${database.user.password}")
	private String password;

	@Bean(name = "dbServer", destroyMethod = "stop")
	public Server dbServer() {
		Server server = null;
		try {
			server = Server.createTcpServer("-tcp", "-tcpAllowOthers",
					"-tcpPort", port);
			server.start();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return server;
	}

	@Bean(name="appDataSource")
	@DependsOn({"dbServer"})
	public JdbcDataSource dataSource(){
		JdbcDataSource dataSource = new JdbcDataSource();
		dataSource.setURL(url);
		dataSource.setUser(user);
		dataSource.setPassword(password);
		
		return dataSource;
	}
}
