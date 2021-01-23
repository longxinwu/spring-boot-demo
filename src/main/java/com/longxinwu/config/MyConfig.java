package com.longxinwu.config;

import com.longxinwu.bean.Dog;
import com.longxinwu.bean.User;
import com.longxinwu.service.HelloService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 *  @Configuration: 指明当前类是一个配置类，就是来代替之前的Spring配置文件的
 *  在原来的配置文件中用<bean></bean>标签添加组件
 *  而在配置类中用@Bean给容器添加组件,默认是单实例
 *  配置类本身就是组件
 *  proxyBeanMethods 代理bean的方法 为true时，代理对象获取方法，Spring boot总会检查该组件是否在容器中是否有，有的话就用存在的，保证组件单实例
 *  为false时，则不会检查。应用场景：（1）配置类之间无依赖关系，则用false lite模式 可减速应用启动，减少判断（2）配置类之间有依赖关系，则用full模式
 *  给容器添加组件还可以用@Import({Dog.class, User.class}) 默认全类名导入组件
 */
@Import({Dog.class, User.class})
@Configuration
public class MyConfig {
    /**
     * 将方法的返回值，添加到容器中,容器中这个组件默认的id就是方法名
     * @return
     */
    @Bean
    public Dog dog01(){
        return new Dog("xiaohei", 2);
    }

}
