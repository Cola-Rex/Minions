package ezreal.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Service;

import ezreal.service.feign.EurekaList;

@Service
public class CreateService {

	@Autowired
	private EurekaList eurekaList;
	@Autowired
	private ShellService shellService;
	@Autowired
	private GitService gitService;
	
	//保存所有容器ID的集合
	private List<String> containerList = new ArrayList<>();
	//保存所有eureka 的地址信息
	private List<URI> uriList = new ArrayList<>();
	
	public void createEureka() {
		//获取所有在线的eureka server
		List<ServiceInstance> list = eurekaList.getEurekaList();
		//已经启动的 eureka server 数量
		int size = list.size();
		//判断是否有新的eureka，有则添加
		for (ServiceInstance si : list) {
			if (!uriList.contains(si.getUri())) {
				uriList.add(si.getUri());
			}
		}
		
		//调用gitlab API 更新配置文件
		gitService.createFile("eureka-server-peer" + (size++) + ".yml");
		
		//调用shell命令创建eureka server
		String containerId = shellService.createEureka(size++);
		containerList.add(containerId);
		
	}
	
}
