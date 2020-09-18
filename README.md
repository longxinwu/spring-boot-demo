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

