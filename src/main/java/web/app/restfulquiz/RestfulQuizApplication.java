package web.app.restfulquiz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableCaching
@EnableAspectJAutoProxy
@SpringBootApplication(exclude= HibernateJpaAutoConfiguration.class)
public class RestfulQuizApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestfulQuizApplication.class, args);
    }

}
