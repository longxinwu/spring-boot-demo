# spring-boot-demo
##1.spring-boot入门
1. 简化Spring应用开发的一个框架
2. 整个Spring技术栈的整合
##2.微服务(待深入了解)
##3.Maven设置
1. 给maven的配置文件setting.xml的profiles标签添加
```
<profile>
  <id>jdk-1.8</id>
  <activation>
    <activeByDefault>true</activeByDefault>
    <jdk>1.8</jdk>
  </activation>
  <properties>
    <maven.compiler.source>1.8</maven.compiler.source>
    <maven.compiler.target>1.8</maven.compiler.target>
    <maven.compiler.compilerVersion>1.8</maven.compiler.compilerVersion>
  </properties>
</profile>
```
2. 修改镜像地址
```
<mirror>  
    <id>nexus-aliyun</id>  
    <mirrorOf>central</mirrorOf>    
    <name>Nexus aliyun</name>  
    <url>http://maven.aliyun.com/nexus/content/groups/public</url>  
</mirror>
```
#4. spring-boot-hello world
##1.pom文件探究
1. 父项目
```
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.3.2.RELEASE</version>
    <relativePath/> <!-- lookup parent from repository -->
</parent>
```
他的父项目是：
```
<parent>
二级有序列表内容
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-dependencies</artifactId>
    <version>2.3.2.RELEASE</version>
</parent>
```
他来真正管理Spring boot应用里面的所有依赖版本（版本仲裁）
2. 导入的依赖
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```
spring-boot-start-web:spring-boot场景启动器，帮我们带入了web模块正常运行所依赖的组件
##2. 主程序类，程序入口
@**AutoConfigurationPackage**：自动配置包
AutoConfigurationPackages.Registrar.class
将主配置类(@SpringBootApplication标注的类)的所在包及下面所有包里面的所有组件扫描到Spring容器中
@**Import**(AutoConfigurationImportSelector.class) 自动组件选择器

##3.resources 目录
1. static保存内所有的静态资源：js,css,images...
2. templates: 保存所有的模板页面(Spring boot默认jar包使用嵌入式的Tomcat,默认不支持JSP页面,可以使用模板引擎，freemarker, thymeleaf)
#2. 配置文件
##1. yml与properties的差异
###1. yml以数据为中心，比json,xml,等更适合做配置文件
```yml
server:
    port: 8081
```
###2. yml基本语法
1. key:(空格)V : 表示一堆键值对（空格必须有），以空格的缩进来控制层级关系；
```yml
server:
    port: 8081
    path: /hello
```
属性和值也是大小写敏感
2. 字面量 普通的值
   * k: v 字面直接来写，字符串默认不用加上单引号或者双引号；
3. 对象 Map
   * k: v 属性和值
```yml
user:
    name: zhangsan
    age: 20   
   ```
4. 数组(List,Set)
```yml
pets:
    - cat
    - dog
    - pig
```
行内写法
```yml
pets:[cat, dog, pig]
```
3. 配置文件值获取
    * 请您参考Person类 @ConfigurationProperties(prefix = "person")
    * 或者用@Value注解 
    ```
   @Value("${person.name}")
   private String name;
    ```
   JSR303数据校验,用来校验字段取值
   * @PropertySource(value = {"classpath:person.properties"})，该注解表示从指定配置文件中取配置值
   * @Configuration指明一个类是配置类,再用@Bean表示给项目添加组件
   ```java
   @Configuration
   public class MyConfig {
       /**
        * 将方法的返回值，添加到容器中,容器中这个组件默认的id就是方法名
        * @return
        */
       @Bean
       public HelloService helloService(){
           return new HelloService();
       }
   }
   ```
   


