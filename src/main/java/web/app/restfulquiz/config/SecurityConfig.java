package web.app.restfulquiz.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import web.app.restfulquiz.security.JwtFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private JwtFilter jwtFilter;

    private UserDetailsService userDetailsService;

    @Autowired
    public void setJwtFilter(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Autowired
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    // authentication provider uses the userDetailsService by calling the loadUserByUsername()
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        return provider;
    }

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    //This method is used to configure the security of the application
    //Since we are attaching jwt to a request header manually, we don't need to worry about csrf
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable();
//                .addFilterAfter(jwtFilter, UsernamePasswordAuthenticationFilter.class)
//                .authorizeRequests()
//                .antMatchers("/login").permitAll()
//                .antMatchers("/user").hasAuthority("read")
//                .antMatchers("/user").hasAuthority("write")
//                .antMatchers("/user/{user_id}/status").hasAuthority("update")
//                .antMatchers("/user").hasAuthority("delete")
//                .anyRequest()
//                .authenticated();
        http
                .csrf().disable()
                .addFilterAfter(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/login", "/register", "/actuator/health", "/actuator/info", "/actuator/beans", "/swagger-ui.html").permitAll()
                .antMatchers("/quiz", "/quizResult", "/admin/user", "/admin/question").hasAuthority("read")
                .antMatchers("/quiz", "/admin/question").hasAuthority("write")
                .antMatchers("/admin/user", "/admin/question").hasAuthority("update")
                .anyRequest().authenticated();
    }

}
