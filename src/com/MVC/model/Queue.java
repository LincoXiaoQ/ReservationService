package com.MVC.model;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by Linco_S on 2017/7/22.
 * 方法只由WQ调用
 */
public class Queue {
	// TODO: 2017/8/22 同步问题
	private BlockingQueue<QueueUser> currentQueue;

	public Queue() {
		this.currentQueue = new LinkedBlockingDeque<>();
	}

	public void add(QueueUser qu) {
		currentQueue.add(qu);
	}

	public void remove(QueueUser qu) {
		currentQueue.remove(qu);
	}

	public QueueUser getFirst() {
		return currentQueue.peek();
	}

	public QueueUser pop(){
		if (!currentQueue.isEmpty())
			return currentQueue.poll();
		return null;
	}

	public Iterator<QueueUser> getIterator(){
		return currentQueue.iterator();
	}
	public void push(QueueUser queueUser){
		currentQueue.add(queueUser);
	}

	public BlockingQueue<QueueUser> getCurrentQueue() {
		return currentQueue;
	}
}
