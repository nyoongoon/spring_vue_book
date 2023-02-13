package app.messages;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

//@Configuration //빈을 정의하기 위한 것임을 스프링에게 알려줌
//@ComponentScan("app.messages") //어노테이션이 달린 컴포넌트를 스캔할 기본 패키지를 스프링에 알려줌
public class AppConfig {
    // 필터 설정 -> FilterRegistrationBean<>();
    @Bean
    public FilterRegistrationBean<AuditingFilter> auditingFilterFilterRegistrationBean() {
        FilterRegistrationBean<AuditingFilter> registration = new FilterRegistrationBean<>();
        AuditingFilter filter = new AuditingFilter();
        registration.setFilter(filter);
        registration.setOrder(Integer.MAX_VALUE); // 순서값이 작은 것이 필터 체인의 앞에 위치.
        registration.setUrlPatterns(Arrays.asList("/messages/*"));
        return registration; // 출력결과에 디버그 로그 표시하려면 AuditingFilter에 대한 디버그 레벨 로그 설정 필요. -> application.properties
    }


}
