package com.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ServletComponentScan 
@EnableScheduling
@ComponentScan(basePackages = { "com.redis" })
public class Application {

	 /**

     *-javaagent:.\lib\springloaded-1.2.4.RELEASE.jar -noverify

     * @param args

     */

    public static void main(String[] args) {

           SpringApplication.run(Application.class,args);

    }
	
}
