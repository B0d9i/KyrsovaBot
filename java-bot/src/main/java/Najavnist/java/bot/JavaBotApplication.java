package Najavnist.java.bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
public class JavaBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaBotApplication.class, args);
		System.out.println();
	}

}
