## 1. Що таке Inversion of Control та Dependency injection в рамках фреймворку спрінг?
---
**Spring IoC (Inversion of Control) Container** is the core of Spring Framework. It creates the objects, configures and assembles their dependencies, manages their entire life cycle. The Container uses Dependency Injection(DI) to manage the components that make up the application. It gets the information about the objects from a configuration file(XML) or Java Code. 

**Dependency Injection** is the main functionality provided by Spring IoC. The Spring-Core module is responsible for injecting dependencies through either Constructor or Setter methods. The design principle of Inversion of Control emphasizes keeping the Java classes independent of each other and the container frees them from object creation and maintenance. Dependency Injection in Spring also ensures loose coupling between the classes.

## 2. Яка різниця між Dependency Injection та Dependency Inversion(SOLID)?
---
- Dependency Inversion is one of the SOLID principles and focuses on the relationship between high-level modules and low-level modules. It suggests that high-level modules should not depend on low-level modules directly, but both should depend on abstractions.
- Dependency Injection is a design pattern that facilitates the implementation of Dependency Inversion. It involves supplying a class’s dependencies from the outside rather than creating them internally. This promotes loose coupling and easier testing.

## 3. Які види конфігурацій спрінг додатку існують? (Навести приклад)
---
- XML - based configuration:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans" ...>

    <bean id="accountService" class="com.wiley.beginningspring.ch2.AccountServiceImpl">
        <property name="accountDao" ref="accountDao"/>
    </bean>
    
    <bean id="accountDao" class="com.wiley.beginningspring.ch2.AccountDaoInMemoryImpl">
    </bean>

</beans>
```

```java
ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("/com/wiley/beginningspring/ch2/ch2-beans.xml");
```

- Java Based Configuration:
```java
@Configuration
public class Ch2BeanConfiguration {

    @Bean
    public AccountService accountService() {
        AccountServiceImpl bean = new AccountServiceImpl();
        bean.setAccountDao(accountDao());
        return bean;
    }
    
    @Bean
    public AccountDao accountDao() {
        AccountDaoInMemoryImpl bean = new AccountDaoInMemoryImpl();
        return bean;
    }
}
```

```java
ApplicationContext applicationContext
            = new AnnotationConfigApplicationContext(Ch2BeanConfiguration.class);
```

- Groovy Based Configuration:
```groovy
@Configuration
@ComponentScan(basePackages = "com.sysgears.example",
        scopeResolver = Jsr330ScopeMetadataResolver.class)
class AppConfig {

    private @Inject Environment env
    
