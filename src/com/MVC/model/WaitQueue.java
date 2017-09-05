package com.MVC.model;

import com.support_Singleton.queueHelp.QueueInThisServer;

import java.util.Date;
import java.util.PriorityQueue;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Linco_S on 2017/7/23.
 */
public class WaitQueue {
	PriorityQueue<WaitUser> waitQueue;
	QueueInThisServer qits;
	Timer tw=new Timer();	//处理等待队列的任务
	TimerTask ttw=new TimerTask() {
		@Override
		public void run() {
			addWaitToQueue();
		}
	};

	public WaitQueue(QueueInThisServer qits) {
		waitQueue = new PriorityQueue<>();
		this.qits=qits;
	}

	public void add(QueueUser qu) {
		waitQueue.add(new WaitUser(qu,qu.getTimeTemp()));
	}

	private QueueUser getFirst() {
		return null;
	}

	public void remove(int uid) {
		for (WaitUser wu : waitQueue) {
			if (wu.getQu().getUid() == uid) {
				waitQueue.remove(wu);
				break;
			}
		}
	}

	private void addWaitToQueue(){
		if(waitQueue!=null&&!waitQueue.isEmpty()){
			qits.addWaitToQueue(waitQueue.poll().getQu());
			refreshWait();//定时下一位
		}
	}
	private void refreshWait(){
		if(!waitQueue.isEmpty()){
			WaitUser au=waitQueue.peek();
			tw.cancel();
			tw.schedule(ttw, new Date(System.currentTimeMillis()+au.getTimeAddToQueue()*1000));
			//等待运行
		}
	}
	public int getSize(){
		return waitQueue.size();
	}
}
