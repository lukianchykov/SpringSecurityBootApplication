package com.example.springsecuritybootapplication.security;

import com.example.springsecuritybootapplication.entities.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final AuthenticationProviderImplementation authenticationProvider;
    private final UserDetailsServiceImplementation userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public WebSecurityConfiguration (AuthenticationProviderImplementation authenticationProvider,
                                     UserDetailsServiceImplementation userService) {
        this.authenticationProvider = authenticationProvider;
        this.userService = userService;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider);
    }

    @Override
    public void configure(WebSecurity web){
        web.debug(true);
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .authorizeRequests()
                .antMatchers("/css/**", "/js/**", "/index*", "/json/**", "/*.ico", "/images/**").permitAll()
                .antMatchers(HttpMethod.GET, "/user/**").hasRole(Role.USER.name())
                .antMatchers(HttpMethod.GET, "/user/**").authenticated()
                .antMatchers(HttpMethod.GET, "/admin/**").hasRole(Role.ADMIN.name())
                .antMatchers(HttpMethod.GET, "/admin/**").authenticated()
            .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/perform_login")
                .defaultSuccessUrl("/")
                .failureForwardUrl("/login?error=true")
            .and()
                .logout()
                .logoutSuccessUrl("/login?logout=true")
            .and()
                .exceptionHandling().accessDeniedPage("/access-denied")
            .and()
                .rememberMe()
            .and()
                .csrf().disable()
/*                .cors().configurationSource(corsConfiguration())*/
        ;
    }

/*    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }*/

/*    @Bean
    CorsConfigurationSource corsConfiguration() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "UPDATE"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }*/
}
