package com.haniln.hicp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application_test {

	private static final Logger logger = LoggerFactory.getLogger(Application_test.class);

	public static void main(String[] args) {
		logger.debug("Kim A Ran 1");
		logger.debug("김아란 22");
		logger.debug("한글 안깨지는건가요? 333");
		//SpringApplication.run(Application.class, args);
	}
}
