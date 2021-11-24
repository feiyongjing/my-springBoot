# my-springBoot
自己造的SpringBoot轮子

## 介绍

模仿 Spring Boot 写的一个轻量级的 HTTP 框架。

内置由 Netty 编写 HTTP 服务器，无需额外依赖 Tomcat 之类的 web 服务器。使用 Netty 几十行代码即可实现一个简易的 HTTP 服务，性能高且轻量。

**为什么要写？**

写这个东西只是自己个人的兴趣爱好使然，，主要目的还是为了提高自己的编码能力。

## 特点

1. 内置由 Netty 编写 HTTP 服务器，无需额外依赖 Tomcat 之类的 web 服务
2. 代码简洁，可读性好
3. 支持 Spring MVC 常用的注解，用法也和 Spring MVC 基本一样
4. 后端只返回 json 数据给前端
5. 集成了 checkstyle 、~~spotbugs、pmd~~ 并设置了 commit 钩子来保证代码质量


### 功能实现

#### Get 请求和 POST 请求处理

- [x] `@GetMapping` : 处理 Get 请求
- [x] `@PostMapping` ：处理 Post 请求
- [x] `@RequestBody` : 接收前端传递给后端的 json 字符串
- [x] `@RequestParam` ：获取 Get 请求的 URL 查询参数
- [x] `@PathVariable` : 获取 URL 中的参数/占位符

#### IOC

- [x] `@Autowired` ：注入对象
- [x] `@Component`：声明对象被 IOC 容器管理
- [x] `@Qualifier`: 指定注入的bean
- [x] 解决循环依赖问题

#### AOP

- [x] `@Aspect`
- [x] `@Pointcut`
- [x] `@Before`
- [x] `@After`
- [x] `@Order`
- [x] `@AfterReturning`
- [x] `@AfterThrowing`
- [x] 支持自定义的注解

#### 拦截器

- [x] 支持拦截实现某个接口的所有 bean(基于 JDK 动态代理)
- [x] 支持拦截某个没有实现任何接口的 bean（基于 CGLIB 动态代理）
- [x] 支持全局拦截器（拦截所有 bean）
- [x] 支持配置多个拦截器
- [x] 支持自定义拦截器的执行顺序

#### 异常处理

- [ ] `@ControllerAdvice`
- [ ] `@ExceptionHandler`

#### 配置文件读取

- [x] 支持读取 yaml 以及 properties 类型的文件

#### 其他

- [x] `@SpringBootApplication`
- [x] `@Configuration`
- [x] `@Bean`

### 代码质量

- [x] 集成 checkstyle
- [x] 集成 spotbugs
- [x] 设置 commit 钩子
- [x] 提高测试覆盖率，增加代码稳定性，为重构提供保障

## 功能演示

查看src\main\java\com\github\feiyongjing\service\spring\dome


