package top.biandeshen.sandstorm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
//@EnableEurekaClient
@MapperScan("top.biandeshen.sandstorm.repository")
public class SandStormInterfaceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SandStormInterfaceApplication.class, args);
	}
}
