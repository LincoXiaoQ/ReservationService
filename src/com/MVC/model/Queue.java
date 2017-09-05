package com.MVC.model;

import java.util.LinkedList;

/**
 * Created by Linco_S on 2017/7/22.
 * 方法只由WQ调用
 */
public class Queue {
	// TODO: 2017/8/22 同步问题
	private LinkedList<QueueUser> currentQueue;

	public Queue() {
		this.currentQueue = new LinkedList<>();
	}

	public void add(QueueUser qu) {
		currentQueue.add(qu);
	}

	public void remove(QueueUser qu) {
		currentQueue.remove(qu);
	}

	public QueueUser getFirst() {
		return currentQueue.getFirst();
	}

	public QueueUser pop(){
		if (!currentQueue.isEmpty())
			return currentQueue.pop();
		return null;
	}

	public void push(QueueUser queueUser){
		currentQueue.push(queueUser);
	}

	public LinkedList<QueueUser> getCurrentQueue() {
		return currentQueue;
	}
}
