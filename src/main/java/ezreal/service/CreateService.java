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
	private ArrayList<URI> uriList = new ArrayList<>();
	
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
		
		//创建新的 eureka 配置文件
		StringBuilder sb = new StringBuilder();
		if (size == 0) {
			sb.append("http://localhost:9001/eureka/"); //默认第一个 server 端口 9001
		} else {
			//InetAddress addr = InetAddress.getLocalHost();
			//新建 eureka 的uri
			for (URI uri : uriList) {
				if (uri.getPort() != 9001 + size) {
					sb.append("http://" + uri.getHost() + ":" + uri.getPort() + "/eureka/,");
				}
			}
			sb.deleteCharAt(sb.length() - 1); //删除最后1个逗号
		}
		
		gitService.createFile(size++, sb.toString());
		
		//更新其他 eureka 配置
		if (size > 0) {
			for (URI uri : uriList) {
				StringBuilder sb1 = new StringBuilder();
				@SuppressWarnings("unchecked")
				List<URI> tempList = (List<URI>) uriList.clone();
				tempList.remove(uri);
				for (URI temp : tempList) {
					sb1.append("http://" + temp.getHost() + ":" + uri.getPort() + "/eureka/,");
				}
				sb1.deleteCharAt(sb1.length() - 1);
				gitService.updateFile(uri.getPort() - 9000, sb1.toString());
			}
		}
		
		//调用shell命令创建eureka server
		String containerId = shellService.createEureka(size++);
		containerList.add(containerId);
		
		//TODO 刷新其他服务
	}
	
}
