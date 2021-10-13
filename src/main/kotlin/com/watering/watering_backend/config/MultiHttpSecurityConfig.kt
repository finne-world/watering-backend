package com.watering.watering_backend.config

import com.watering.watering_backend.domain.constant.Authority
import com.watering.watering_backend.domain.security.filter.ExceptionHandlerFilter
import com.watering.watering_backend.domain.security.filter.JwtAuthenticationTokenFilter
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@EnableWebSecurity
class MultiHttpSecurityConfig: WebMvcConfigurer {
    @Order(1)
    @Configuration
    class ApiWebSecurityConfigurerAdapter(
        private val passwordEncoder: PasswordEncoder,
        private val userDetailsService: UserDetailsService,
        private val unauthorizedHandler: AuthenticationEntryPoint,
        private val exceptionHandlerFilter: ExceptionHandlerFilter,
        private val jwtAuthenticationTokenFilter: JwtAuthenticationTokenFilter
    ): WebSecurityConfigurerAdapter() {
        override fun configure(http: HttpSecurity) {
            http.antMatcher("/api/**")
                .cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(this.unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers("/api/authentication/signin", "/api/authentication/signup", "/api/authentication/refresh_token").permitAll()
                .anyRequest().hasRole(Authority.SERVICE.name)

            http.addFilterBefore(this.jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter::class.java)
                .addFilterBefore(this.exceptionHandlerFilter, JwtAuthenticationTokenFilter::class.java)
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
                    .antMatchers(
                        "/",
                        "/users/signup",
                    ).permitAll()
                    .antMatchers("/**").hasRole(Authority.USER.name)
                    .antMatchers("/admin/**").hasRole(Authority.ADMIN.name)
                .and()
                .formLogin()
                    .loginPage("/users/signin")
                    .loginProcessingUrl("/users/signin")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .defaultSuccessUrl("/users/profile")
                    .failureUrl("/users/signin?failed=true")
                    .permitAll()
        }

        override fun configure(web: WebSecurity) {
            web.ignoring().antMatchers("/webjars/**")
        }
    }

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/")
    }
}