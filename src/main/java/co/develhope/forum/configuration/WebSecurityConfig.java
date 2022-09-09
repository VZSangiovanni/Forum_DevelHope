package co.develhope.forum.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// inspired by https://www.codejava.net/frameworks/spring-boot/form-authentication-with-jdbc-and-mysql
// inspired by https://www.baeldung.com/spring-security-jdbc-authentication
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private MySQLConfig mySQLConfig;


    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(mySQLConfig.dataSource());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests()
                .antMatchers("/clean").permitAll()
                .antMatchers("/user/**")
                .permitAll().anyRequest().authenticated();

        http.csrf().disable();
        http.headers().frameOptions().disable();

        return http.build();
    }
}
