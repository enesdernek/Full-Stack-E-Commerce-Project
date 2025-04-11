package io.github.enesdernek.e_commerce.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.github.enesdernek.e_commerce.jwt.AuthEntryPoint;
import io.github.enesdernek.e_commerce.jwt.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
public static final String AUTHENTICATE = "/authenticate";
	
	public static final String REGISTER = "/register";
	
	public static final String [] ALLOWED_PATHS= {
			"/example",
			
	};
	
	public static final String[] SWAGGER_PATHS = {
			"/swagger-ui/**",
			"v3/api-docs/**",
			"swagger-ui.html"
	};
	
	
	@Autowired
	private AuthenticationProvider authenticationProvider;
	
	@Autowired
	private JwtAuthenticationFilter authenticationFilter;
	
	@Autowired
	private AuthEntryPoint authEntryPoint;
	
	
	  @Bean
	    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	        http
	            .cors() // Enable CORS
	            .and()
	            .csrf().disable() // Disable CSRF for stateless sessions
	            .authorizeRequests(request -> request
	                .requestMatchers(AUTHENTICATE, REGISTER).permitAll() // Allow public access to AUTHENTICATE and REGISTER
	                .requestMatchers(SWAGGER_PATHS).permitAll()
	                .requestMatchers(ALLOWED_PATHS).permitAll()// Allow access to Swagger paths
	                .anyRequest().authenticated() // All other requests require authentication
	            )
	            .sessionManagement(session -> session
	                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Use stateless sessions
	            )
	            .authenticationProvider(authenticationProvider) // Set custom authentication provider
	            .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class) // Add custom authentication filter
	            .exceptionHandling(exception -> exception
	                .authenticationEntryPoint(authEntryPoint) // Handle authentication exceptions
	            );

	        return http.build();
	    }
	
	 @Bean
	    public WebMvcConfigurer corsConfigurer() {
	        return new WebMvcConfigurer() {
	            @Override
	            public void addCorsMappings(CorsRegistry registry) {
	                registry.addMapping("/**")
	                        .allowedOrigins("http://localhost:5173","http://localhost:5174")  // Allow localhost:5173
	                        .allowedMethods("GET", "POST", "PUT", "DELETE")  // Specify allowed HTTP methods
	                        .allowedHeaders("*")  // Allow any headers
	                        .allowCredentials(true);  // Allow cookies/auth credentials
	            }
	        };
	    }
}
