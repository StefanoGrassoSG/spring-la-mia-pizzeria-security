package org.java.spring.auth.conf;

import org.java.spring.auth.db.serv.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class AuthConf {
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http.authorizeHttpRequests()
	        .requestMatchers("/pizzas/create/**").hasAnyAuthority("ADMIN")
	        .requestMatchers("/pizzas/edit/**").hasAnyAuthority("ADMIN")
	        .requestMatchers("/pizzas/delete/**").hasAnyAuthority("ADMIN")
	        .requestMatchers("/pizzas/special/**").hasAnyAuthority("ADMIN")
	        .requestMatchers("/pizzas/special/edit/**").hasAnyAuthority("ADMIN")
	        .requestMatchers("/ingredients/create/**").hasAnyAuthority("ADMIN")
	        .requestMatchers("/ingredients/delete/**").hasAnyAuthority("ADMIN")
	        .requestMatchers("/**").hasAnyAuthority("USER", "ADMIN")
	        .and().formLogin()
	        .and().logout()
	        .and().csrf().disable();
	    ;
		
		return http.build();
	}

    @Bean
    UserDetailsService userDetailsService() {
	    return new UserService();
	}
    
    @Bean
	public static PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
    }
    
    @Bean
    DaoAuthenticationProvider authenticationProvider() {
      
    	DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
   
    	authProvider.setUserDetailsService(userDetailsService());
    	authProvider.setPasswordEncoder(passwordEncoder());
   
    	return authProvider;
    }
   
}
