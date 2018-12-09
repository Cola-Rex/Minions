package ezreal.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import ezreal.entity.Minion;

@Service
public class RefreshService {

	private static final Logger log = LoggerFactory.getLogger(RefreshService.class);
	
	public void refresh(List<Minion> list) {
		RestTemplate restTemplate = new RestTemplate();
		for (Minion minion : list) {
			restTemplate.postForObject("http://" + minion.getAddress() + "/refresh", null, String.class);
			log.info("服务 {} 更新完毕", minion.getAddress());
		}
	}
}
