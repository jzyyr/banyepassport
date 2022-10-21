package cn.tedu.csmallpassport.config;

import cn.tedu.csmallpassport.filter.JwtAuthorizationFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    //加密需要用这个
    @Bean
    public PasswordEncoder passwordEncoder() {
        log.debug("创建@Bean方法定义的对象：PasswordEncoder");
        return new BCryptPasswordEncoder();
//        return NoOpPasswordEncoder.getInstance(); // 无操作的密码编码器，即：不会执行加密处理

    }

    //要用自己的账号密码就要有这个配置
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    //配置白名单
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String [] urls={
                "/doc.html",
                "/**/*.js",
                "/**/*.css",
                "/swagger-resources",
                "/v2/api-docs",
                "/admins/login"
        };

        // 禁用CSRF（防止伪造的跨域攻击）
       http.csrf().disable();

       //启用spring security的corsFilter过滤器,此过滤器会放行跨域请求,
       // ,包括预检的OPTIONS请求
       http.cors();

        http.authorizeRequests() // 对请求执行认证与授权
                .antMatchers(urls) // 匹配某些请求路径
                .permitAll() // （对此前匹配的请求路径）不需要通过认证即允许访问

//                 以下2行代码是用于对预检的OPTIONS请求直接放行的
//                .antMatchers(HttpMethod.OPTIONS, "/**")
//                .permitAll()
//
                .anyRequest() // 除以上配置过的请求路径以外的所有请求路径
                .authenticated(); // 要求是已经通过认证的

        //是否启用登录页面
//        http.formLogin();

        //一定要将自定义的JWT过滤器添加在(spring内置的过滤器之前)
        //UsernamePasswordAuthenticationFilter之前
        http.addFilterBefore(jwtAuthorizationFilter,
                UsernamePasswordAuthenticationFilter.class);
    }

    @Autowired
    JwtAuthorizationFilter jwtAuthorizationFilter;
}
