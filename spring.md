# 스프링 제어의 역전(IoC)과 의존성 주입(DI)
## 의존성 관리
- 스프링에서는 룩업패턴을 활용해 의존성들을 찾아 배치함.
- 생성자 또는 세터를 통해 스프링이 의존성을 주입함. => IoC & DI
- 스프링은 개발자가 작성한 설정 메타 데이터를 사용하여 의존성을 주입(애노테이션 기반)
- 스프링 컨테이너 설정 Class == AnnotationConfigApplicatoinContext (ApplicationContext 인터페이스 구현체)
## 빈(Bean)
- 스프링 규약에 의해 스프링 컨테이너가 관리하는 객체를 보통 빈이라고 부름.
- 스프링 컨테이너에 빈을 등록할 때 기본으로 싱글톤으로 등록한다. -> 따라서 대부분 같은 스프링 빈이면 같은 인스턴스임!
### 스프링 빈을 등록하는 두가지 방법
- 1. 컴포넌트 스캔과 자동의존관계설정(애노테이션 붙이기) 2. 자바코드로 직접 빈 등록(@Bean붙힌 메소드로 생성 후 반환.)
#### 1. 컴포넌트 스캔과 자동 의존관계설정(스프링 컨테이너 관리를 위한 어노테이션 설정)
- @Component, @Service, @Controller, @Repository
- 클래스에 위 어노테이션이 적용돼 있으면 스프링은 해당 클래스를 인스턴스화한다.
- cf) 스프링은 @ComponentScan 어노테이션에 입력한 기본 패키지부터 스캔해 해당 어노테이션이 달린 클래스를 수집함.
- cf) 의존관계가 실행중에 동적으로 변하는 경우는 거의 없으므로 생성자 주입을 권장 !!
##### @SpringBootApplication // 스프링이 자동 설정을 수행(autoconfiguration)
- 스프링이 자동 설정을 수행하도록 함.
- https://tecoble.techcourse.co.kr/post/2021-10-14-springboot-autoconfiguration/
- @SpringBootApplication 은 아래 3가지 어노테이션을 합친 것이다.

- @SpringBootConfiguration : @Configuration과 같은 기능을 한다.
- @EnableAutoConfiguration : classpath에 있는 /resource/META-INF/spring.factories 중 EnableAutoConfiguration 부분에 정의된 Configuration들을 자동으로 등록한다. 이때, 모든 경우가 적용되는 것이 아니라, AutoConfiguration의 등록 조건을 만족하는 경우에만 등록된다. --debug 옵션을 가지고 jar파일을 실행시키면 조건 만족 여부와 함께, 어떤 Bean들이 등록되는지 확인해 볼 수 있다.
- @ComponentScan : base-package가 정의되지 않으면 해당 어노테이션이 붙은 classpath 하위의 @Component 어노테이션을 스캔해서, Bean으로 등록한다.
이때, 개발자가 정의한 Component들이 먼저 스캔 돼서 Bean으로 등록된 후에 AutoConfiguration의 Bean들이 등록된다. 해당 등록 부분을 확인하고 싶으면 ConfigurationClassParser 클래스의 doProcessConfigurationClass 메소드를 디버깅을 해보면 확인해 볼 수 있다.
#### 2. 자바코드로 직접 빈 등록(@Bean붙힌 메소드로 생성 후 반환.)
```java
@Configuration
public class SpringConfig{
    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository);
    }
    @Bean
    public MemberRepository memberRepository(){
        return new MemberRepository();
    }
}
``` 
- **실무에서는 정형화된 컨트롤러, 서비스, 리포지토리 같은 코드는 컴포넌트 스캔을 이용!!** (@Component-클래스)
- **정형화되지 않거나 상황에따라 구현 클래스를 변경해야하면 설정을 통해 스프링 빈으로 등록한다!** (@Bean-메소드)



### 의존성 연결
- 의존성을 연결하는 데는 @Autowired를 사용할 수 있음.
- 생성자 기반 의존성 주입에는 @Autowired 생략 가능 !!!
#### 생성자 기반 의존성 주입
- 반드시 필요한 의존성은 항상 생성자를 통해 주입해야하!
- 생성자 의존성 주입하여 인스턴스는 완전히 초기화되고 주입된 의존성은 읽기전용이 됨.

# 프로젝트 주요 Class
## AppConfig.java (스프링 설정 클래스)
- 스프링이 컨테이너를 인스턴스화 하는데 사용할 설정 메타 데이터
### @Configuration
- 이 클래스가 빈을 정의하기 위한 것임을 스프링에게 알려주는 어노테이션
### @ComponentScan
- 어노테이션이 달린 컴포넌트를 스캔할 기본 패키지를 스프링에게 알려줌.

## Application.java
- 애플리케이션의 시작점이며 main() 메소드가 있음.


# 서블릿
- 톰캣과 같은 애플리케이션 서버인 서블릿 컨테이너 내에 동작
- 모든 HTTP 요청에 대해 HttpServletRequest 인스턴스가 생성, 모든 HTTP 응답에 대해 HttpServletResponse 인스턴스가 생성.
## 사용자 식별 (HttpSession)
- 사용자 식별을 위해 애플리케이션 서버는 첫 번째 요청을 받으면 **HttpSession 인스턴스를 생성**함.
- HttpSession 인스턴스는 세션ID라는 ID를 갖음.
- **세션ID**는 HTTP 응답 헤더의 **클라이언트 쿠키로 담겨 전송됨**.
- 클라이언트는 그 쿠키를 저장하고, 다음 요청 시 다시 서버로 보냄. 
- 이렇게 해서 서버는 쿠키에서 찾은 세션 ID로 HttpSession 인스턴스를 조회해 사용자를 인식할 수 있음.
- HttpSessionListener 인터페이스를 구현해 HttpSession 라이프 사이클 이벤트를 수신 가능
- ServletRequestListener 인터페이스를 구현해 요청에 대한 라이프 사이클 이벤트를 수신 가능.

