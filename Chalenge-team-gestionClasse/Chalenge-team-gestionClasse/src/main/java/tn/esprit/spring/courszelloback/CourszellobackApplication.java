package tn.esprit.spring.courszelloback;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.twilio.Twilio;
import tn.esprit.spring.Config.TwilioConfig;

@EntityScan("tn.esprit.entities")
@ComponentScan( "tn.esprit.spring")
@EnableMongoRepositories(basePackages = "tn.esprit.spring.repositories")
@SpringBootApplication
public class CourszellobackApplication {

	public static void main(String[] args) {

		Twilio.init("ACe03dfa219a613e56e8c2a8dbcabf0f4c","bba118762ffc5fc6adbbcd571e347438");
		SpringApplication.run(CourszellobackApplication.class, args);
	}

}
