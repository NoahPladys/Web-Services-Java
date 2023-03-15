package edu.ap.spring.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
   @Override
   public void addViewControllers(ViewControllerRegistry registry) {
      registry.addViewController("/login").setViewName("login");
      registry.addViewController("/balance/**").setViewName("balance");
      registry.addViewController("/transaction").setViewName("transaction");
      registry.addViewController("/alltransactions").setViewName("alltransactions");
   }
}
