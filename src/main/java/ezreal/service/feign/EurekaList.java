package ezreal.service.feign;

import java.util.List;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "eureka-server")
public interface EurekaList {

	@RequestMapping("/test/getServerList")
	public List<ServiceInstance> getEurekaList();
	
}
