package ezreal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ezreal.service.feign.EurekaList;

@Controller
public class IndexController {
	
	@Autowired
	EurekaList eurekaList;
	
    @RequestMapping("/")
    public String index(){
        return "index";
    }
    
    @RequestMapping("/a")
    @ResponseBody
    public String aa(){
    	List<ServiceInstance> list = eurekaList.getEurekaList();
    	System.out.println(list);
        return "IG牛逼";
    }
}
