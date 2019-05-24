package pers.idoo.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import pers.idoo.dao.JDBC;
import pers.idoo.jrserver.*;
import pers.idoo.pojo.Distance;

@SpringBootApplication
public class Main extends SpringBootServletInitializer{
	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
		
		Config.loadConfigFile("config.json");
		
		JRServer ser = new JRServer(8001);
		ser.start();
		ser.checkSelf("127.0.0.1",8001);
	}
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(this.getClass());
	}
}