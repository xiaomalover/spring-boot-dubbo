package com.weison;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * @author xiaomalover <xiaomalover@gmail.com>
 * 生产者程序入口
 */
@SpringBootApplication
@ImportResource({ "classpath:config/dubbo-provider.xml", "classpath:config/redisson.xml" })
@MapperScan("com.weison.provider.mapper")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
