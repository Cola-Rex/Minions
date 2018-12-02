package ezreal.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * 调用gitlab API 的工具类
 * @author Ezreal
 */
@Service
public class GitService {

	private Logger log = LoggerFactory.getLogger(GitService.class);

	private RestTemplate restTemplate = new RestTemplate();
	//http://192.168.31.250/api/v4/projects/1/repository/files/test%2Etxt?
	//private_token=pj2h2kV-9jwLy42YKz7G&branch=master&content=你是真的皮&commit_message=create%20a%20new%20file
	public void createFile(long count, String serviceUrl) {
		Map<String, String> map = new HashMap<>();
		map.put("private_token", "pj2h2kV-9jwLy42YKz7G");
		map.put("branch", "master"); 					//分支
		map.put("content", 
				"server:\r\n" + 
				"  port: 900" + count + "\r\n" + 
				"\r\n" + 
				"spring:\r\n" + 
				"  application:\r\n" + 
				"    name: eureka-server\r\n" + 
				"eureka:\r\n" + 
				"  instance:\r\n" + 
				"    hostname: localhost\r\n" + 
				"    preferIpAddress: true\r\n" + 
				"  client:\r\n" + 
				"    registerWithEureka: true\r\n" + 
				"    fetchRegistry: true\r\n" + 
				"    serviceUrl:\r\n" + 
				"      defaultZone: " + serviceUrl + "\r\n" + 
				"\r\n" + 
				"  server:\r\n" + 
				"      waitTimeInMsWhenSyncEmpty: 0\r\n" + 
				"      enableSelfPreservation: false"); 				//内容
		map.put("commit_message", "create a new file"); //提交信息
		String result = restTemplate.postForObject("http://192.168.31.250/api/v4/projects/1/repository/files/{fileName}", map, String.class, "eureka-server-peer" + count + ".yml");
		log.info("gitlab API 返回" + result);
	}
	
	public void updateFile(long count, String serviceUrl) {
		Map<String, String> map = new HashMap<>();
		map.put("private_token", "pj2h2kV-9jwLy42YKz7G");
		map.put("branch", "master"); 					//分支
		map.put("content", 
				"server:\r\n" + 
				"  port: " + count + "\r\n" + 
				"\r\n" + 
				"spring:\r\n" + 
				"  application:\r\n" + 
				"    name: eureka-server\r\n" + 
				"eureka:\r\n" + 
				"  instance:\r\n" + 
				"    hostname: localhost\r\n" + 
				"    preferIpAddress: true\r\n" + 
				"  client:\r\n" + 
				"    registerWithEureka: true\r\n" + 
				"    fetchRegistry: true\r\n" + 
				"    serviceUrl:\r\n" + 
				"      defaultZone: " + serviceUrl + "\r\n" + 
				"\r\n" + 
				"  server:\r\n" + 
				"      waitTimeInMsWhenSyncEmpty: 0\r\n" + 
				"      enableSelfPreservation: false"); 				//内容
		map.put("commit_message", "create a new file"); //提交信息
		restTemplate.put("http://192.168.31.250/api/v4/projects/1/repository/files/{fileName}", map, String.class, "eureka-server-peer" + count + ".yml");
		log.info("文件更新成功");
	}
	
}
