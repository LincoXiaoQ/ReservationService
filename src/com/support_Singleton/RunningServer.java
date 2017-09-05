package com.support_Singleton;

import com.support.ThisServer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Linco on 2017/8/7.
 * 当前登录的服务
 * 在创建/销毁服务时由此产生对象
 */
public class RunningServer implements BeanFactoryAware{
	private BeanFactory beanFactory;
	Map<Integer,ThisServer> serverMap;
	public RunningServer() {
		serverMap=new HashMap<Integer,ThisServer>();
	}
	//获取当前管理员的服务或创建新的服务
	//Socket和Ajax两个服务接收端都会get
	public ThisServer getServer(int aid){
		if (serverMap.containsKey(aid))
			return serverMap.get(aid);
		ThisServer thisServer= (ThisServer) beanFactory.getBean("thisServer");
		//生成后给出Aid
		thisServer.setAid(aid);
		serverMap.put(aid,thisServer);
		return thisServer;
	}
	public void StopServer(int aid){
		if (serverMap.containsKey(aid))
			serverMap.remove(aid);
	}

	//判断某级别或以下的组的服务是否存在开启的
	public boolean isServerRunning(int serverLeval){
		for (ThisServer ts:serverMap.values()){
			if (ts.isState_ser()){
				return true;
			}
		}
		return false;
	}

	public Collection<ThisServer> getServerMap() {
		return serverMap.values();
	}

	//为了多例构造ThisServer实例
	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory=beanFactory;
	}

}
