package iiqcov.blog.springbootdeveloper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BlogProjectApplication {
    public static void main(String[] args){
        SpringApplication.run(BlogProjectApplication.class, args);
    }
}
