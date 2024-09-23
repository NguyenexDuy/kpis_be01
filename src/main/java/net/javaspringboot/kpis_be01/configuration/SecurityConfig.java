package net.javaspringboot.kpis_be01.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final String[] PUBLIC_ENDPOINTS={"/auth/login","/auth/introspect"};
    protected static  final   String SIGNER_KEY="A7uenFLhLY3uiBQjNWiwPlrCT1enwNHniqLq3TqwPQApo+MGZ6YsrBv9xNpFm0PY";
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        //cho nay can phai co token moi truy cap duoc cac phuong thu get, put, delete
        httpSecurity.authorizeHttpRequests(request ->
                // cho phep su dung post voi tat ca quyen
                request.requestMatchers(HttpMethod.POST,PUBLIC_ENDPOINTS).permitAll()
                .requestMatchers(HttpMethod.GET,"/manager/")
                        .hasAuthority("manager")
                        .requestMatchers(HttpMethod.GET,"/staff").permitAll()
                        .anyRequest().authenticated());
        httpSecurity.oauth2ResourceServer(oauth2-> oauth2.jwt(jwtConfigurer ->
                jwtConfigurer.decoder(jwtDecoder())
                        //customise
                        .jwtAuthenticationConverter(jwtAuthenticationConverter())));

        httpSecurity.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable());
        return httpSecurity.build();
    }

    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter(){
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter=new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("");
        JwtAuthenticationConverter jwtAuthenticationConverter=new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return  jwtAuthenticationConverter;
    }
    @Bean
    JwtDecoder jwtDecoder(){
        SecretKeySpec secretKeySpec=new SecretKeySpec(SIGNER_KEY.getBytes(),"HS512");
        return NimbusJwtDecoder
                .withSecretKey(secretKeySpec)
                .macAlgorithm(MacAlgorithm.HS512)
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }
}
