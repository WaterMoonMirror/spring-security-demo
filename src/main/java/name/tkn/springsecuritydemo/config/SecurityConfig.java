package name.tkn.springsecuritydemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @description: Security配置拦截适配器类
 * @author: lz
 * @time: 2022/9/15 13:11
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // 注入 PasswordEncoder 类到 spring 容器中
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 配置认证
        http.formLogin()
                .loginPage("/index") // 配置哪个 url 为登录页面
                .loginProcessingUrl("/login") // 设置哪个是登录的 url。
                .successForwardUrl("/success") // 登录成功之后跳转到哪个 url
                .failureForwardUrl("/fail");// 登录失败之后跳转到哪个 url
        http.authorizeRequests()
                .antMatchers("/layui/**","/index") //表示配置请求路径
                .permitAll() // 指定 URL 无需保护。
                .anyRequest() // 其他请求
                .authenticated(); //需要认证
// 关闭 csrf
        http.csrf().disable();
    }
}
