package com.support_Singleton.queueHelp;

import com.MVC.model.Queue;
import com.MVC.model.QueueUser;
import com.MVC.model.WaitQueue;

/**
 * Created by Linco on 2017/8/7.
 * 用户的排队,取消请求经由这里分派
 */
public class QueueInThisServer {
	private Queue queue;
	private WaitQueue waitQueue;

	public QueueInThisServer() {
		queue=new Queue();
		waitQueue=new WaitQueue(this);
	}

	public Queue getQueue() {
		return queue;
	}

	public WaitQueue getWaitQueue() {
		return waitQueue;
	}
	public void addWaitToQueue(QueueUser qu){
		queue.add(qu);
	}


}
