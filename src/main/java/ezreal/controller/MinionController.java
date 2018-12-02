package ezreal.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import ezreal.entity.Minion;
import ezreal.repository.MinionRepository;
import ezreal.service.CreateService;

@Controller
public class MinionController {

	private static final Logger log = LoggerFactory.getLogger(MinionController.class);
	
	@Autowired
	private MinionRepository minionRepository;
	@Autowired
	private CreateService createService;
	
	@RequestMapping("/create")
	public String create(Model model) {
		createService.createEureka();
		model.addAttribute("minions", minionRepository.findAll());
		log.info("燃料填充，立场启动，开始EUREKA SERVER！");
		return "minions";
	}
	
	@GetMapping("/minions")
	public String list(Model model) {
		model.addAttribute("minions", minionRepository.findAll());
		log.info("显示全部 minion");
		return "minions";
	}
	
	@GetMapping("minion/{id}")
	public String showMinion(@PathVariable int id, Model model) {
		Minion minion = minionRepository.findById(id);
		model.addAttribute("minion", minion);
		log.info("显示 ID：{}的服务，minion:{}", id, minion.getAddress());
		return "minionshow";
	}
	
}
