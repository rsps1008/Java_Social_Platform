package socailPlatform;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests(authorizeRequests ->
                authorizeRequests.anyRequest().permitAll()
            )
            .csrf().disable()  // 如果您不需要 CSRF 防护，可以禁用它
            .formLogin().disable()  // 禁用默认的表单登录
            .httpBasic().disable(); // 禁用默认的 HTTP 基本认证
        return http.build();
    }
}

