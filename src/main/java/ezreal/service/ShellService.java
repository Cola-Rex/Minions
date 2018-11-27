package ezreal.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ShellService {

	private Logger log = LoggerFactory.getLogger(ShellService.class);
	
	//docker run -d -e "spring.profiles.active=peer1" --network host eureka-server:0.0.1 后台执行
	//docker run -it -e "spring.profiles.active=peer1" --network=host eureka-server:0.0.1 bash 交互执行
	public String createEureka(int peerNumber) {
		String containerId = "";
		try {
			String cmd = "docker run -d -e \"spring.profiles.active=peer" + peerNumber + "\" --network host eureka-server:0.0.1";
			log.info("执行命令：{}", cmd);
			Process process = Runtime.getRuntime().exec(cmd, null, null);
			
			BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
			containerId = input.readLine(); //获取容器ID
			
			/*while ((line = input.readLine()) != null) {
					log.info(line);
			}*/
			process.destroy(); //销毁进程
			input.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return containerId;
	}
}
