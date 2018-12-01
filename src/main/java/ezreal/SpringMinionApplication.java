package ezreal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * 主函数
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class SpringMinionApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringMinionApplication.class, args);
	}

}
