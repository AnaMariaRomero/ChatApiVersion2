package net.apimessages.pd2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories({"net.apimessages.pd2.repository"})
@ComponentScan({"net.apimessages.pd2.model","net.apimessages.pd2.service","net.apimessages.pd2.management","net.apimessages.pd2.rest","net.apimessages.pd2.exceptions"})
public class ChatApiVersion2Application {

	public static void main(String[] args) {
		SpringApplication.run(ChatApiVersion2Application.class, args);
	}

}
