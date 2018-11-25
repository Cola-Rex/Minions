package ezreal.service.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "eureka-server")
public interface EurekaList {

	@RequestMapping("/test/getServerList")
	public Map<String, String> getEurekaList();
	
}
