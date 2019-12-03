package com.xxm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 * 描述
 *
 * @author Mr Liu
 * @version 1.0
 * @package com.xxm *
 * @Date 2019-11-23
 * @since 1.0
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
// exclude = DataSourceAutoConfiguration.class 排除自动配置类 不要自动配置
@EnableEurekaClient
@CrossOrigin//支持跨域 服务端系统支持前端发送过来的ajax请求
public class FileApplication {
    public static void main(String[] args) {
        SpringApplication.run(FileApplication.class, args);
    }
}
