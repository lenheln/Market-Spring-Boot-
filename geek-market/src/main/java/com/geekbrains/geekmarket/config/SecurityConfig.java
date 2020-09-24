package com.geekbrains.geekmarket.config;

import com.geekbrains.geekmarket.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private DataSource myDataSource;
    private UserService userService;
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;


    @Autowired
    public void setMyDataSource(DataSource myDataSource) {
        this.myDataSource = myDataSource;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setCustomAuthenticationSuccessHandler(CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler) {
        this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
    }

//    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
//
//        auth.jdbcAuthentication().dataSource(myDataSource);
//
//        //Хранение пользователей в оперативной памяти
////        User.UserBuilder users = User.builder();
////        auth.inMemoryAuthentication()
////                .withUser(users.username("admin").password("{noop}1234").roles("ADMIN", "USER"))
////                .withUser(users.username("bob").password("{noop}1234").roles("USER"));
//    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception  {
        auth.authenticationProvider(authenticationProvider());
    }

    //Для обхода 403 ошибки при отправке POST запроса
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http       //other configure params.
//                .csrf().disable();
//    }

    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
//                .antMatchers("/").hasAnyRole("USER", "ADMIN")
                .antMatchers("/").permitAll()
                .antMatchers("/register/**").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/cart/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/product/**").hasAnyRole("USER", "ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/authenticateTheUser")
                .permitAll()
                .and()
                .logout()
                .permitAll();

////                .antMatchers("/admin/**").hasRole("ADMIN")
//                .antMatchers("/product/").permitAll()
//                .and()
//                .formLogin()
//                .loginPage("/login")
//                .loginProcessingUrl("/authenticateTheUser")
//                .successHandler(customAuthenticationSuccessHandler)
//                .permitAll();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
//        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }
}
