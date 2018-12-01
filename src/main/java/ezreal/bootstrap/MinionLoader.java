package ezreal.bootstrap;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import ezreal.entity.Minion;
import ezreal.entity.MyServiceInstance;
import ezreal.repository.MinionRepository;
import ezreal.service.feign.EurekaList;

@Component
public class MinionLoader implements ApplicationListener<ApplicationReadyEvent> {

	@Autowired
    private MinionRepository minionRepository;
	@Autowired
	private EurekaList eurekaList;

    private Logger log = LoggerFactory.getLogger(MinionLoader.class);

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {

		//获取所有在线的eureka server
		List<MyServiceInstance> list = eurekaList.getEurekaList();
		for (MyServiceInstance instance : list) {
			Minion minion = new Minion();
			minion.setMinionId(instance.getServiceId());
			minion.setAddress(instance.getHost() + ":" + instance.getPort());
			minionRepository.save(minion);
			log.info("预保存：{}", minion);
		}
    }
}
