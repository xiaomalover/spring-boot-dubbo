package com.weison;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * @author xiaomalover <xiaomalover@gmail.com>
 * 消费者程序入口
 */
@SpringBootApplication
@ImportResource(value = { "classpath:config/dubbo-consumer.xml", "classpath:config/redisson.xml" })
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}

