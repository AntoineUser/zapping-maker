package fr.agauthier.zappingmaker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class ZappingMakerApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(ZappingMakerApplication.class, args);
	}

}
