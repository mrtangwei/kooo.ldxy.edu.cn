package cn.edu.ldxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Author: Kooo
 * @Date: Created in 2018/9/17
 * @Modified By:
 * @Decription:
 */
@EnableDiscoveryClient
@SpringBootApplication
public class JwtAuthApplication extends WebMvcConfigurerAdapter {
    public static void main(String[] args) {
        SpringApplication.run(JwtAuthApplication.class, args);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/oauth/confirm_access").setViewName("authorize");
    }
}