package com.prepfortech.netflixclone.security;

import com.prepfortech.netflixclone.accessor.UserAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.beans.JavaBean;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserAccessor userAccessor;

    @Autowired
    private UserSecurityService userSecurityService;

    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userSecurityService);
    }

    protected void configure(HttpSecurity http) throws Exception {
        //tell what endpoints are public and what endpoints needs security and tell how to handle security
      http.cors().and().csrf().disable().authorizeRequests()
              .antMatchers("/login").permitAll()
              .anyRequest().authenticated()
              .and()
              .addFilter(new JWTAuthorizationFilter(new RoleBasedAuthenticationManager(userAccessor)))
              .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

      http.httpBasic().disable();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedHeader("Authorization");
        corsConfiguration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","OPTIONS"));
        corsConfiguration.addAllowedOrigin("");

        source.registerCorsConfiguration("/**",corsConfiguration);
        return source;
    }

}
