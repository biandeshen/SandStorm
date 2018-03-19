package top.biandeshen.sandstorm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

// 启注解事务管理，等同于xml配置方式的 <tx:annotation-driven />
@EnableTransactionManagement
@SpringBootApplication
//@EnableEurekaClient
@EnableSwagger2
@MapperScan("top.biandeshen.sandstorm.repository")
public class SandStormInterfaceApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(SandStormInterfaceApplication.class, args);
	}
}