    public @Bean GreetInfo createGreetInfo() {
        env.getProperty("greetInfo", GreetInfo.class)
    }
}
```

## 4. Які існують Bean Scopes?
---

|Scope|Description|
|---|---|
|[singleton](https://docs.spring.io/spring-framework/reference/core/beans/factory-scopes.html#beans-factory-scopes-singleton)|(Default) Scopes a single bean definition to a single object instance for each Spring IoC container.|
|[prototype](https://docs.spring.io/spring-framework/reference/core/beans/factory-scopes.html#beans-factory-scopes-prototype)|Scopes a single bean definition to any number of object instances.|
|[request](https://docs.spring.io/spring-framework/reference/core/beans/factory-scopes.html#beans-factory-scopes-request)|Scopes a single bean definition to the lifecycle of a single HTTP request. That is, each HTTP request has its own instance of a bean created off the back of a single bean definition. Only valid in the context of a web-aware Spring `ApplicationContext`.|
|[session](https://docs.spring.io/spring-framework/reference/core/beans/factory-scopes.html#beans-factory-scopes-session)|Scopes a single bean definition to the lifecycle of an HTTP `Session`. Only valid in the context of a web-aware Spring `ApplicationContext`.|
|[application](https://docs.spring.io/spring-framework/reference/core/beans/factory-scopes.html#beans-factory-scopes-application)|Scopes a single bean definition to the lifecycle of a `ServletContext`. Only valid in the context of a web-aware Spring `ApplicationContext`.|
|[websocket](https://docs.spring.io/spring-framework/reference/web/websocket/stomp/scope.html)|Scopes a single bean definition to the lifecycle of a `WebSocket`. Only valid in the context of a web-aware Spring `ApplicationContext`.|

## 5. Описати життєвий цикл біна.
---
- Bean Definitions are declared
- Bean Definitions are loaded
- Bean Definitions are procesed
- Beans are instantiated
- Dependencies are injected (setters are called)
- Beans are Post Processed (before initialisation)
- Beans are initialised
- Beans are Post Processed (after initialisation)
- Bans are destroyed

## 6. Різниця між Constructor injection & setter injection (плюси-мінуси)?
---
-  **Partial dependency**: can be injected using setter injection but it is not possible by constructor. 
-  **Overriding**: Setter injection overrides the constructor injection. If we use both constructor and setter injection, IOC container will use the setter injection.
- **Changes**: We can easily change the value by setter injection. It doesn't create a new bean instance always like constructor. So setter injection is flexible than constructor injection.
---
**Pros & Cons**

| Key                            | Constructor based Injection                                                                                                                                    | Setter based Injection                                                                                                       |
| ------------------------------ | -------------------------------------------------------------------------------------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------- |
| Circular                       | It doesn’t allow to create circular dependency                                                                                                                 | It doesn’t check the circular dependency                                                                                     |
| Ordering                       | Constructor-based DI fixes the order in which the dependencies need to be injected.                                                                            | Setter-based DI helps us to inject the dependency only when it is required, as opposed to requiring it at construction time. |
| MutilThread Environment        | Combining with final fields, constructor injection gives extra safety in multithreaded environment                                                             | No extra benefit in setter injection                                                                                         |
| Spring Code generation Library | Spring code generation library doesn’t support constructor injection so it will not be able to create proxy. It will force you to use no-argument constructor. | Spring framework level code uses setter injection                                                                            |
| Use Case                       | It should be used for mandatory dependencies                                                                                                                   | It should be used for optional dependencies.                                                                                 |

## 7. Що таке bean Autowiring та описати як це працює, які існують типи?
---
**@Autowired** is one of the core annotations in Spring, used for automatic dependency injection. In simpler terms, it allows Spring to automatically wire the required beans (dependencies) into your classes, eliminating the need for manual configuration. With **@Autowired**, you no longer need to explicitly create and inject dependencies; Spring does it for you behind the scenes.

**How @Autowired Works**

When Spring scans your code for beans, it identifies the dependencies and automatically attempts to find the corresponding beans in its application context. If it finds a single matching bean, it injects it into the target class. If it finds multiple matching beans, it considers it an ambiguity, and you must provide additional qualifiers to resolve it.

**Use Cases of @Autowired**
- Dependency Injection
- Constructor Injection
- Setter Injection
- Autowired with Collections

## 8. Різниця між BeanFactory/Application context, BeanPostProcessor/BeanFactoryPostProcessor?
---
**BeanFactory vs Application context**

| BeanFactory                                                                                                                                                                                                    | ApplicationContext                                                                                                                                                     |
| -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| It is a fundamental container that provides the basic functionality for managing beans.                                                                                                                        | It is an advanced container that extends the BeanFactory that provides all basic functionality and adds some advanced features.                                        |
| It is suitable to build standalone applications.                                                                                                                                                               | It is suitable to build Web applications, integration with AOP modules, ORM and distributed applications.                                                              |
| It supports only Singleton and Prototype bean scopes.                                                                                                                                                          | It supports all types of bean scopes such as Singleton, Prototype, Request, Session etc.                                                                               |
| It does not support Annotations. In Bean Autowiring, we need to configure the properties in XML file only.                                                                                                     | It supports Annotation based configuration in Bean Autowiring.                                                                                                         |
| This interface does not provides messaging (i18n or internationalization) functionality.                                                                                                                       | ApplicationContext interface extends MessageSource interface, thus it provides messaging (i18n or internationalization) functionality.                                 |
| BeanFactory does not support Event publication functionality.                                                                                                                                                  | Event handling in the ApplicationContext is provided through the ApplicationEvent class and ApplicationListener interface.                                             |
| BeanFactory will create a bean object when the getBean() method is called thus making it Lazy initialization.                                                                                                  | ApplicationContext loads all the beans and creates objects at the time of startup only thus making it Eager initialization.                                            |
| BeanFactory interface provides basic features only thus requires less memory. For standalone applications where the basic features are enough and when memory consumption is critical, we can use BeanFactory. | ApplicationContext provides all the basic features and advanced features, including several that are geared towards enterprise applications thus requires more memory. |

**BeanPostProcessor vs BeanFactoryPostProcessor**

1. A bean implementing `BeanFactoryPostProcessor` is called when all bean definitions will have been loaded, but no beans will have been instantiated yet. This allows for overriding or adding properties even to eager-initializing beans. This will let you have access to all the beans that you have defined in XML or that are annotated (scanned via component-scan).
2. A bean implementing `BeanPostProcessor` operate on bean (or object) instances which means that when the Spring IoC container instantiates a bean instance then BeanPostProcessor interfaces do their work.
3. `BeanFactoryPostProcessor` implementations are "called" during startup of the Spring context after all bean definitions will have been loaded while `BeanPostProcessor` are "called" when the Spring IoC container instantiates a bean (i.e. during the startup for all the singleton and on demand for the proptotypes one)

## 9. Для чого ми можемо використовувати BeanPostProcessor, знайти в Spring .jar 3 приклада BeanPostProcessor. 
---
A bean post processor allows for custom modification of new bean instances created by spring bean factory. If you want to implement some custom logic after the Spring container finishes instantiating, configuring, and initializing a bean, we can plug in one or more BeanPostProcessor implementations.

**Examples**
- abstract class AbstractAdvisingBeanPostProcessor | org.springframework.aop.framework
- class BeanValidationPostProcessor | org.springframework.validation.beanvalidation
- class CommonAnnotationBeanPostProcessor | org.springframework.context.annotation
## 10. Що таке і як працює DispatcherServlet?
---
DispatcherServlet acts as the **Front Controller** for Spring-based web applications. Any request which is going to come into website, the front controller is going to accept all the requests and will make a decision that who is the right controller to handle that request.

DispatcherServlet handles an incoming HttpRequest, delegates the request, and processes that request according to the configured HandlerAdapter interfaces that have been implemented within the Spring application along with accompanying annotations specifying handlers, controller endpoints, and response objects.

## 11. Описати кроки http request на прикладі спрінг додатку від request до response?
---
request -> Servlet Filters -> Dispatcher Servlet -> Handler Mapper -> Handler Interceptor -> Controller -> **response** -> Handler Interceptor -> Dispatcher Servlet -> Servlet Filters -> response

## 12. Що таке Stereotype annotations?
---
These annotations are used to create Spring beans automatically in the application context. @Component annotation is the main Stereotype Annotation. There are some Stereotype meta-annotations which is derived from _@Component_ those are
1. _@Service_
	- We specify a class with @Service to indicate that they’re holding the business logic.
2. _@Repository_
	- We specify a class with @Repository to indicate that they’re dealing with **CRUD operations**, usually, it’s used with DAO (Data Access Object) or Repository implementations that deal with database tables.
3. _@Controller_
	- We specify a class with @Controller to indicate that they’re front controllers and responsible to handle user requests and return the appropriate response. It is mostly used with REST Web Services.