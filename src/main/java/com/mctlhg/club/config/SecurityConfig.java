package com.mctlhg.club.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Log4j2
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
                .antMatchers("/sample/all").permitAll()
                .antMatchers("/sample/member").hasRole("USER");

        http.formLogin(); //인가, 인증에 문제시 로그인 화면 발생시킨다.
        http.csrf().disable(); // 토큰 발행 하지 않는다.
        //http.logout();
        http.oauth2Login();
    }
/*
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        // 사용자 계정: user1
        // 1111 의 인코딩결과를 이용한다.
        auth.inMemoryAuthentication()
                .withUser("user1")
                .password("$2a$10$Tl3m468xA92Y3NxNRGyNcu60OyuR61aicHXC2L4SCYqT0fbdwsxUm")
                .roles("USER");
    }
 */
}
