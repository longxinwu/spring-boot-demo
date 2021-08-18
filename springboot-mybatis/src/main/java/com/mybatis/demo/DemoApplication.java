package com.mybatis.demo;

import com.mybatis.demo.config.NettyConfig;
import com.mybatis.demo.netty.WebSocketNettyServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Autowired
	private WebSocketNettyServer webSocketNettyServer;

	@Autowired
	private NettyConfig nettyConfig;

	@Override
	public void run(String... args) throws Exception {
		new Thread(new Runnable() {
			@Override
			public void run() {
				webSocketNettyServer.start(nettyConfig.getPort());
			}
		}).start();
	}
}
