package com.paypal.ewallet.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfiguration {

//singleton scope and prototype(not frequently used) scope.
//Singleton one object instance per Spring IoC container. It’s default
//If you want to reuse same instance across per Spring IoC container,in that kind of scenario, you can go with singleton.
// If you ask the spring container for a bean multiple times it'll return the same instance back.
//However, if you want to create a new instance each time you ask for a bean, in that kind of scenario,
//you can go for prototype scope
@Bean("passwordEncoder")
    public PasswordEncoder getPasswordEncoder (){
    return new BCryptPasswordEncoder();

}

}
