# [Inflearn] 스프링 기본편 강의 공부 (7/10 완강)

#### [Inflearn] 김영한 강사님의 '스프링 핵심 원리 - 기본편' 을 참고하여 공부한 내용을 블로그에 정리하였습니다.

![수료증](https://user-images.githubusercontent.com/74015710/127106125-ec0253a9-11fd-488f-9694-20caf7af157c.png)

* ## Summary

<details>
<summary> Summary#1 Singleton container with Spring , Click toggle for contents </summary>

# 싱글톤(Singleton) 컨테이너

## **싱글톤 이란?**

싱글톤은 객체지향 디자인패턴중 하나이고 이 싱글톤 패턴은 인스턴스를 하나만 생성하도록 보증하는 패턴, 최초로 인스턴스를 생성하면 그 이후에 인스턴스를 계속해서 생성해도 똑같은 인스턴스를 반환시켜준다.

```java
Service service = new Service();
```

클라이언트가 위의 객체를 사용하는 어떤 프로그램을 사용할 때 마다 , 인스턴스를 생성

만약 다수의 클라이언트가 동시에 서비스를 사용한다면?

![Singleton](https://user-images.githubusercontent.com/74015710/129825636-bdb0aea2-96ba-4806-853b-a6e9af7defa0.jpg)


  

만약 A,B,C 3명이 아닌 100,000명 1,000,000명이 이용한다면 그수에 맞는 인스턴스를 생성,소멸해야함, 이는 엄청난 메모리 낭비와 함께 트래픽 문제를 발생시킨다.

**싱글톤패턴(Singleton Pattern) 은 하나의 실객체만을 반환시켜주어 하나의 Service()객체를 모든 클라이언트가 공유해서 쓸 수 있도록 해준다.**

싱글톤패턴에는 여러가지 종류가 있지만 , 그 중 하나를 예시( Eager Initialization , 이른 초기화)를 들어 코드를 짜보면 다음과 같다.
  

![Singleton2](https://user-images.githubusercontent.com/74015710/129825835-a67e5126-e4fb-48e7-8ac8-296b13e94729.jpg)

똑같은 객체를 생성하는것을 확인할 수 있다 
  
![Singleton3](https://user-images.githubusercontent.com/74015710/129825910-8f974468-ec18-4842-81a4-9c888bdecb73.jpg)


## 싱글톤을 사용하는 이유 ,장점

 
    1. 고정된 메모리 영역을 가지고 하나의 인스턴스만 사용 →메모리 절약
    2. 싱글톤 클래스의 인스턴스는 전역변수이기 때문에 다른 클래스의 인스턴스들이 데이터를 공유하기 쉬움
    3. DBCP(DataBase Connection Pool) 과 같은 공통된 객체를 여러개 생성해야 하는 상황에 자주 사용

## 싱글톤의 단점

 
    1. 구현하는 코드가 많아짐
    2. **DIP(Dependency Inversion Principal , 의존 관계 역전 원리)** 위반
    3. **OCP(Open Closed Principal , 개방 폐쇄의 원리)** 위반

   ![Singleton 4jpg](https://user-images.githubusercontent.com/74015710/129827311-a3a771f9-72f2-43dc-a889-c5d3e4fd9873.png)

    SingletonTest는 결국 SingletonService라는 구현체에 의존 , DIP는 인터페이스에 의존해야함

    구체 클래스에 의존하다보니 변경과 수정에 자유롭지 못함 따라서 OCP 위반


     객체지향과는 거리가 먼 패턴

     객체지향과는 맞지않는 패턴 , 싱글톤으로 설계하면 필연적으로 다른 서비스들과의 의존성이 올라가기 때문에 테스트가 용이하지않다. 

     그렇다면 객체지향의 원칙도 지키면서 , 싱글톤 패턴을 써서 객체를 구현하려면 어떻게 해야할까

## 개발자들에게 찾아온 봄 , Spring

### **Spring의 객체관리 - Spring Container**

**스프링 컨테이너는 기본적으로 싱글톤 컨테이너 역할을 한다.** 

빈을 등록할때 별다른 설정을 하지않으면 기본적으로 싱글톤방식으로 객체를 생성한다.

이는 스프링이 직접 싱글톤 형태의 오브젝트를 만들고 관리하는 기능을 제공하는 **싱글톤 레지스트리**를 갖고 있기 때문이다. ( 싱글톤과는 대비되는 프로토타입으로 설정도 가능)

스프링의 싱글톤 컨테이너는 기존의 싱글톤패턴의 스태틱 메소드와 private 생성자를 사용해야하는 비정상적인(인용) 클래스가 아니라 평범한 자바 클래스를 싱글톤으로 활용하게 해준다.

## 스프링 컨테이너의 생성 & 테스트

### 1) DI 컨테이너 + @Configuration

**제어를 역전하는 DI 컨테이너에 각각 서비스를 구성하는 객체의 의존관계를 주입하여 생성하는 메서드를 생성후 @Bean 을 붙여 반환되는 객체를 스프링 컨테이너에 빈으로 등록**

스프링 컨테이너는 @Configuration 이 붙은 AppConfig 를 설정(구성) 정보로 사용한다. 여기서 @Bean
이라 적힌 메서드를 모두 호출해서 반환된 객체를 스프링 컨테이너에 등록한다. 이렇게 스프링 컨테이너에
등록된 객체를 스프링 빈이라 한다

![7_15_Singleton_](https://user-images.githubusercontent.com/74015710/129828088-f4e00df8-4912-4ab9-8242-6d3bbfd8e053.png)

DI 컨테이너 ,  IoC 컨테이너 (제어의 역전) 를 생성,의존성 주입을 해줌 그리고 @Bean 으로 객체들을 스프링 컨테이너에 등록

### 2) 스프링 컨테이너 생성

  **스프링 컨테이너 생성  -> new AnnotationConfigApplicationContext(AppConfig.class)**



AppConfig.class 는 구성정보로써 스프링 컨테이너를 만들시 꼭 필요                                                                       ![7_14_Singleton__1](https://user-images.githubusercontent.com/74015710/129827966-7c9783e0-bd48-4e75-98fa-a466daf21040.png)


### 3) 의존관계 설정

![7_14_Singleton__3](https://user-images.githubusercontent.com/74015710/129828237-64e5f8bc-3dc1-43df-8ae8-a02dfdf35e9c.png)

### 4) **테스트결과**

![7_15_Singleton_4](https://user-images.githubusercontent.com/74015710/129828245-213ddcfc-f680-4e7a-ac8c-386960a2b7b7.png)

## 싱글톤(스프링) 컨테이너의 주의점

**무상태성(Stateless)으로 설계해야함** , 상태를 유지하는 필드가 존재하면 무시무시한 일이 발생

### EX) 상태성(stateful)로 설계한 객체를 스프링빈에 담아서 테스트

price 같이 값이 상태를 유지하는 필드를 가진 객체를 생성

![7_15_Singleton_5](https://user-images.githubusercontent.com/74015710/129828254-62e49114-ceb3-4581-b14d-b0fa53f78ce3.png)

해당 객체를 빈으로 등록후 스프링 컨테이너를 테스트

![7_15_Singleton_ 6png](https://user-images.githubusercontent.com/74015710/129828270-daf96baf-81e6-4482-a14e-706cbc901ffd.png)

여러 클라이언트가 하나의 같은 객체 인스턴스를 공유하기 때문에 싱글톤 객체는 상태를 유지
(stateful)하게 설계하면 안된다

따라서 상태를 유지하는 price필드로 인하여 A사용자는 10000원 짜리 상품을 구매하고 , 나중손님이 20000원 짜리 상품을 구매하면 20000원을 결제해야하는 위험한 상황을 초래

## 정리

1. 객체를 하나만 생성하여 클라이언트가 공유해야 하는 상황에서 싱글톤패턴은 적절한 디자인패턴 하지만 객체지향의 특성을 살리기 어렵고 테스트가 쉽지않다는 단점이 존재

1. Springframework의 스프링 컨테이너(싱글톤 컨테이너)는 객체들을 싱글톤으로 관리해주며 , 객체지향의 원칙을 위반하지 않게하여 개발자들의 개발을 더욱 편하게 해준다.

1. 단, DBCP 같은 다수의 클라이언트가 공동으로 사용하며 , 반복적으로 사용되는 객체에 사용되어야 하고 , 무상태성으로 설계하여야한다.

# **자료출처**

- [Inflearn] 스프링 핵심 원리 - 기본편

[스프링 핵심 원리 - 기본편 - 인프런 | 강의](https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81-%ED%95%B5%EC%8B%AC-%EC%9B%90%EB%A6%AC-%EA%B8%B0%EB%B3%B8%ED%8E%B8)

- 블로그

[싱글턴 패턴(Singleton Pattern)] (URL https://webdevtechblog.com/%EC%8B%B1%EA%B8%80%ED%84%B4-%ED%8C%A8%ED%84%B4-singleton-pattern-db75ed29c36)

[자바 디자인 패턴의 이해 - Gof Design Pattern[1~11]] (URL https://catsbi.oopy.io/344dbe7b-9774-48fc-9c95-b554e9c1c4bc#a1cfdedc-4a6f-44da-916a-13edfb67c73d)

[[Spring] 싱글톤 레지스트리] (URL https://withseungryu.tistory.com/74)
  
  </details>

<details>
  
* * *
  
<summary> 업로드 예정 ...  </summary>
```
CODE!
```
</details>

<details>
<summary> 업로드 예정 ... </summary>
```
CODE!
```
</details>



 * [싱글톤(스프링) 컨테이너.pdf](https://github.com/GRIFFITHH/core/files/6883196/default.pdf)

 * [컴포넌트 스캔과 자동 의존 관계 주입.pdf](https://github.com/GRIFFITHH/core/files/6883188/_._._._._.pdf)

 * [빈의_생명주기.pdf](https://github.com/GRIFFITHH/MySpringStudies/files/6912836/_.pdf)
