package com.support_Singleton.queueHelp;

import com.MVC.model.Queue;
import com.MVC.model.QueueUser;
import com.support.ThisServer;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Linco_S on 2017/8/2.
 * 包装modal的信息提供给两个端,包括list和list的信息
 */
public class QueueListProvider implements OnQueueChangeListener{

	//提供初始化值避免为null
	private String jsonQueue="\"queue\":[]";

	/*不允许这样,因为构造完成才注入,构造方法先执行
	@Autowired
	QueueOperaEntrance qoe;

	public QueueListProvider() {
		qoe.addOnQueueChangeListener(this);
	}*/
	QueueOperaEntrance qoe;
	public QueueListProvider(QueueOperaEntrance qoe) {
		this.qoe=qoe;
		qoe.addOnQueueChangeListener(this);
	}

	//队列改变是调用同步方法
	private void syncJson(){
		StringBuilder sb=new StringBuilder();
		sb.append("\"queue\":[");
		Iterator<QueueUser> i=qoe.getIterator();
		while(i.hasNext()){
			sb.append("\""+i.next().getUserName()+"\",");
		}
		if(sb.charAt(sb.length()-1)==',')
			sb.deleteCharAt(sb.length()-1);
		sb.append("]");
		System.out.println(jsonQueue=sb.toString());
	}
	public String getQueueJson(){
		return jsonQueue;
	}

	@Override
	public void onQueueChange() {
		syncJson();
	}
}
