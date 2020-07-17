package newsApp.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class MvcConfiguration implements WebMvcConfigurer {

//    private final static String[][] mappings = {
//            {"/login",      "login"},
//    };
//
//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//        Arrays.stream(mappings)
//                .forEach(m->
//                        registry.addViewController(m[0]).setViewName(m[1]));
//    }
}