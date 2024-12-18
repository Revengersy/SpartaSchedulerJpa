package sparta.sparta_scheduler_jpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SpartaSchedulerJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpartaSchedulerJpaApplication.class, args);
    }

}
