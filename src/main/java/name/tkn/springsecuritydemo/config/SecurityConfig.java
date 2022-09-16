package name.tkn.springsecuritydemo.config;

import name.tkn.springsecuritydemo.service.IAclUserService;
import name.tkn.springsecuritydemo.service.impl.AclUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * @description: Security配置拦截适配器类
 * @author: lz
 * @time: 2022/9/15 13:11
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // 注入 PasswordEncoder 类到 spring 容器中
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private DataSource dataSource;
    @Autowired
    private AclUserServiceImpl usersService;
    @Autowired
    private PersistentTokenRepository tokenRepository;


    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new
                JdbcTokenRepositoryImpl();
// 赋值数据源
        jdbcTokenRepository.setDataSource(dataSource);
// 自动创建表,第一次执行会创建，以后要执行就要删除掉！
//        jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
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
                .antMatchers("/layui/**", "/index") //表示配置请求路径
                .permitAll() // 指定 URL 无需保护。
//                .antMatchers("/findAll").hasAuthority("admins")//需要用户有admins权限
//                .antMatchers("/find").hasAnyAuthority("role,admin")//需要用户有role或者admin权限
                .antMatchers("/find").hasRole("admin")//需要用户有admin角色
                .anyRequest() // 其他请求
                .authenticated(); //需要认证
        // 退出
        http.logout().logoutUrl("/logout").logoutSuccessUrl("/index").permitAll();
        // 开启记住我功能
        http.rememberMe()
                .tokenValiditySeconds(60) // 单位秒
                .tokenRepository(tokenRepository)
                .userDetailsService(usersService);

// 关闭 csrf
        http.csrf().disable();
    }
}
