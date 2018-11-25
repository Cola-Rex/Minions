package ezreal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class EurekaController {

	@RequestMapping("/create")
	public String create() {
		return "eurekaList";
	}
	
}
