package com.lzl;

import com.lzl.metric.Bootstrap;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author lizanle
 * @data 2019/3/18 9:01 PM
 */
@SpringBootApplication
public class Application implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }

    public void run(String... args) throws Exception {
        Bootstrap.init();
    }
}
