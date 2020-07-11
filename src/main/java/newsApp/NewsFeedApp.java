package newsApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class NewsFeedApp {
    public static void main(String[] args) {
        SpringApplication.run(NewsFeedApp.class,args);
    }
}
