package com.marcinsz1993.eventmanagementkotlin.configuration

import com.marcinsz1993.eventmanagementkotlin.filter.JwtAuthenticationFilter
import com.marcinsz1993.eventmanagementkotlin.service.UserDetailsImpl
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.reactive.function.client.WebClient


@EnableAsync
@Configuration
@EnableCaching
@EnableWebSecurity

class SecurityConfig(
    private val userDetailsImpl: UserDetailsImpl,
    private val jwtAuthenticationFilter: JwtAuthenticationFilter
) {
    @Bean
    @Throws(Exception::class)
    fun securityFilterChain(httpSecurity: HttpSecurity): SecurityFilterChain {
        return httpSecurity.authorizeHttpRequests { registry ->
            registry.requestMatchers("/users/**").permitAll()
            registry.requestMatchers("/login/").permitAll()
            registry.requestMatchers("/weather").hasAnyRole("USER", "ADMIN")
            registry.requestMatchers("/swagger-ui/**").permitAll()
            registry.requestMatchers("/v3/api-docs/**").permitAll()
            registry.requestMatchers("/events").hasAnyRole("USER", "ADMIN")
            registry.anyRequest().authenticated()
        }
            .csrf { obj: CsrfConfigurer<HttpSecurity> -> obj.disable() }
            .userDetailsService(userDetailsImpl)
            .sessionManagement { session: SessionManagementConfigurer<HttpSecurity?> ->
                session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    @Throws(Exception::class)
    fun authenticationManager(configuration: AuthenticationConfiguration): AuthenticationManager {
        return configuration.authenticationManager
    }

    @Bean
    fun webClient(): WebClient {
        return WebClient.create()
    }
}
