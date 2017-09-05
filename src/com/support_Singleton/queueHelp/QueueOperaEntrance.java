package com.support_Singleton.queueHelp;

import com.MVC.model.Queue;
import com.MVC.model.QueueUser;
import com.support.ThisServer;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;


/**
 * Created by Linco on 2017/8/12.
 */
public class QueueOperaEntrance {
	//线程同步
	@Autowired
	QueueListProvider qlp;
	private final Queue queue;
	private Set<OnQueueChangeListener> oqcl;

	public QueueOperaEntrance(Queue queue) {
		this.queue = queue;
		oqcl=new HashSet<>(8);
		oqcl.add(qlp);	//这个要放最前面最先响应
	}

	public void add(QueueUser qu) {
		queue.add(qu);
		onQueueChange();
	}

	public void remove(QueueUser queueUser) {
		queue.remove(queueUser);
		onQueueChange();
	}

	//业务失败,回退
	public void back(QueueUser queueUser){
		queue.push(queueUser);
		onQueueChange();
	}
	public QueueUser takeFirst() {
		QueueUser qu=queue.pop();
		onQueueChange();
		return qu;
	}

	private void onQueueChange(){
		for(OnQueueChangeListener oqc:oqcl){
			oqc.onQueueChange();
		}
	}
	//注册时相同服务对应注册的
	public void addOnQueueChangeListener(OnQueueChangeListener onQueueChangeListener){
		oqcl.add(onQueueChangeListener);
	}
	public void removeOnQueueChangeListener(OnQueueChangeListener onQueueChangeListener){
		oqcl.remove(onQueueChangeListener);
	}
}
