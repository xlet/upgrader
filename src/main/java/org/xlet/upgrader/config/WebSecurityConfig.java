package org.xlet.upgrader.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Creator: JimmyLin
 * DateTime: 14-9-3 下午3:45
 * Summary:
 */
@Configuration
@EnableWebMvcSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/dashboard/login").defaultSuccessUrl("/dashboard")
                .permitAll()
                    .and()
                    .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/dashboard/logout"));
        ;
        http.authorizeRequests()
                .antMatchers("/webjars/**", "/api/**/**", "/static/**", "/download/**", "/file/**", "/index", "/", "/dashboard/login","/upload**","/home/**","/**/timeline")
                .permitAll()
                .anyRequest()
                .authenticated();
        http.csrf().disable();
    }


    @Configuration
    protected static class AuthenticationConfiguration extends GlobalAuthenticationConfigurerAdapter {

        @Override
        public void init(AuthenticationManagerBuilder auth) throws Exception {
            auth.inMemoryAuthentication().withUser("znyy").password("znyy").roles("ADMIN");
        }
    }
}
