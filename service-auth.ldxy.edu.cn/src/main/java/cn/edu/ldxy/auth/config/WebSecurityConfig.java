package cn.edu.ldxy.auth.config;


import cn.edu.ldxy.auth.domain.RoleResources;
import cn.edu.ldxy.auth.handle.MySimpleUrlAuthenticationSuccessHandler;
import cn.edu.ldxy.auth.service.CustomUserDetailsService;
import cn.edu.ldxy.auth.service.RoleResourcesService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.*;

/**
 * Created by Mr.Yangxiufeng on 2017/12/27.
 * Time:16:42
 * ProjectName:Mirco-Service-Skeleton
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private RoleResourcesService roleResourcesService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    private Map<String, Collection<ConfigAttribute>> resourceMap = null;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        auth.userDetailsService(customUserDetailsService).passwordEncoder(encoder);
    }




    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .cors()
                .and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/anonymous*").anonymous()
                .antMatchers("/login*","/oauth/**", "/users/query/**").permitAll()
                .anyRequest().authenticated()

                .and()
                .formLogin()
                //.loginPage("/login.html")
                .loginProcessingUrl("/login")
                .successHandler(myAuthenticationSuccessHandler());

                getRoleResource();
                for( Map.Entry<String,  Collection<ConfigAttribute>> entry :resourceMap.entrySet())
                {
                    String urls = " " + entry.getKey().toString() + " ";  //这个地方是最奇怪的地方  entry.getKey() 不能被直接当做String识别
                    String role = "";
                    for(int i=0; i < entry.getValue().toArray().length; i++)
                    {
                        role = role + entry.getValue().toArray()[i];
                        if(i < entry.getValue().toArray().length -1){
                            role = role +  ",";
                        }
                    }
                    System.out.println("urls " + urls + " matcher : " +  role);
                    http.authorizeRequests().antMatchers(urls.trim()).hasAnyAuthority(role);
                }
        // ...
    }

    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler(){
        return new MySimpleUrlAuthenticationSuccessHandler();
    }

   @Bean
    public StrictHttpFirewall httpFirewall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowSemicolon(true);
        return firewall;
    }

/*
    @Override
      public void configure(HttpSecurity http)throws Exception{
        http
                .cors().and()
                .csrf().disable();

        getRoleResource();
        for( Map.Entry<String,  Collection<ConfigAttribute>> entry :resourceMap.entrySet())
        {
            String urls = " " + entry.getKey().toString() + " ";  //这个地方是最奇怪的地方  entry.getKey() 不能被直接当做String识别
            String role = "";
            for(int i=0; i < entry.getValue().toArray().length; i++)
            {
                role = role + entry.getValue().toArray()[i];
                if(i < entry.getValue().toArray().length -1){
                    role = role +  ",";
                }
            }
            System.out.println("urls " + urls + " matcher : " +  role);
            http.authorizeRequests().antMatchers(urls.trim()).hasAnyAuthority(role);
        }
        http.authorizeRequests().anyRequest().permitAll().requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .and().formLogin().loginPage("/login").permitAll()
                .and()
                // 登出
                .logout().permitAll()
                .and()
                // HTTP Basic authentication 异步认证
                .httpBasic();

    }*/

    private void getRoleResource() throws Exception {
        resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
        for (RoleResources r : this.roleResourcesService.findAll())
        {
            Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
            ConfigAttribute ca = new SecurityConfig(r.getRole());
            atts.add(ca);
            JSONObject json = r.getUrlJson();
            Iterator ir = json.entrySet().iterator();
            for(String s : r.getUrlJson().keySet()){
                String url = json.getString(s);
                if (resourceMap.get(url) != null) {
                    boolean is = false;
                    Iterator<ConfigAttribute> it = resourceMap.get(url).iterator();
                    Collection<ConfigAttribute> attnew = new ArrayList<ConfigAttribute>();
                    while (it.hasNext()) {
                        ConfigAttribute tmpca = it.next();
                        if (ca == tmpca){
                            is = true;
                        }else{
                            attnew.add(tmpca);
                        }
                    }
                    if (!is) {
                        attnew.add(ca);
                        resourceMap.put(url, attnew);
                    }
                } else {
                    resourceMap.put(url, atts);
                }
            }
        }
    }



    @Bean
    @Order(value = 0)
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedOrigin("http://localhost:8080");
        configuration.addAllowedOrigin("http://localhost:8082");
        configuration.addAllowedOrigin("http://stardream-boss.ldxy.edu.cn");
        configuration.addAllowedOrigin("http://localhost:9020");
        configuration.addAllowedOrigin("http://snhstat.ddianle.com");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
