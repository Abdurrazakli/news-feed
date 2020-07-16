package newsApp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@Configuration
@EnableOAuth2Client
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/registration","/css/*","/js/*","/img/*").permitAll()
                .antMatchers("/login**","/webjars/**","/error**").permitAll()
                .antMatchers("/user/reset-password**","/user/change-password**","/user/reset-password**","/user/save-password**").permitAll()
                .antMatchers("/user/verify-account**").permitAll()
                .anyRequest().authenticated()
        .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/", true)
                .failureUrl("/login?error")
                .permitAll()
        .and()
                .oauth2Login()
                .loginPage("/login")
                .defaultSuccessUrl("/news")
                .failureUrl("/login?error");
    }
}
