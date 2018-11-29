package ezreal.service;

import java.net.URI;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RefreshService {

	private static final Logger log = LoggerFactory.getLogger(RefreshService.class);
	
	public void refresh(ArrayList<URI> uriList) {
		RestTemplate restTemplate = new RestTemplate();
		for (URI uri : uriList) {
			restTemplate.postForObject("http://" + uri.getHost() + ":" + uri.getPort() + "/refresh", null, String.class);
			log.info("服务 {}:{} 更新完毕");
		}
	}
}
