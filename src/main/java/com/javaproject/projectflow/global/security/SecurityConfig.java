package com.javaproject.projectflow.global.security;

import com.javaproject.projectflow.domain.user.UserRepository;
import io.netty.handler.codec.base64.Base64Encoder;
import lombok.RequiredArgsConstructor;
import org.bson.internal.Base64;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.messaging.handler.invocation.reactive.ArgumentResolverConfigurer;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.messaging.rsocket.annotation.support.RSocketMessageHandler;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.rsocket.EnableRSocketSecurity;
import org.springframework.security.config.annotation.rsocket.RSocketSecurity;
import org.springframework.security.messaging.handler.invocation.reactive.AuthenticationPrincipalArgumentResolver;
import org.springframework.security.messaging.handler.invocation.reactive.CurrentSecurityContextArgumentResolver;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtReactiveAuthenticationManager;
import org.springframework.security.rsocket.authentication.BearerPayloadExchangeConverter;
import org.springframework.security.rsocket.authentication.PayloadExchangeAuthenticationConverter;
import org.springframework.security.rsocket.core.PayloadSocketAcceptorInterceptor;
import org.springframework.web.util.pattern.PathPatternRouteMatcher;

import javax.crypto.spec.SecretKeySpec;

@RequiredArgsConstructor
@Configuration
@EnableRSocketSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {

    @Value("${jwt.secret}")
    private String secret;

    @Bean
    public PayloadExchangeAuthenticationConverter authenticationConverter() {
        return new BearerPayloadExchangeConverter();
    }

    private ReactiveJwtDecoder reactiveJwtDecoder() {
        SecretKeySpec secretKeySpec = new SecretKeySpec(Base64.encode(secret.getBytes()).getBytes(), MacAlgorithm.HS256.getName());

        return NimbusReactiveJwtDecoder.withSecretKey(secretKeySpec)
                .macAlgorithm(MacAlgorithm.HS256)
                .build();
    }

    private ReactiveAuthenticationManager authenticationManager() {
        return new JwtReactiveAuthenticationManager(reactiveJwtDecoder());
    }

    @Bean
    PayloadSocketAcceptorInterceptor authenticationConverter(RSocketSecurity security) {
        return security
                .authorizePayload(authorizePayloadsSpec -> authorizePayloadsSpec
                        .setup().permitAll()
                        .anyExchange().authenticated())
                .jwt(jwtSpec -> jwtSpec.authenticationManager(authenticationManager()))
                .build();
    }


    // TODO: 2021-08-31 어디부터 어디까지 필요한지 확인
    @Bean
    RSocketMessageHandler messageHandler(RSocketStrategies rSocketStrategies, BeanFactory beanFactory) {
        BeanFactoryResolver resolver = new BeanFactoryResolver(beanFactory);
        AuthenticationPrincipalArgumentResolver principal = new AuthenticationPrincipalArgumentResolver();
        principal.setBeanResolver(resolver);
        CurrentSecurityContextArgumentResolver context = new CurrentSecurityContextArgumentResolver();
        context.setBeanResolver(resolver);
        RSocketMessageHandler messageHandler = new RSocketMessageHandler();
        messageHandler.setRSocketStrategies(rSocketStrategies);
        messageHandler.setRouteMatcher(new PathPatternRouteMatcher());
        ArgumentResolverConfigurer args = messageHandler
                .getArgumentResolverConfigurer();
        args.addCustomResolver(principal);
        args.addCustomResolver(context);
        return messageHandler;
    }

}
