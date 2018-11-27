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
	public void createFile(String fileName) {
		Map<String, String> map = new HashMap<>();
		map.put("private_token", "pj2h2kV-9jwLy42YKz7G");
		map.put("branch", "master"); 					//分支
		map.put("content", "你是真的皮"); 				//内容
		map.put("commit_message", "create a new file"); //提交信息
		String result = restTemplate.postForObject("http://192.168.31.250/api/v4/projects/1/repository/files/{fileName}", map, String.class, fileName);
		log.info("gitlab API 返回" + result);
	}
	
}
