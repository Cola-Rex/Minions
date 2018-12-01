package ezreal.service.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ezreal.entity.MyServiceInstance;

@FeignClient(value = "eureka-server")
public interface EurekaList {

	@RequestMapping(value = "/test/getServerList", method = RequestMethod.GET)
	public List<MyServiceInstance> getEurekaList();
	
}
