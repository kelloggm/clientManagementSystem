package lv.javaguru.cms;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@ComponentScan("lv.javaguru")
@EnableAutoConfiguration
@EnableWebMvc
@SpringBootConfiguration
public class CourseManagementSystem {

    public static void main(String[] args) {
        new SpringApplicationBuilder(CourseManagementSystem.class).run(args);
    }

}