## DispatcherServlet
- 스프링 MVC를 사용하면 서블릿 생성 필요 X
- 스프링은 모든 요청을 받아 DispatcherServlet에 넘겨줌. 
- DispatcherServlet은 @RequestMapping 어노테이션에 지정된 URI 패턴에 따라 요청을 처리할 패턴에 맞는 컨트롤러를 찾음.


# 스프링 MVC

## Model
- 모델은 컨트롤러가 생성하고 HTTP 응답을 통해 클라이언트에 전송되어 **뷰가 최종 결과를 렌더링하는데 사용할 수 있는 데이터를 포함**함.


# 필터
- 필터는 디자인 패턴인 책임 연쇄 패턴을 구현한 것.
- 서블릿에 도달하기 전에 HTTP 요청에 대한 필터리 작업을 수행하려 할 때 유용함.
- javax.servlet.Filter 인터페이스를 구현 
- org.springframework.web.filter.**GenericFilterBean**을 확장해서 필터를 만들 수도 있음. 
- chian.doFilter()는 체인에 실행할 필터가 존재하면 추가 필터를 호출할 수 있도록 함.
- **chain.doFilter()호출하지 않으면 클라이언트에 어떠한 응답도 보내지 못함 !!!**
- chain.doFilter()를 호출한 이후에도 여전히 추가작업을 수행할 수 있음.
## 스프링에 필터 등록하기
- FilterRegistrationBean을 만들어 설정 클래스에 등록(@Bean 설정)!



# JPA (Java Persistence API)
- JPA는 자바 객체의 영속성을 위한 자바의 표준화된 접근 방식을 정의함.
## javax.sql.DataSource
- spring-boot-starter-jdbc 모듈을 사용 =>스프링이 DataSource의 인스턴스를 빈으로 설정함.
- application.properties에서 **DataSource를 인스턴스화 하는데 필요한 매개변수를 설정**
## 하이버네이트 
### 하이버네이트 라이브러리 설정
```maven
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-orm</artifactId>
</dependency>
<dependency>
    <groupId>org.hibernate</groupId>
    <artifactId>hibernate-core</artifactId>
</dependency>
```
- spring-orm : 하이버네이트와 같은 ORM기술을 기반으로 하는 스프링의 ORM을 지원한다.
- hibernate-core : 라이브러리는 하이버네이트 ORM 프레임워크
### 하이버네이트 - 작동 설명
- 하이버네이트는 기본 생성자를 필요로함
- 하이버네이트가 데이터베이스에서 레코드를 가져올 때 Message 객체를 재구성하는데, 이때 객체를 생성하기위한 기본 생성자를 사용

### 주의점
#### 하이버네이트 데이터검증
- 하이버네이트는 @Column의 nullable 설정과 length 속성을 기반으로 데이터 검증 수행하지 않는다.
- hbm(Hibernate mapping)으로부터 DDL을 생성하는 **hbm2ddl에서 이 메타데이터를 사용**한다. 


### 하이버네이트 - JPA 어노테이션 설명 - p.124
#### .Entity
#### .Table
#### .Id
#### .GeneratedValue
#### .Column
#### .Temporal



## cf) 그외 방법
### JDBC 드라이버
- spring-boot-starter-jdbc 모듈을 사용 -> **스프링은 javax.sql.DataSource의 인스턴스를 기동**, **스프링 컨테이너에서 빈으로** 사용할 수 있게 해줌.
- 또한, 데이터베이스 커넥션 풀을 설정함.

### 스프링 JDBC (JdbcTemplate 클래스)
- 스프링 JDBC는 JDBC API 위의 추상화 계층을 제공함. 
- 핵심인 **JdbcTemplate 클래스는 연결을 관리하고 JDBC API와 상호작용**하는 워크플로를 제공하는데 도움을 줌.
- **NamedParameterJdbcTemplate** 클래스는 JdbcTemplate 객체를 래핑한 클래스로 JDBC의 "?" 플레이스 홀더 대신에 **이름을 지정한 매개변수 사용** 기능 제공.




# HTTP 
## 애노테이션
### @RequestBody
- @RequestBody가 매개변수에 붙여질 떄 
- 매개변수에 @RequestBody를 붙이면 HTTP 요청 본문에 전달된 JSON형식 string을 DTO 인스턴스로 변환함!!
```java
@PostMapping("/messages")
@RequestBody //응답을 json으로 변환하여 http응답본문에 담아 리턴
public ResponseEntity<Message> saveMessage(@RequestBody MessageDate data){
    //매개변수에 @RequestBody를 붙이면 HTTP요청본문에 전달된 JSON형식 string을 DTO 인스턴스로 변환함!!
    Message saved = messageService.save(data.getText);
    if(saved == null){
        return ResponseEntity.status(500).build();
    }
    return ResponseEntity.ok(saved);
    }
```