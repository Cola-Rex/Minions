package ezreal.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ezreal.entity.Minion;
import ezreal.repository.MinionRepository;

@Service
public class CreateService {

	private static final Logger log = LoggerFactory.getLogger(CreateService.class);
	
	@Autowired
	private ShellService shellService;
	@Autowired
	private GitService gitService;
	@Autowired
	private RefreshService refreshService;
	@Autowired
	private MinionRepository minionRepository;
	
	//保存所有容器ID的集合
	private List<String> containerList = new ArrayList<>();
	
	public void createEureka() {
		//从数据库获取所有minion
		List<Minion> list = minionRepository.findAll();
		//当前数据库中minion的数量
		long size = minionRepository.count();
		log.info("当前数据库中的minion数量：{}", size);
		//创建新的 eureka 配置文件
		StringBuilder sb = new StringBuilder();
		if (size == 0) {
			sb.append("http://localhost:9001/eureka/"); //默认第一个 server 端口 9001
		} else {
			//InetAddress addr = InetAddress.getLocalHost();
			//新建 eureka 的uri
			for (Minion minion : list) {
				sb.append("http://" + minion.getAddress() + "/eureka/,");
			}
			sb.deleteCharAt(sb.length() - 1); //删除最后1个逗号
		}
		log.info("新建 eureka 的 defaultZone : {}", sb);
		gitService.createFile(size+1, sb.toString());
		
		//将当前新建 eureka 保存至数据库
		Minion minion = new Minion();
		minion.setAddress("localhost:" + (size+9001));
		minion.setMinionId(String.valueOf(size+1));
		minionRepository.save(minion);
		
		//更新其他 eureka 配置
		if (size > 0) {
			for (Minion mi : list) {
				StringBuilder tempString = new StringBuilder();
				List<Minion> tempList = new ArrayList<Minion>(list);
				tempList.remove(mi); //移除当前配置服务的uri
				for (Minion temp : tempList) {
					tempString.append("http://" + temp.getAddress() + "/eureka/,");
				}
				tempString.append("http://" + minion.getAddress() + "/eureka/");//最后添加上面新建eureka的uri
				log.info("更新服务：{}，serviceUri:{}", mi.getMinionId(), tempString);
				gitService.updateFile(Long.parseLong(mi.getAddress().split(":")[1]), tempString.toString());
			}
		}
		
		//调用shell命令创建eureka server
		String containerId = shellService.createEureka(size+1);
		log.info("新容器ID：{}", containerId);
		containerList.add(containerId);
		
		// 使用 /refresh 接口重启其他服务
		refreshService.refresh(list);
	}
	
}
