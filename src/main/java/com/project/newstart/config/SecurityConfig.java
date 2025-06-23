package com.project.newstart.config;

import com.project.newstart.dto.CustomUserDetails;
import com.project.newstart.entity.UserEntity;
import com.project.newstart.repository.UserRepository;
import com.project.newstart.service.CustomOAuth2UserService;
import com.project.newstart.service.CustomUserDetailsService;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.http.HttpMethod;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomUserDetailsService customUserDetailsService;
    private final UserRepository userRepository;

    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService, CustomUserDetailsService customUserDetailsService, UserRepository userRepository) {
        this.customOAuth2UserService = customOAuth2UserService;
        this.customUserDetailsService = customUserDetailsService;
        this.userRepository = userRepository;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web
                .ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true);
        config.setAllowedOriginPatterns(List.of(
                "https://newstart-project-444411.web.app",
                "https://backend-7eac6k6zia-du.a.run.app"
        )); // React 배포 URL 허용
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")); // 모든 HTTP 메서드 허용
        config.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept", "X-Requested-With", "Origin")); // 모든 헤더 허용
        config.setExposedHeaders(List.of("Authorization", "Content-Type")); // 모든 응답 헤더 허용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // 모든 경로에 대해 CORS 설정 적용
        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // CSRF 비활성화
        http.csrf(csrf -> csrf.disable());

        // HTTP Basic 인증 방식 비활성화
        http.httpBasic(httpBasic -> httpBasic.disable());

        // CORS 설정 적용
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));

        // 권한 설정
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // OPTIONS 메서드 허용
                .requestMatchers(HttpMethod.GET, "/api").permitAll() // /api 경로에 대한 GET 요청은 인증 없이 접근 허용
                .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                .requestMatchers(
                        "/",
                        "/**",
                        "/login",
                        "/api/login/**",
                        "/api/oauth2/**",
                        "/api/auth/**",
                        "/api/css/**",
                        "/api/images/**",
                        "/api/js/**",
                        "/api/logout/**",
                        "/api/posts/**",
                        "/api/comments/**",
                        "/api/error/**"
                ).permitAll() // /api 경로에 대한 모든 요청 허용
                .anyRequest().authenticated() // 그 외 모든 요청은 인증 필요
        );

        // 인증되지 않은 사용자의 리다이렉트 설정
        http.exceptionHandling(exception -> exception
                .authenticationEntryPoint((request, response, authException) -> {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setContentType("application/json");
                    response.getWriter().write("{\"error\": \"Unauthorized\", \"message\": \"인증이 필요합니다.\"}");
                })
        );

        // OAuth2.0 설정
        http.oauth2Login(oauth2 -> oauth2
                .loginPage("https://newstart-project-444411.web.app/login")
                .defaultSuccessUrl("https://newstart-project-444411.web.app/") // 로그인 성공 후 리다이렉트
                .userInfoEndpoint(userInfo -> userInfo
                        .userService(customOAuth2UserService))
        );

        // Form 로그인 방식 설정
        http.formLogin(form -> form
                .loginPage("https://newstart-project-444411.web.app/auth/email/login")
                .loginProcessingUrl("/api/auth/email/loginProcess") // /api 경로로 변경
                .successHandler((request, response, authentication) -> {

                    //유저 아이디
                    String name = authentication.getName();
                    //아이디로 유저 엔티디 찾아오기
                    UserEntity user_entity = userRepository.findByUsername(name);
                    Long user_id = user_entity.getId();


                    // CORS 헤더 설정 제거 (rewrites를 통해 동일 출처로 요청 처리)
                    //response.sendRedirect("https://newstart-project-444411.web.app/"); // 성공 시 React 앱으로 리디렉션
                    //성공 시 JSON 형태로 프론트에 OK 반환
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write("{\"statusCode\":\"OK\",\n");
                    response.getWriter().write("\t\"userId\":"+user_id+"}");
                    response.getWriter().flush();
                })
                .failureHandler((request, response, exception) -> {
                    // CORS 헤더 설정 제거 (rewrites를 통해 동일 출처로 요청 처리)
                    response.sendRedirect("https://newstart-project-444411.web.app/auth/email/login?error"); // 실패 시 React 로그인 페이지로 리디렉션
                })
                .permitAll()
        );

        // Logout 설정
        http.logout(logout -> logout
                .logoutUrl("/api/logout") // /api 경로로 변경
                .logoutSuccessUrl("https://newstart-project-444411.web.app/")
                .deleteCookies("JSESSIONID", "remember-me")
                .permitAll()
        );

        // 세션 관리 설정
        http.sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .sessionFixation(sessionFixation -> sessionFixation.newSession())
                .maximumSessions(1)
                .maxSessionsPreventsLogin(true)
        );

        return http.build();
    }
}
