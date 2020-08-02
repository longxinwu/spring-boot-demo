##1 pom文件
##2 注解
### @SpringBootConfiguration 表明这是一个配置类
### @EnableAutoConfiguration 开启自动配置功能
#### @AutoConfigurationPackage 自动配置包
#### @Import({Registrar.class})是Spring的底层注解@Import,给容器导入一个组件，导入的组件由Registrar.class将著配置类（@SpringBootApplication注解的类）的所在包及下面所有子包里面的所有组件扫描到Spring容器
#### @Import({AutoConfigurationImportSelector.class}) 给容器中导入组件，将所有需要导入的舰以全类名的方式返回，这些组件就会被添加到容器中。
##### 会给容器中导入非常多的自动配置类（xxxAutoConfiguration）;就是给容器中导入这个场景需要的所有组件，并配置好这些组件。
##### Spring Boot 启动的时候从各类路径下的META-INF/spring.factories中获取EnableAutoConfigration指定的值，将这些值作为自动配置类导入到容器中，自动配置类生效，然后帮我们进行自动配置工作
##3 项目结构
### templates 存放所有的模板页面，默认不支持jsp，可以使用模板引擎 freemarker thymeleaf
###4 配置文件 application.properties/application.yml