package ezreal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import ezreal.service.CreateService;

@Controller
public class EurekaController {

	@Autowired
	private CreateService createService;
	
	@RequestMapping("/create")
	public String create() {
		createService.createEureka();
		return "eurekaList";
	}
	
}
