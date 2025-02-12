package kory.spring.com.bekoryfurniture.config;

import kory.spring.com.bekoryfurniture.exception.JwtAuthenticationEntryPoint;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;


@Configuration
@EnableWebSecurity
@EnableSpringDataWebSupport()
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private CustomerJwtDecoder customerJwtDecoder;

    private final String[] GET_PUBLIC_ENDPOINTS = {
            "/api/v2/category",
            "/api/v2/customer/{id}",
            "/api/v2/comment",
            "/api/v2/product",
            "/api/v2/product/{id}",
            "/api/v2/shopping-cart/customer/{customerId}"
    };

    private final String[] POST_PUBLIC_ENDPOINTS = {
            "/api/auth/login",
            "/api/auth/logout",
            "/api/auth/introspect",
            "/api/auth/change-password",
            "/api/v2/comment",
            "/api/v2/customer",
            "/api/v2/order",
            "/api/create-payment-intent",
            "/api/v2/shopping-cart"
    };

    private final String[] PUT_PUBLIC_ENDPOINTS = {
            "/api/v2/comment",
            "/api/v2/customer",
            "/api/v2/customer/{id}",
    };

    private final String[] DELETE_PUBLIC_ENDPOINTS = {
            "/api/v2/shopping-cart/{id}"
    };

    private final String[] GET_ADMIN_ENDPOINTS = {
            "/api/v2/admin/**",
            "/api/v2/customer",
            "/api/v2/order/**"
    };

    private final String[] POST_ADMIN_ENDPOINTS = {
            "/api/v2/admin",
            "/api/v2/category",
    };

    private final String[] PUT_ADMIN_ENDPOINTS = {
            "/api/v2/admin",
            "/api/v2/product",
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{

        httpSecurity
                .authorizeHttpRequests(request ->
//                        request.requestMatchers("/**").permitAll()
                        request.requestMatchers(HttpMethod.GET, GET_ADMIN_ENDPOINTS)
                                .hasAnyAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.POST, POST_ADMIN_ENDPOINTS)
                                .hasAnyAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.PUT, PUT_ADMIN_ENDPOINTS)
                                .hasAnyAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/v2/admin", "/api/v2/product", "/api/v2/customer")
                                .hasAnyAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.POST, POST_PUBLIC_ENDPOINTS).permitAll()
                                .requestMatchers(HttpMethod.GET, GET_PUBLIC_ENDPOINTS).permitAll()
                                .requestMatchers(HttpMethod.PUT, PUT_PUBLIC_ENDPOINTS)
                                .hasAnyAuthority("ROLE_USER")
                                .requestMatchers(HttpMethod.DELETE, DELETE_PUBLIC_ENDPOINTS)
                                .hasAnyAuthority("ROLE_USER")
                                .anyRequest().authenticated()
                );

        httpSecurity.exceptionHandling(exceptionHandling ->
                exceptionHandling.authenticationEntryPoint(jwtAuthenticationEntryPoint));

        httpSecurity.oauth2ResourceServer(oauth2 ->
                oauth2.jwt(jwtConfigurer -> jwtConfigurer.decoder(customerJwtDecoder)
                        .jwtAuthenticationConverter(jwtAuthenticationConverter()))

        );

        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.cors((cors) -> cors
                .configurationSource(corsConfigurationSource())
        );

        return httpSecurity.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:5000"));
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new
                UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter(){
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);

        return jwtAuthenticationConverter;
    }

}
