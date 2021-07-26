package com.watering.watering_backend.config

import com.watering.watering_backend.domain.constant.Authority
import com.watering.watering_backend.domain.security.filter.JwtAuthenticationTokenFilter
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class MultiHttpSecurityConfig {
    @Order(1)
    @Configuration
    class ApiWebSecurityConfigurerAdapter(
        private val passwordEncoder: PasswordEncoder,
        private val userDetailsService: UserDetailsService,
        private val unauthorizedHandler: AuthenticationEntryPoint,
        private val jwtAuthenticationTokenFilter: JwtAuthenticationTokenFilter
    ): WebSecurityConfigurerAdapter() {
        override fun configure(http: HttpSecurity) {
            http.antMatcher("/api/**")
                .cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(this.unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers("/api/authentication/signin", "/api/authentication/signup").permitAll()
                .anyRequest().hasRole(Authority.SERVICE.name)

            http.addFilterBefore(this.jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter::class.java)
        }

        override fun configure(auth: AuthenticationManagerBuilder) {
            auth.userDetailsService(this.userDetailsService).passwordEncoder(this.passwordEncoder)
        }
    }

    @Order(2)
    @Configuration
    class ViewWebSecurityConfigurerAdapter: WebSecurityConfigurerAdapter() {
        override fun configure(http: HttpSecurity) {
            http.authorizeRequests()
                .antMatchers("/**").hasRole(Authority.USER.name)
                .antMatchers("/admin/**").hasRole(Authority.ADMIN.name)
                .antMatchers("/").permitAll()
        }
    }
}